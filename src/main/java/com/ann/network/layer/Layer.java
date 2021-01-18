package com.ann.network.layer;

import com.ann.network.edge.DoubleEdge;
import com.ann.network.edge.Edge;
import com.ann.network.layer.vertex.DoubleVertex;
import com.ann.network.layer.vertex.Vertex;
import com.ann.network.functions.Activation;
import com.ann.network.input.Input;
import com.ann.network.input.Numeric;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Layer<E extends Input, F extends Input> {
    Vertex<E, F>[] vertices;

    Layer(Vertex<E, F>[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return Arrays.stream(vertices).parallel().map(Object::toString).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
    }

    public static class Builder {
        private Vertex<Numeric, Numeric>[] vertices;

        public Builder(Activation function, int count) {
            vertices = new DoubleVertex[count];
            IntStream.range(0, count).forEach(a -> vertices[a] = new DoubleVertex(function));
        }

        public Layer<Numeric, Numeric> build(Layer<?, Numeric> source) {
            Arrays.asList(vertices).forEach(a -> Arrays.asList(source.vertices).forEach(b -> {
                Edge<Numeric> edge = new DoubleEdge();
                a.addSource(edge);
                b.addDestination(edge);
            }));
            return new Layer<>(vertices);
        }
    }
}
