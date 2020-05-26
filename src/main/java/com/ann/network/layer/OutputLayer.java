package com.ann.network.layer;

import com.ann.network.edge.DoubleEdge;
import com.ann.network.functions.Activation;
import com.ann.network.layer.vertex.DoubleVertex;
import com.ann.network.layer.vertex.Vertex;

import java.util.Arrays;

public class OutputLayer extends Layer<Double> {
    private OutputLayer(Vertex<Double> vertex) {
        super(new Vertex[]{vertex});
    }

    public double learn(double expected) {
        double actual = evaluate();
        vertices[0].learn(expected - actual);
        return actual;
    }

    public double evaluate() {
        return vertices[0].evaluate();
    }

    public static class Builder {
        private Vertex<Double> vertex;

        public Builder(Activation function) {
            this.vertex = new DoubleVertex(function);
        }

        public OutputLayer build(Layer<?> source) {
            Arrays.asList(source.vertices).forEach(a -> vertex.addSource(new DoubleEdge(a)));
            return new OutputLayer(vertex);
        }
    }
}
