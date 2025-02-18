package com.itmo.vk.lab4.job.analyze;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Sell implements Writable {
    private double revenue;
    private int quantity;

    // needs for working
    @SuppressWarnings("unused")
    public Sell() {}

    public Sell(double revenue, int quantity) {
        this.revenue = revenue;
        this.quantity = quantity;
    }

    public double getRevenue() {
        return revenue;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void addRevenue(double revenue) {
        this.revenue += revenue;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(revenue);
        dataOutput.writeInt(quantity);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.revenue = dataInput.readDouble();
        this.quantity = dataInput.readInt();
    }

    @Override
    public String toString() {
        return revenue + "\t" + quantity;
    }
}
