# coding: utf-8
#Made: Pedro Marques 89069 e Flávia Figueiredo 88887 

import csv
import logging
import argparse
import socket
import pickle
import functools
import locale
import queue
import threading



logging.basicConfig(level=logging.DEBUG,format='%(asctime)s %(name)-12s %(levelname)-8s %(message)s',datefmt='%m-%d %H:%M:%S')
logger = logging.getLogger('coordinator')
coordinatorSocket = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
datastore = [] #Guarda os blobs do texto
hist = [] #Guarda os Maps feitos pelos workers
wordCount = {} #Guarda as palavras do texto e todas as vezes que se repetem
workerQueue = queue.Queue() #Queue de workers
backUpQueue = queue.Queue() #Queue de coordenators caso o principal morra
locale.setlocale(locale.LC_ALL,"pt_PT.UTF-8")


def main(args):
    address = ('localhost', args.port)
    with args.file as f:
        while True:
            blob = f.read(args.blob_size)
            if not blob:
                break
            while not str.isspace(blob[-1]):
                ch = f.read(1)
                if not ch:
                    break
                blob += ch
            logger.debug('Blob: %s', blob)
            datastore.append(blob)
    backup = False
    sizeDatastore = 0
    try:
        coordinatorSocket.bind(address)
        coordinatorSocket.settimeout(3)
    except:
        backup = True
    if not backup:
        work()
    else:
        coordinatorSocket.bind(('localhost', args.port + 1))
        coordinatorSocket.settimeout(8)
        registerMessage = {'task': 'backupRegister'}
        send(address, registerMessage)
        toPopQueue = queue.Queue()
        while backup:
            info, addr = recv()
            try:
                message = pickle.loads(info)
                logger.info('Received: %s', message['task'])
                if message['task'] == 'done':
                    backup = False
                if message['task'] == 'mapPopUpdate':
                    datastore.remove(message['info'])
                if message['task'] == 'mapInfoUpdate':
                    hist.append(message['info'])
                if message['task'] == 'reducePopUpdate':
                    hist.remove(message['info'])
                if message['task'] == 'reduceInfoUpdate':
                    for i in message['info']:
                        words = message['info']
                        if i in wordCount: 
                            wordCount[i] += words[i]
                        else:
                            wordCount[i] = words[i]
            except:
                backup = False
                logger.info("Becoming the coordinator")
                work()

def work():
	backUpOn = False
	done = False
	backAddr = None
	while not done:
		if not backUpQueue.empty():
			backUpOn = True
		worker, addr = recv()
		try:
			message = pickle.loads(worker)
			logger.debug("Received: %s", message['task'])
			if message['task'] == 'backupRegister':
				backUpQueue.put(addr)
			if message['task'] == 'register':
				workerQueue.put(addr)
				th = threading.Thread(target=toMap, args=(backUpOn, ))
				th.start()
				if backUpOn:
					backAddr = backUpQueue.get()
					message = {'task': 'registerBackup', 'info': backAddr}
					backUpQueue.put(backAddr)
					send(addr, message)
			if message['task'] == 'map':
				if backUpOn:
					backUp = backUpQueue.get()
					message = {'task': 'mapInfoUpdate', 'info': message['info']}
					send(backUp, message)
					backUpQueue.put(backUp)
				hist.append(message['info'])
				th = threading.Thread(target=toMap, args=(backUpOn, ))
				th.start()
			if message['task'] == 'reduce':
				if backUpOn:
					backUp = backUpQueue.get()
					message = {'task': 'reduceInfoUpdate', 'info': message['info']}
					send(backUp, message)
					backUpQueue.put(backUp)
				th = threading.Thread(target=toReduce, args=(backUpOn, ))
				th.start()
				for i in message['info']:
					words = message['info']
					if i in wordCount:
						wordCount[i] += words[i]
					else:
						wordCount[i] = words[i]      
		except TypeError:
			done = True
			while not backUpQueue.empty():
				backUp = backUpQueue.get()
				message = {'task': 'done'}
				send(backUp, message)
			if len(datastore) == 0 and len(hist) != 0 or len(datastore) != 0:
				logger.info("Impossible to make task")
			else:
				logger.info("Making the csv file")
				while not workerQueue.empty():
					worker = workerQueue.get()
					message = {'task': 'goodbye'}
					send(worker, message)
				makeFile()     


def toMap(backupon):
	if len(datastore) != 0:
		addr = workerQueue.get()
		message = datastore.pop()
		send(addr, {'task': 'toMap', 'info': message})
		workerQueue.put(addr)
		if backupon:
			backUp = backUpQueue.get()
			message = {'task': 'mapPopUpdate', 'info': message}
			send(backUp, message)
			backUpMessage = {'task': 'backup', 'addr': backUp}
			send(addr, backUpMessage)
			backUpQueue.put(backUp)
	else:
		toReduce(backupon)

def toReduce(backupon):
    if len(hist) != 0:
        message = hist.pop()
        addr = workerQueue.get()
        send(addr, {'task': 'toReduce', 'info': message})
        workerQueue.put(addr)
        if backupon:
        	backUp = backUpQueue.get()
        	message = {'task': 'reducePopUpdate', 'info': message}
        	send(backUp, message)
        	backUpMessage = {'task': 'backup', 'addr': backUp}
        	send(addr, backUpMessage)
        	backUpQueue.put(backUp)

def makeFile():
    newList = wordCount.items()
    #É criado um ficheiro csv com todas as palavras e o numero de vezes encontradas no texto lido com, ordenadas por ordem alfabetica 
    with args.out as f:
        csv_writer = csv.writer(f, delimiter=',',
        quotechar='"', quoting=csv.QUOTE_MINIMAL)
        #sorting alphabetically
        final = sorted(wordCount, key=functools.cmp_to_key(locale.strcoll))
        for w in final:
            csv_writer.writerow([w,wordCount[w]])
    logger.info("Shutting down")

def send(address, o):
	p = pickle.dumps(o)
	coordinatorSocket.sendto(p, address)

def recv():
        try:
            p, addr = coordinatorSocket.recvfrom(4096)
        except socket.timeout:
            return None, None
        else:
            if len(p) == 0:
                return None, addr
            else:
                return p, addr

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='MapReduce Coordinator')
    parser.add_argument('-p', dest='port', type=int, help='coordinator port', default=8765)
    parser.add_argument('-f', dest='file', type=argparse.FileType('r', encoding='UTF-8'), help='input file path')
    parser.add_argument('-o', dest='out', type=argparse.FileType('w', encoding='UTF-8'), help='output file path', default='output.csv')
    parser.add_argument('-b', dest ='blob_size', type=int, help='blob size', default=1024)
    args = parser.parse_args()
    
    main(args)
