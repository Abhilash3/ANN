package com.ann.network.edge;

import com.ann.network.input.Input;

public class InputEdge<E extends Input> extends Edge<E> {

    public void register(E result) {
        this.result = result;
        triggerDirty();
    }

    @Override
    E evaluateFull() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("I(%f)", weight);
    }
}
