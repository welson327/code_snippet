## 以WordFreq為例

假設兩個tsv檔案，分別記錄字頻  
**1.tsv**
```
word1    100
word2    200
```
**2.tsv**
```
word2    300
word3    400
```
想要合併成**merged.tsv**，怎麼做呢？
```
word1    100
word2    500
word3    400
```

請見[MergeWordFreqJob.java](MergeWordFreqJob.java)

PS:
* 如果是要處理字串，只要將java檔內的`IntWritable`換成Text即可
