package com.ann.network.edge;

import com.ann.network.input.Input;

import java.util.function.Supplier;

public class InputEdge<E extends Input> extends Edge<E> {
    private Supplier<E> supplier;

    public InputEdge() {
        super(null);
    }

    public void register(Supplier<E> supplier) {
        isDirty = true;
        this.supplier = supplier;
    }

    @Override
    E result() {
        E result = null;
        if (supplier != null) {
            result = supplier.get();
            supplier = null;
        }
        return result;
    }

    @Override
    public String toString() {
        return "[I]->";
    }
}
