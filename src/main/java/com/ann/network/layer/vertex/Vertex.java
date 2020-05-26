package com.ann.network.layer.vertex;

import com.ann.network.edge.Edge;
import com.ann.network.functions.Activation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public abstract class Vertex<E> {

    List<Edge<E>> sources = new ArrayList<>();
    Activation func;
    boolean isDirty = true;
    private ToDoubleFunction<E> mapper;
    private double result;

    Vertex(Activation func, ToDoubleFunction<E> mapper) {
        this.func = func;
        this.mapper = mapper;
    }

    public void addSource(Edge<E> edge) {
        isDirty = sources.add(edge);
    }

    public double evaluate() {
        if (isDirty()) {
            result = func.apply(mapper.applyAsDouble(aggregate(sources.stream().map(Edge::evaluate).collect(Collectors.toList()))));
            isDirty = false;
        }
        return result;
    }

    public void learn(double loss) {
    }

    public boolean isDirty() {
        return isDirty || sources.stream().anyMatch(Edge::isDirty);
    }

    protected abstract E aggregate(List<E> values);

    @Override
    public String toString() {
        return sources.stream().map(Edge::toString).reduce("", (a, b) -> a.isEmpty() ? b : a + "\n" + b) + "[V]";
    }
}
