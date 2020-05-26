package com.ann.network.edge;

import com.ann.network.layer.vertex.Vertex;

public abstract class Edge<E> {
    static final double LEARNING_RATE = 0.25;

    Vertex<?> source;
    double weight = Math.random() - 0.5;

    boolean isDirty = true;
    private E result = null;

    Edge(Vertex<?> source) {
        this.source = source;
    }

    abstract E result();

    public boolean isDirty() {
        return isDirty || (source != null && source.isDirty());
    }

    public E evaluate() {
        if (isDirty()) {
            isDirty = false;
            result = result();
        }
        return result;
    }

    public void learn(double loss, double derivation) {
    }

    @Override
    public String toString() {
        return source == null ? "" : String.format("%s-[%f]->", source.toString(), weight);
    }
}
