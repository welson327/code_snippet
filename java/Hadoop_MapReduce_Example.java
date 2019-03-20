package com.pchome.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.Iterator;

public class MergeWordFreqJob extends Configured implements Tool {
    private Configuration conf;

    public static class MyMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
        private Text outputKey;
        private IntWritable outputValue;

        @Override
        protected void setup(Context context) {
            outputKey = new Text();
            outputValue = new IntWritable();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] cols = line.split("\t");
            outputKey.set(cols[0]);
            outputValue.set(Integer.valueOf(cols[1]));
            context.write(outputKey, outputValue);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }

    }

    public static class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
        private Text outKey = new Text();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
        }

        @Override
        public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            outKey.set(key);
            context.write(outKey, new IntWritable(sum));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        conf = getConf();

        String inputFilePath1 = "/path/to/1.tsv";
        String inputFilePath2 = "/path/to/2.tsv";
        String outputFolder = "/path/to/output";

        // setup job
        Job job = Job.getInstance(this.conf, "MergeWordFreqJob");

        job.setJarByClass(MergeWordFreqJob.class); // 設定執行的Jar套件

        // Setup mapper
        job.setMapperClass(MyMapper.class);             // 設定Mapper類別
        job.setMapOutputKeyClass(Text.class);           // 設定Mapper Key輸出類型
        job.setMapOutputValueClass(IntWritable.class);  // 設定Mapper Value輸出類型
        // Setup reducer
        job.setReducerClass(MyReducer.class);           // 設定Reducer類別
        job.setOutputKeyClass(Text.class);              // 設定Key輸出類型
        job.setOutputValueClass(IntWritable.class);     // 設定Value輸出類型

        // Setup input/output format
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        // Setup input files
        MultipleInputs.addInputPath(job, new Path(inputFilePath1), TextInputFormat.class, MergeWordFreqJob.MyMapper.class);
        MultipleInputs.addInputPath(job, new Path(inputFilePath2), TextInputFormat.class, MergeWordFreqJob.MyMapper.class);

        // 設定輸入的 Key Value 類別
        //FileInputFormat.setInputPaths(job,new Path(inputFilePath));
        FileOutputFormat.setOutputPath(job,new Path(outputFolder));


        if (job.waitForCompletion(true)) {
            // do something
        }

        return job.isSuccessful() ? 0 : -1;
    }

    public static void main(String args[]) throws Exception {
        ToolRunner.run(new MergeWordFreqJob(), args);
    }
}
