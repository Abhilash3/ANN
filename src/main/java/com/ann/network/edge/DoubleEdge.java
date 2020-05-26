package com.ann.network.edge;

import com.ann.network.layer.vertex.Vertex;

public class DoubleEdge extends Edge<Double> {
    public DoubleEdge(Vertex<?> vertex) {
        super(vertex);
    }

    @Override
    public void learn(double loss, double derivative) {
        weight += LEARNING_RATE * loss * source.evaluate() * derivative;
        isDirty = true;
        source.learn(loss);
    }

    @Override
    protected Double result() {
        return source.evaluate() * weight;
    }
}
