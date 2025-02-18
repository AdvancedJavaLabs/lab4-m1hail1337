package com.itmo.vk.lab4.job.sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

import com.itmo.vk.lab4.job.analyze.Sell;

public class SortMapper extends Mapper<Object, Text, SortingKey, Sell> {
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] parts = value.toString().split("\t");
        if (parts.length < 2) {
            return;
        }
        String category = parts[0];
        double revenue = Double.parseDouble(parts[1]);
        int quantity = Integer.parseInt(parts[2]);
        context.write(new SortingKey(revenue, category), new Sell(revenue, quantity));
    }
}
