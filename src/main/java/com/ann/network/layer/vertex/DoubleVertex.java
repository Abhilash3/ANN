package com.ann.network.layer.vertex;

import com.ann.network.edge.BiasEdge;
import com.ann.network.functions.Activation;
import com.ann.network.input.Numeric;

import java.util.List;

public class DoubleVertex extends Vertex<Numeric, Numeric> {
    public DoubleVertex(Activation func) {
        super(func, Numeric::toDouble);
        sources.add(new BiasEdge());
    }

    @Override
    protected Numeric aggregate(List<Numeric> values) {
        return new Numeric(values.stream().mapToDouble(Numeric::toDouble).sum());
    }

    @Override
    public void learn(double loss) {
        double derivative = func.derivative(evaluate());
        sources.forEach(a -> a.learn(loss, derivative));
        triggerDirty();
    }
}
