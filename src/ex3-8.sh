#/bin/sh

putInfo() {

	file="DB.txt"
	
	if [ ! -e $file ]
	then
		eval "touch $file"
	fi
	echo $1 $2 >> $file
}

putInfo $1 $2
exit 0
