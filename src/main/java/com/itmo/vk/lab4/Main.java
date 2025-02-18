package com.itmo.vk.lab4;

import java.io.*;

import com.itmo.vk.lab4.job.analyze.AnalyzeMapper;
import com.itmo.vk.lab4.job.analyze.AnalyzeReducer;
import com.itmo.vk.lab4.job.analyze.Sell;
import com.itmo.vk.lab4.job.sort.SortingKey;
import com.itmo.vk.lab4.job.sort.SortMapper;
import com.itmo.vk.lab4.job.sort.SortReducer;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Main {
    private static final String ANALYZE_OUTPUT_PATH = "analyze_output";
    private static final Configuration CONF = new Configuration();

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        clearTargetPaths(args[1]);
        performAnalyzeJob(args[0]);
        performSortJob(args[1]);
    }

    private static void clearTargetPaths(String outputPath) throws IOException {
        FileUtils.deleteDirectory(new File(outputPath));
        FileUtils.deleteDirectory(new File(ANALYZE_OUTPUT_PATH));
    }

    private static void performAnalyzeJob(String input) throws IOException, InterruptedException, ClassNotFoundException {
        Job analyzeStep = Job.getInstance(CONF, "Analyze job");
        analyzeStep.setMapperClass(AnalyzeMapper.class);
        analyzeStep.setReducerClass(AnalyzeReducer.class);
        analyzeStep.setMapOutputKeyClass(Text.class);
        analyzeStep.setMapOutputValueClass(Sell.class);
        analyzeStep.setOutputKeyClass(Text.class);
        analyzeStep.setOutputValueClass(Sell.class);

        FileInputFormat.addInputPath(analyzeStep, new Path(input));
        FileOutputFormat.setOutputPath(analyzeStep, new Path(ANALYZE_OUTPUT_PATH));

        if (!analyzeStep.waitForCompletion(true)) {
            System.exit(1);
        }
    }

    private static void performSortJob(String output) throws IOException, InterruptedException, ClassNotFoundException {
        Job sortStep = Job.getInstance(CONF, "Sorting job");
        sortStep.setMapperClass(SortMapper.class);
        sortStep.setReducerClass(SortReducer.class);
        sortStep.setMapOutputKeyClass(SortingKey.class);
        sortStep.setMapOutputValueClass(Sell.class);
        sortStep.setOutputKeyClass(Text.class);
        sortStep.setOutputValueClass(Sell.class);

        FileInputFormat.addInputPath(sortStep, new Path(ANALYZE_OUTPUT_PATH));
        FileOutputFormat.setOutputPath(sortStep, new Path(output));

        System.exit(sortStep.waitForCompletion(true) ? 0 : 1);
    }
}
