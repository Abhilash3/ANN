package com.ann.network.edge;

import com.ann.network.input.Numeric;

public class DoubleEdge extends Edge<Numeric> {

    @Override
    public void learn(double loss, double derivative) {
        weight += LEARNING_RATE * loss * source.evaluate() * derivative;
        source.learn(loss);
        triggerDirty();
    }

    @Override
    protected Numeric evaluateFull() {
        return new Numeric(source.evaluate() * weight);
    }
}
