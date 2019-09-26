#!/bin/bash

echo "Caso -a pasta:"

bash totalspace.sh -a $1

echo "=================================================================="

echo "Caso -r pasta:"

bash totalspace.sh -r $1

echo "=================================================================="

echo "Caso -a -r pasta:"

bash totalspace.sh -a -r $1

echo "=================================================================="

echo "Caso -r -a pasta:"

bash totalspace.sh -a -r $1

echo "=================================================================="

echo "Caso -l 10 pasta:"

bash totalspace.sh -l 10 $1

echo "=================================================================="

echo "Caso -L 50 pasta:"

bash totalspace.sh -L 10 $1

echo "=================================================================="

echo "Caso -L 50 pasta:"

bash totalspace.sh -L 10 $1

echo "=================================================================="

echo "Caso -n .*sh pasta:"

bash totalspace.sh -n ".*sh" $1

echo "=================================================================="

echo "Caso -d Sep 10 2018 pasta:"

bash totalspace.sh -d "Sep 10 2018" $1

echo "=================================================================="

echo "Caso -n .*sh -d Sep 10 2018 pasta:"

bash totalspace.sh -n ".*sh" -d "Sep 10 2018" $1

echo "=================================================================="

echo "Caso -a -r - l 4 -n .*sh -d Sep 10 2018 pasta:"

bash totalspace.sh -a -r -l 4 -n ".*txt" -d "Sep 10 2018" $1

echo "=================================================================="

echo "Caso -a -r -L 4 -n .*sh -d Sep 10 2018 pasta:"

bash totalspace.sh -a -r -L 4 -n ".*sh" -d "Sep 10 2018" $1

echo "=================================================================="

echo "Caso -a -r -L 3 -l 4 -n .*sh -d Sep 10 2018 pasta:"

bash totalspace.sh -a -r -L 3 -l 4 -n ".*sh" -d "Sep 10 2018" $1


