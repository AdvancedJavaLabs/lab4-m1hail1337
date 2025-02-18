package com.itmo.vk.lab4.job.analyze;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AnalyzeMapper extends Mapper<LongWritable, Text, Text, Sell> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Sell>.Context context) throws IOException, InterruptedException {
        String[] params = value.toString().split(",");
        if (key.get() != 0 && params.length == 5) {
            int quantity = Integer.parseInt(params[4]);
            Sell sell = new Sell(Double.parseDouble(params[3]) * quantity, quantity);
            context.write(new Text(params[2]), sell);
        }
    }
}
