#!/bin/bash

FILES=$(ls *.fs)

for f in ${FILES}
do
	if java -jar ../dist/Part2.jar ${f} > /dev/null; then
		echo -e "\033[0;32mTest to fs file: ${f} sucessfull!\033[0m"
	else
		echo -e "\033[0;31mTest to fs file: ${f} failed!\033[0m"
	fi
	echo "------------------------------------------------"
done
