package com.ann.network.edge;

public class BiasEdge extends DoubleEdge {
    private static final double BIAS = 1;

    public BiasEdge() {
        super(null);
    }

    @Override
    public void learn(double loss, double derivative) {
        isDirty = true;
        weight += LEARNING_RATE * loss * derivative;
    }

    @Override
    protected Double result() {
        return BIAS * weight;
    }
}
