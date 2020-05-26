package com.ann.network.input;

public class Numeric implements Input {
    private Number n;

    public Numeric(Number n) {
        this.n = n;
    }

    @Override
    public double toDouble() {
        return n.doubleValue();
    }

    @Override
    public int toInt() {
        return n.intValue();
    }

    @Override
    public String toString() {
        return n.toString();
    }
}
