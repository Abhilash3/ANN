package com.ann.network.layer.vertex;

import com.ann.network.functions.Activation;
import com.ann.network.input.Input;

import java.util.List;

public class InputVertex<E extends Input, F extends Input> extends Vertex<E, F> {

    public InputVertex(Activation func) {
        super(func, Input::toDouble);
    }

    @Override
    protected E aggregate(List<E> values) {
        return values.get(0);
    }

    @Override
    public String toString() {
        return "I";
    }
}
