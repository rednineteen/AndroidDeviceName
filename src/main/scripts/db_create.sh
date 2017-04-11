#!/bin/bash
CSV_FILE_NAME=supported_devices.csv
GOOGLE_SUPPORTED_DEVICES_URL=http://storage.googleapis.com/play_public/supported_devices.csv
SQLITE_DB_NAME=supported_devices.sql3
SQLITE_DB=../assets/$SQLITE_DB_NAME
# downlaod the CSV file from the official google support site.
wget -O $CSV_FILE_NAME $GOOGLE_SUPPORTED_DEVICES_URL

dos2unix $CSV_FILE_NAME
# Delete previous DB file
rm -f $SQLITE_DB

# Create DB
sqlite3 $SQLITE_DB "CREATE TABLE device (brand TEXT,market_name TEXT,device TEXT,model TEXT);"

# Read CSV file with supported devices and insert into the DB
insert_stmt=""
i=1
while IFS=,$'\t\n\r' read Brand Name Device Model
do
	test $i -eq 1 && ((i=i+1)) && continue
	
	Brand=${Brand//[\'\"\`]}
	Name=${Name//[\'\"\`]}
	Device=${Device//[\'\"\`]}
	Model=${Model//[\'\"\`]}
	
	#insert_stmt+="INSERT INTO device (brand,market_name,device,model) values ('$Brand','$Name','$Device','$Model'); "
	sqlite3 $SQLITE_DB "INSERT INTO device (brand,market_name,device,model) values ('$Brand','$Name','$Device','$Model'); "
	#echo "Brand: $Brand, Name: $Name, Device: $Device, Model: $Model"
	echo "INSERT INTO device (brand,market_name,device,model) values ('$Brand','$Name','$Device','$Model'); "

done < $CSV_FILE_NAME


#echo $insert_stmt > temp.txt
#cat temp.txt | sqlite3 $SQLITE_DB