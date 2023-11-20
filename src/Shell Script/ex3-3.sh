#/bin/sh

weight=$1
height=`echo "scale=2; $2 / 100" | bc`
bmi=`echo "scale=2; $weight / ($height * $height)" | bc`

if [ `echo "scale=2; $bmi >= 23.0" | bc -l` = 1 ] ; then
	echo "과체중입니다."
elif [ `echo "scale=2; $bmi >= 18.5" | bc -l` = 1 ] && [ `echo "scale=2; $bmi < 23.0" | bc -l` = 1 ] ; then
	echo "정상체중입니다."
else
	echo "저체중입니다."
fi
