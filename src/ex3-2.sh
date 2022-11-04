#/bin/sh

result=""

case $2 in
	"+")
		result=`expr $1 + $3`
		echo $result;;
	"-")
		result=`expr $1 - $3`
		echo $result;;
esac
