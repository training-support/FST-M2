-- Load Data from HDFS
inputDialogues4 = LOAD 'hdfs:///user/amey/inputs/episodeIV_dialogues.txt' USING PigStorage('\t') AS (name:chararray, line:chararray);
inputDialogues5 = LOAD 'hdfs:///user/amey/inputs/episodeV_dialogues.txt' USING PigStorage('\t') AS (name:chararray, line:chararray);
inputDialogues6 = LOAD 'hdfs:///user/amey/inputs/episodeVI_dialogues.txt' USING PigStorage('\t') AS (name:chararray, line:chararray);

-- Filter out the first 2 lines from each file
ranked4 = RANK inputDialogues4;
Dialogues4 = FILTER ranked4 BY (rank_inputDialogues4 > 2);
ranked5 = RANK inputDialogues5;
Dialogues5 = FILTER ranked5 BY (rank_inputDialogues5 > 2);
ranked6 = RANK inputDialogues6;
Dialogues6 = FILTER ranked6 BY (rank_inputDialogues6 > 2);

-- Merging all 3 input files
mergedInputData = UNION Dialogues4,Dialogues5,Dialogues6;

-- Group by name
groupByName = GROUP mergedInputData BY name;

-- Count the number of line by each character
names = FOREACH groupByName GENERATE $0 as name, COUNT($1) as no_of_lines;
nameOrdered = ORDER names BY no_of_lines DESC;

-- Remove the existing output folder
rmf hdfs:///user/amey/outputs;

-- Store Result in HDFS
STORE nameOrdered INTO 'hdfs:///user/amey/outputs' USING PigStorage('\t');