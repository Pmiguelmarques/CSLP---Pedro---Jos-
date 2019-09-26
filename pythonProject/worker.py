# coding: utf-8
#Made: Pedro Marques 89069 e Flávia Figueiredo 88887 

import string
import logging
import argparse
import socket
import pickle

logging.basicConfig(level=logging.DEBUG,format='%(asctime)s %(name)-12s %(levelname)-8s %(message)s',datefmt='%m-%d %H:%M:%S')
logger = logging.getLogger('worker')
workerSocket = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)

def tokenizer(txt):
    tokens = txt.lower()
    tokens = tokens.translate(str.maketrans('', '', string.digits))
    tokens = tokens.translate(str.maketrans('', '', string.punctuation))
    tokens = tokens.translate(str.maketrans('', '', '«»'))
    tokens = tokens.rstrip()
    return tokens.split()

def main(args):
    address = ('localhost', 5003 + args.id)
    coordinatorAddress = ('localhost', args.port)
    workerSocket.bind(address)
    workerSocket.settimeout(10)
    logger.debug('Connecting %d to %s:%d', args.id, args.hostname, args.port)
    backUpAddr = None
    registerMessage = {'task': 'register', 'info': address}
    done = False
    send(coordinatorAddress, registerMessage)
    while not done:
        try:
            p, addr = recv()
            message = pickle.loads(p)
            if message['task'] == 'goodbye':
                done = True 
            if message['task'] == 'registerBackup':
                backUpAddr = message['info']
            if message['task'] == 'toMap':
                mapReady = Map(message['info'])
                toSend = ({"task": 'map', "info": mapReady})
                send(coordinatorAddress, toSend)
            if message['task'] == 'toReduce':
                reduceReady = Reduce(message['info'])
                toSend = ({"task": 'reduce', 'info': reduceReady})
                send(coordinatorAddress, toSend)
            if message['task'] == 'backup':
                backUpAddr = message['addr']
        except:
            if backUpAddr != None:
                message = {'task': 'register', 'info': address}
                coordinatorAddress = (('localhost', args.port + 1))
                send(coordinatorAddress, message)
            else:
                done = True
    logger.info("Shutting Down")

def Map(string):
    wordRecord = list()                                         #Vai ser retornada uma lista de tuplos (não seria possível mapear com um dicionário pois as palavras que se repetem no texto                                                             # não seriam inseridas mais que uma vez, pois as keys não podem ser repetidas no dict)
    words = tokenizer(string)                                   #Separa em palavras
    for word in words:
        word = word.lower()                                     #Mudar para minúsculas -> "Olá" = "olá"
        wordRecord.append((word,1))                             #Adiciona à lista
        #Testing (console)
        loggerTest = dict(wordRecord)                           #list to dict
        logger.info("MAP: %s -> %d", word,loggerTest.get(word))
    return wordRecord

def Reduce(firstList):
    reducedWordRecord = {}                                     #Vai ser retornada um dicionário em que a KEY é a palavra e o VALUE é as vezes que se repete no texto
    for list_tuple in firstList:
        key,y = list_tuple                                      #Retirar do tuplo a key ( y = 1 sempre )
        #Se a key já estiver no dicionário, então é necessário buscar o value dessa key no dict e incrementar
        if key in reducedWordRecord:
            reducedWordRecord[key] = reducedWordRecord.get(key,0) + 1
        #Caso não se encontre no dic, adicionar
        else:
            reducedWordRecord[key] = 1
        #Testing (Console)    
        logger.info("REDUCE: %s -> %d",key,reducedWordRecord.get(key))

    return reducedWordRecord

def send(address, o):
    p = pickle.dumps(o)
    workerSocket.sendto(p, address)

def recv():
        try:
            p, addr = workerSocket.recvfrom(4096)
        except socket.timeout:
            return None, None
        else:
            if len(p) == 0:
                return None, addr
            else:
                return p, addr

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='MapReduce worker')
    parser.add_argument('--id', dest='id', type=int, help='worker id', default=0)
    parser.add_argument('--port', dest='port', type=int, help='coordinator port', default=8765)
    parser.add_argument('--hostname', dest='hostname', type=str, help='coordinator hostname', default='localhost')
    args = parser.parse_args()
    
    main(args)
