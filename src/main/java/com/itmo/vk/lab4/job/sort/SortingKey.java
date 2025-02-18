package com.itmo.vk.lab4.job.sort;

import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SortingKey implements WritableComparable<SortingKey> {
    private double revenue;
    private String category;

    // needs for working
    @SuppressWarnings("unused")
    public SortingKey() {}

    public SortingKey(double revenue, String category) {
        this.revenue = revenue;
        this.category = category;
    }

    // needs for working
    @SuppressWarnings("unused")
    public double getRevenue() {
        return revenue;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeDouble(revenue);
        out.writeUTF(category);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        revenue = in.readDouble();
        category = in.readUTF();
    }

    @Override
    public int compareTo(SortingKey o) {
        int cmp = Double.compare(o.revenue, this.revenue);
        if (cmp != 0) {
            return cmp;
        }
        return this.category.compareTo(o.category);
    }

    @Override
    public String toString() {
        return category + "\t" + revenue;
    }
}

