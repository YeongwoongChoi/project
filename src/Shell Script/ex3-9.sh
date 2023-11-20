#/bin/sh

search() {
	pattern=$1
	file="DB.txt"
	eval "find . -name $file | xargs grep $pattern"
}

search $1
exit 0
