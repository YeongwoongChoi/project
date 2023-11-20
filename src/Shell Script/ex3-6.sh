#/bin/sh

makeDirectory() {
	eval "mkdir -m 777 $1"

	for i in 0 1 2 3 4
	do
		fileName="file$i.txt"
		eval "touch $1/$fileName"
		echo $fileName
	done
	
	eval "tar -cf $1.tar $1"
	eval "mv ./$1.tar $1/$1.tar"
	eval "cd $1"
	eval "tar -xf $1.tar"
	eval "mv ./$1.tar ./$1/$1.tar"
	return
}

makeDirectory $1
exit 0
