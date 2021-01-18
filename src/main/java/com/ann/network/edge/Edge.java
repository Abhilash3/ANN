package com.ann.network.edge;

import com.ann.network.input.Input;
import com.ann.network.layer.vertex.Vertex;

public abstract class Edge<E extends Input> {
    static final double LEARNING_RATE = 0.5;

    Vertex<?, E> source;
    double weight = Math.random() - 0.5;
    private boolean isDirty = true;
    private Vertex<E, ?> destination;
    E result = null;

    public void setSource(Vertex<?, E> source) {
        this.source = source;
        triggerDirty();
    }

    public void setDestination(Vertex<E, ?> destination) {
        this.destination = destination;
        triggerDirty();
    }

    abstract E evaluateFull();

    public void triggerDirty() {
        if (isDirty) return;
        isDirty = true;
        if (destination != null) destination.triggerDirty();
    }

    public E evaluate() {
        if (isDirty) {
            result = evaluateFull();
            isDirty = false;
        }
        return result;
    }

    public void learn(double loss, double derivation) {
    }

    @Override
    public String toString() {
        return String.format("(%s * %f)", source.toString(), weight);
    }
}
