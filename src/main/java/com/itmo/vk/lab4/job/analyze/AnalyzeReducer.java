package com.itmo.vk.lab4.job.analyze;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AnalyzeReducer extends Reducer<Text, Sell, Text, Sell> {
    @Override
    protected void reduce(Text key, Iterable<Sell> values, Reducer<Text, Sell, Text, Sell>.Context context) throws IOException, InterruptedException {
        Sell sell = new Sell(0, 0);
        for (Sell current : values) {
            sell.addQuantity(current.getQuantity());
            sell.addRevenue(current.getRevenue());
        }
        context.write(new Text(key), sell);
    }
}
