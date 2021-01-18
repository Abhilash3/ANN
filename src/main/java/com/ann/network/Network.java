package com.ann.network;

import com.ann.network.functions.Activation;
import com.ann.network.input.Input;
import com.ann.network.input.Numeric;
import com.ann.network.layer.InputLayer;
import com.ann.network.layer.Layer;
import com.ann.network.layer.OutputLayer;

import java.util.ArrayList;
import java.util.List;

public class Network<E extends Input, F extends Input> {
    private final InputLayer<E, F> input;
    private final OutputLayer output;

    private Network(InputLayer<E, F> input, OutputLayer output) {
        this.input = input;
        this.output = output;
    }

    private void register(E[] values) {
        input.register(values);
    }

    double learn(double expected, E[] values) {
        register(values);
        return output.learn(expected);
    }

    double evaluate(E[] values) {
        register(values);
        return output.evaluate();
    }

    @Override
    public String toString() {
        return "N[" + output.toString() + "]";
    }

    static class Builder<E extends Input> {
        private InputLayer<E, Numeric> start;
        private List<Layer.Builder> hidden;

        Builder(Activation function, int count) {
            start = new InputLayer<>(function, count);
            hidden = new ArrayList<>();
        }

        void addLayer(Activation function, int count) {
            hidden.add(new Layer.Builder(function, count));
        }

        Network<E, Numeric> finalLayer(Activation function) {
            Layer<?, Numeric> last = start;
            for (Layer.Builder b : hidden) {
                last = b.build(last);
            }
            return new Network<>(start, new OutputLayer.Builder(function).build(last));
        }
    }
}
