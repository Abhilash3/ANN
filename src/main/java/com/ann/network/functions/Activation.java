package com.ann.network.functions;

import java.util.function.DoubleUnaryOperator;

public enum Activation {
    NONE(x -> x, x -> x),
    SIGMOID(x -> 1 / (1 + Math.exp(-x)), x -> x * (1 - x)),
    SINUSOID(x -> (Math.sin(x) + 1) / 2, x -> Math.cos(Math.asin(x * 2 - 1))),
    TAN_H(x -> {
        double a = Math.exp(x);
        double b = Math.exp(-x);
        return ((a - b) / (a + b) + 1) / 2;
    }, x -> 1 - Math.pow(x * 2 - 1, 2));

    private final DoubleUnaryOperator equation;
    private final DoubleUnaryOperator derivative;

    Activation(DoubleUnaryOperator equation, DoubleUnaryOperator derivative) {
        this.equation = equation;
        this.derivative = derivative;
    }

    public double apply(double x) {
        return equation.applyAsDouble(x);
    }

    public double derivative(double x) {
        return derivative.applyAsDouble(x);
    }
}
