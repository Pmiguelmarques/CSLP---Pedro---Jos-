#!/bin/bash

declare -g -A values #global associative array with directories and their used space
declare -g -A args # global associative array with script options and arguments
declare -g directories=() # global array with all input directories
declare -g -A files # global array with all files and their size
declare -g sortedArray
declare -g sortedDir
counter=0
function parse_arguments () {
    while getopts "n:l:d:L:ra" o; do
        
        case $o in
            n|r|a)
            ;;
            d)
                # converts argument date to number of seconds since 1970-01-01 00:00:00 UTC
                OPTARG=$(date -d "$OPTARG" +%s)
            ;;
            l)
                if [ ${args[L]} ]; then
                    echo "Can't use -l and -L at the same time"
                    exit
                fi
            ;;
            L)
                if [ ${args[l]} ]; then
                    echo "Can't use -l and -L at the same time"
                    exit
                fi
            ;;
            *)
                exit
            ;;
        esac
        args[$o]=$OPTARG # save all options in array
    done
    
    shift $((OPTIND - 1))
    directories=("$@") # save directories
}

function tts(){
    local dirSize=0
    local subDirSize=0
    for f in $1/*; do
    local -A currDirFiles
        if [[ -f $f ]]; then
            # ignore file if doesn't match with -n regex
            if [ ${args[n]} ] && [[ ! "$(basename "$f")" =~ ${args[n]} ]]; then
                continue
            fi
            if [ ${args[d]} ]; then
                # converts file last access date to number of seconds since 1970-01-01 00:00:00 UTC
                fileDate=$(date -d "$(stat "$f" | tail -4 | head -1 | cut -d " " -f2)" +%s)
                # ignore file if doesn't match with -d date
                if ! [ ${args[d]} -ge $fileDate ]; then
                    continue
                fi
            fi
            local fileSize=$(stat --format=%s $f)
            dirSize=$((dirSize + fileSize))
            files[$f]=$fileSize

            if [ ${args[l]} ]; then
                # save an array with all files in current directory and their space
                currDirFiles[$f]=$fileSize
            fi
        fi
        if [[ -d $f ]]; then
            local dir=$f
            tts $f
            subDir=${values[$dir]}
            subDirSize=$((subDirSize + subDir))
        fi
    done

    totalSpace=$((dirSize + subDirSize))
    values[$1]=$totalSpace

    if [ ${args[l]} ]; then
        # remove files and space that won't follow -l option (are smaller than the ${args[l]} larger)
        local -A filesToRemove=()
        local numFilesToRemove=$((${#currDirFiles[@]} - ${args[l]}))
        for e in ${!currDirFiles[@]}; do
            if [ ${#filesToRemove[@]} -lt $numFilesToRemove ]; then
                filesToRemove[$e]=${currDirFiles[$e]}
            else 
                for s in ${!filesToRemove[@]}; do
                    if [ ${currDirFiles[$e]} -lt ${filesToRemove[$s]} ]; then
                        unset filesToRemove[$s]
                        filesToRemove[$e]=${currDirFiles[$e]}
                        continue
                    fi
                done
            fi
        done
        # remove elements from array and update spaces
        local ftrSum=0
        for ftr in ${!filesToRemove[@]}; do
            if [ ${filesToRemove[$ftr]} ] && [ ${files[$ftr]} ]; then
                unset files[$ftr]
                ftrSum=$((ftrSum + ${filesToRemove[$ftr]}))
            fi
        done
        echo "$ftrSum"
        values[$1]=$((${values[$1]} - $ftrSum))
    fi
}

parse_arguments "$@"

for dir in "${directories[@]}"; do
    if [ -d $dir ]; then
        if [ ${args[l]} ];then
            tts $dir
            max=0
            while [ "$counter" -lt ${args[l]} ]; do
                for v in "${!values[@]}"; do
                    if (( "${values[$v]}" >= $max ));then
                        max=${values[$v]}
                        index=$v
                    fi 
                done
                if ! [ -z $v ];then
                    files[$index]=0
                    sortedDir[$counter]=$index
                    sortedArray[$counter]=$max
                    max=0
                fi
                counter=$((counter+1))
            done
            if [ ${args[a]} ] && ! [ ${args[r]} ]; then
                tts $dir
                for K in "${!sortedArray[@]}"; do
                    echo "${sortedArray[$K]} --- ${sortedDir[$K]}";
                done | sort -k2 
            elif ! [ ${args[a]} ] && [ ${args[r]} ]; then
                tts $dir
                for K in "${!sortedArray[@]}"; do
                    echo "${sortedArray[$K]} --- ${sortedDir[$K]}";
                done | sort -k1 -g
            elif [ ${args[a]} ] && [ ${args[r]} ];then
                tts $dir
                for K in "${!sortedArray[@]}"; do
                    echo "${sortedArray[$K]} --- ${sortedDir[$K]}";
                done | sort -k2 -r
            else
                tts $dir
                for K in "${!sortedArray[@]}"; do
                    echo "${sortedArray[$K]} --- ${sortedDir[$K]}";
                done | sort -k1 -gr
            fi
        elif [ ${args[L]} ];then
            tts $dir
            max=0
            while [ "$counter" -lt ${args[L]} ]; do
                for v in "${!files[@]}"; do
                    if (( "${files[$v]}" >= $max ));then
                        max=${files[$v]} 
                        index=$v
                    fi 
                done
                if ! [ -z $v ];then
                    files[$index]=0
                    sortedDir[$counter]=$index
                    sortedArray[$counter]=$max
                    max=0
                fi
                counter=$((counter+1))
            done
            if [ ${args[a]} ] && ! [ ${args[r]} ]; then
                tts $dir
                for K in "${!sortedArray[@]}"; do
                    echo "${sortedArray[$K]} --- ${sortedDir[$K]}";
                done | sort -k2 
            elif ! [ ${args[a]} ] && [ ${args[r]} ]; then
                tts $dir
                for K in "${!sortedArray[@]}"; do
                    echo "${sortedArray[$K]} --- ${sortedDir[$K]}";
                done | sort -k1 -g
            elif [ ${args[a]} ] && [ ${args[r]} ];then
                tts $dir
                for K in "${!sortedArray[@]}"; do
                    echo "${sortedArray[$K]} --- ${sortedDir[$K]}";
                done | sort -k2 -r
            else
                tts $dir
                for K in "${!sortedArray[@]}"; do
                    echo "${sortedArray[$K]} --- ${sortedDir[$K]}";
                done | sort -k1 -gr
            fi
        else
            if [ ${args[a]} ] && ! [ ${args[r]} ]; then
                tts $dir
                for K in "${!values[@]}"; do
                    echo "${values[$K]} --- $K";
                done | sort -k2 
            elif ! [ ${args[a]} ] && [ ${args[r]} ]; then
                tts $dir
                for K in "${!values[@]}"; do
                    echo "${values[$K]} --- $K";
                done | sort -k1 -g
            elif [ ${args[a]} ] && [ ${args[r]} ];then
                tts $dir
                for K in "${!values[@]}"; do
                    echo "${values[$K]} --- $K";
                done | sort -k2 -r
            else
                tts $dir
                for K in "${!values[@]}"; do
                    echo "${values[$K]} --- $K";
                done | sort -k1 -gr
            fi
        fi
    fi
done
