#!/bin/bash

echo "Press <C-c> when you want to stop downloading pages from wikipedia, about \
	a minute should be enough to generate files meaningful for test"

wget -e robots=off --mirror --convert-links --adjust-extension --page-requisites --no-parent --no-host-directories --restrict-file-names=windows --base=./ https://en.wikipedia.org/wiki/Massimo_Boldi &> /dev/null
