package com.ann.network.layer.vertex;

import com.ann.network.edge.Edge;
import com.ann.network.functions.Activation;
import com.ann.network.input.Input;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public abstract class Vertex<E extends Input, F extends Input> {

    Set<Edge<E>> sources = new HashSet<>();
    Activation func;
    private Set<Edge<F>> destinations = new HashSet<>();
    private boolean isDirty = true;
    private ToDoubleFunction<E> mapper;
    private double result;

    Vertex(Activation func, ToDoubleFunction<E> mapper) {
        this.func = func;
        this.mapper = mapper;
    }

    public void addSource(Edge<E> edge) {
        sources.add(edge);
        edge.setDestination(this);
        triggerDirty();
    }

    public void addDestination(Edge<F> edge) {
        destinations.add(edge);
        edge.setSource(this);
        if (isDirty) edge.triggerDirty();
    }

    public double evaluate() {
        if (isDirty) {
            result = func.apply(mapper.applyAsDouble(aggregate(sources.stream().map(Edge::evaluate).collect(Collectors.toList()))));
            isDirty = false;
        }
        return result;
    }

    public void triggerDirty() {
        if (isDirty) return;
        isDirty = true;
        destinations.forEach(Edge::triggerDirty);
    }

    public void learn(double loss) {
    }

    protected abstract E aggregate(List<E> values);

    @Override
    public String toString() {
        return "[" + sources.stream().map(Edge::toString).reduce((a, b) -> a + "," + b).orElse("") + "]";
    }
}
