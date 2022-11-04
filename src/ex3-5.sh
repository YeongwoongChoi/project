#/bin/sh
Function () {
	echo "함수 안으로 들어왔음"
	str="ls "$1
	eval $str
	return
}
echo "프로그램을 시작합니다."
Function $1
echo "프로그램을 종료합니다."
exit 0
