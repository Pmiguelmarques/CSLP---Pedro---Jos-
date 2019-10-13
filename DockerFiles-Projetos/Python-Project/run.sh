#!/bin/bash
set -e


python3 coordinator.py -f "lusiadas.txt" &
sleep .5
python3 worker.py 
