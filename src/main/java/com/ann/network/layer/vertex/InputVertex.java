package com.ann.network.layer.vertex;

import com.ann.network.functions.Activation;
import com.ann.network.input.Input;

import java.util.List;

public class InputVertex<E extends Input> extends Vertex<E> {

    public InputVertex(Activation func) {
        super(func, Input::toDouble);
    }

    @Override
    protected E aggregate(List<E> values) {
        return values.get(0);
    }
}
