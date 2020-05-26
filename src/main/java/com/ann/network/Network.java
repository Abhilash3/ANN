package com.ann.network;

import com.ann.network.functions.Activation;
import com.ann.network.input.Input;
import com.ann.network.layer.InputLayer;
import com.ann.network.layer.Layer;
import com.ann.network.layer.OutputLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Network<E extends Input> {
    private final InputLayer<E> input;
    private final OutputLayer output;

    private Network(InputLayer<E> input, OutputLayer output) {
        this.input = input;
        this.output = output;
    }

    private Supplier<E>[] asSuppliers(E[] values) {
        return Arrays.stream(values).map(a -> (Supplier) () -> a).toArray(Supplier[]::new);
    }

    void register(E[] values) {
        input.register(asSuppliers(values));
    }

    double learn(double expected) {
        return output.learn(expected);
    }

    double evaluate() {
        return output.evaluate();
    }

    @Override
    public String toString() {
        return "[\n" + output.toString() + "\n]";
    }

    static class Builder<E extends Input> {
        private InputLayer<E> start;
        private List<Layer<Double>> hidden = new ArrayList<>();

        Builder(Activation function, int count) {
            this.start = new InputLayer<>(function, count);
        }

        private Layer<?> lastAdded() {
            return hidden.isEmpty() ? start : hidden.get(hidden.size() - 1);
        }

        void addLayer(Activation function, int count) {
            hidden.add(new Layer.Builder(function, count).build(lastAdded()));
        }

        Network<E> finalLayer(Activation function) {
            return new Network<>(start, new OutputLayer.Builder(function).build(lastAdded()));
        }
    }
}
