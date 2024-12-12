#!/bin/bash
touch song{1,2,3,4,5,6}.mp3
touch snap{1,2,3,4,5,6}.jpg
touch film{1,2,3,4,5,6}.mp4

mkdir -p Music
mkdir -p Pictures
mkdir -p Videos

mv *.mp3 Music/
mv *.jpg Pictures/
mv *.mp4 Videos/
