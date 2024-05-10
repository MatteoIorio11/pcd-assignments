#!/bin/bash

cd ./wiki/
sed -i '' 's/href="\/wiki\/\([^"]*\)"/href=".\/\1.html"/g' *.html
