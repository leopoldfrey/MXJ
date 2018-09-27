clear

echo "____________________________________________________"
echo "Backup Mxj"

echo "..."

export DATE=`date "+%d-%m-%y_%Hh%M"`
export ZIP_NAME=./archives/Mxj_$1_$DATE.zip

for a in `find . -maxdepth 1`
do
	if [ $a != . ] && [ $a != ./archives ] && [ $a != ./backup.sh ] && [ $a != ./old ]
	then
		echo "    "Add $a to $ZIP_NAME
		zip -rgq9 $ZIP_NAME $a
	fi	
done

echo "Done"
echo "____________________________________________________"

