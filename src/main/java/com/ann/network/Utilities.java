package com.ann.network;

import com.ann.network.functions.Activation;
import com.ann.network.input.Input;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;
import java.util.logging.*;
import java.util.stream.IntStream;

public class Utilities {
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Utilities.class.getSimpleName());
        LOGGER.setLevel(Level.ALL);
        Handler handler = new StreamHandler(System.out, new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord r) {
                return r.getMessage() + "\n";
            }
        });
        handler.setLevel(Level.ALL);
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(handler);
    }

    private Utilities() {
        throw new UnsupportedOperationException();
    }

    public static <E extends Input> Network<E> buildNetwork(Activation func, int start, int... hidden) {
        Network.Builder<E> builder = new Network.Builder<>(func, start);
        for (int a : hidden) {
            builder.addLayer(func, a);
        }
        return builder.finalLayer(func);
    }

    public static <E extends Input> void trainNetwork(Network<E> network, int times, ToDoubleFunction<E[]> mapper, E[][] values) {
        IntStream.range(0, times).forEach(a -> Arrays.stream(values).forEach(b -> {
            network.register(b);
            double expected = mapper.applyAsDouble(b);
            LOGGER.fine(String.format("%d ] %s: %s; %s", a + 1, Arrays.toString(b), expected, network.learn(expected)));
        }));
    }

    public static <E extends Input> void testNetwork(Network<E> network, E[][] values) {
        LOGGER.fine(network::toString);
        Arrays.stream(values).forEach(a -> {
            network.register(a);
            LOGGER.fine(String.format("%s: %s", Arrays.toString(a), network.evaluate()));
        });
    }
}
