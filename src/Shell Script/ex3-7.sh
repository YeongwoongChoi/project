#/bin/sh

makeFile() {
	if [ -e $1 ]
	then
		eval "rm -r $1"
	fi
	eval "mkdir -m 777 $1"
	eval "cd $1"

	for i in 0 1 2 3 4
	do
		fileName="file$i"
		eval "touch $fileName.txt"
		eval "mkdir $fileName"
		eval "cd $fileName"
		eval "ln -s $fileName.txt $fileName.txt"
		eval "cd .."
	done
	return
}

makeFile $1
exit 0
