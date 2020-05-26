package com.ann.network.layer;

import com.ann.network.edge.DoubleEdge;
import com.ann.network.functions.Activation;
import com.ann.network.layer.vertex.DoubleVertex;
import com.ann.network.layer.vertex.Vertex;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Layer<E> {
    Vertex<E>[] vertices;

    Layer(Vertex<E>[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return Arrays.stream(vertices).parallel().map(Object::toString).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
    }

    public static class Builder {
        private Vertex<Double>[] vertices;

        public Builder(Activation function, int count) {
            vertices = new DoubleVertex[count];
            IntStream.range(0, count).forEach(a -> vertices[a] = new DoubleVertex(function));
        }

        public Layer<Double> build(Layer<?> source) {
            Arrays.asList(vertices).forEach(a -> Arrays.asList(source.vertices).forEach(b -> a.addSource(new DoubleEdge(b))));
            return new Layer<>(vertices);
        }
    }
}
