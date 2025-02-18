package com.itmo.vk.lab4.job.sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

import com.itmo.vk.lab4.job.analyze.Sell;

public class SortReducer extends Reducer<SortingKey, Sell, Text, Sell> {
    @Override
    public void reduce(SortingKey key, Iterable<Sell> values, Context context) throws IOException, InterruptedException {
        for (Sell value : values) {
            context.write(new Text(key.getCategory()), value);
        }
    }
}
