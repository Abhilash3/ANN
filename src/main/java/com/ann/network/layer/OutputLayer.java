package com.ann.network.layer;

import com.ann.network.edge.DoubleEdge;
import com.ann.network.edge.Edge;
import com.ann.network.layer.vertex.DoubleVertex;
import com.ann.network.layer.vertex.Vertex;
import com.ann.network.functions.Activation;
import com.ann.network.input.Numeric;

import java.util.Arrays;

public class OutputLayer extends Layer<Numeric, Numeric> {
    private OutputLayer(Vertex<Numeric, Numeric> vertex) {
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
        private Vertex<Numeric, Numeric> vertex;

        public Builder(Activation function) {
            this.vertex = new DoubleVertex(function);
        }

        public OutputLayer build(Layer<?, Numeric> source) {
            Arrays.asList(source.vertices).forEach(a -> {
                Edge<Numeric> edge = new DoubleEdge();
                vertex.addSource(edge);
                a.addDestination(edge);
            });
            return new OutputLayer(vertex);
        }
    }
}
