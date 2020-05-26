package com.ann.network.layer.vertex;

import com.ann.network.edge.BiasEdge;
import com.ann.network.functions.Activation;

import java.util.List;

public class DoubleVertex extends Vertex<Double> {
    public DoubleVertex(Activation func) {
        super(func, a -> a);
        sources.add(new BiasEdge());
    }

    @Override
    protected Double aggregate(List<Double> values) {
        return values.stream().mapToDouble(a -> a).sum();
    }

    @Override
    public void learn(double loss) {
        double derivative = func.derivative(evaluate());
        isDirty = true;
        sources.forEach(a -> a.learn(loss, derivative));
    }
}
