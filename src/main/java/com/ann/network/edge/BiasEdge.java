package com.ann.network.edge;

import com.ann.network.input.Numeric;

public class BiasEdge extends DoubleEdge {
    private static final double BIAS = 1;

    @Override
    public void learn(double loss, double derivative) {
        weight += LEARNING_RATE * loss * derivative;
        triggerDirty();
    }

    @Override
    protected Numeric evaluateFull() {
        return new Numeric(BIAS * weight);
    }

    @Override
    public String toString() {
        return String.format("B(%f)", weight);
    }
}
