package com.ann.network.layer;

import com.ann.network.edge.InputEdge;
import com.ann.network.layer.vertex.InputVertex;
import com.ann.network.layer.vertex.Vertex;
import com.ann.network.functions.Activation;
import com.ann.network.input.Input;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public class InputLayer<E extends Input, F extends Input> extends Layer<E, F> {
    private Consumer<E>[] consumers;

    public InputLayer(Activation function, int count) {
        super(new Vertex[count]);

        consumers = new Consumer[count];
        IntStream.range(0, count).forEach(a -> {
            InputVertex<E, F> vertex = new InputVertex<>(function);
            InputEdge<E> edge = new InputEdge<>();
            vertex.addSource(edge);

            vertices[a] = vertex;
            consumers[a] = edge::register;
        });
    }

    public void register(E[] values) {
        IntStream.range(0, values.length).forEach(a -> consumers[a].accept(values[a]));
    }
}
