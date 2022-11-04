#/bin/sh

str="hello world"

if [ -z "$1" ]
then
	echo "Usage: sh $0 n"
	echo "(n is an integer which means the number of repetition)"
else
	for i in $(seq 1 $1)
	do
		echo $str
	done
fi
