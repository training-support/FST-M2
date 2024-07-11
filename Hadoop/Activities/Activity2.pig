-- Load input file from HDFS
inputFile = LOAD 'hdfs:///user/Amey/input.txt' AS (line:chararray);
-- Tokeize each word in the file (Map)
words = FOREACH inputFile GENERATE FLATTEN(TOKENIZE(line)) AS word;
-- Combine the words from the above stage
grpd = GROUP words BY word;
-- Count the occurence of each word (Reduce)
totalCount = FOREACH grpd GENERATE $0 as word, COUNT($1) as wordCount;
-- Remove the old folder
rmf hdfs:///user/Amey/pigOutput1;
-- Store the result in HDFS
STORE totalCount INTO 'hdfs:///user/Amey/pigOutput1';