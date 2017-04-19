#!/bin/bash
CSV_FILE_NAME=supported_devices.csv
sed -i "s/\\\t//g" $CSV_FILE_NAME