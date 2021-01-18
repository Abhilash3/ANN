package com.ann;

import com.ann.network.Network;
import com.ann.network.functions.Activation;
import com.ann.network.input.Numeric;

import java.util.stream.IntStream;

import static com.ann.network.Utilities.buildNetwork;
import static com.ann.network.Utilities.testNetwork;
import static com.ann.network.Utilities.trainNetwork;

public class Main {

    private static Numeric[][] twoLogicalGateInput() {
        Numeric[][] inputs = new Numeric[4][2];
        IntStream.range(0, 2).forEach(a -> IntStream.range(0, 2).forEach(b -> {
            inputs[a * 2 + b][0] = new Numeric(a);
            inputs[a * 2 + b][1] = new Numeric(b);
        }));
        return inputs;
    }

    private static Numeric[][] oneLogicalGateInput() {
        Numeric[][] inputs = new Numeric[2][1];
        IntStream.range(0, 2).forEach(a -> inputs[a][0] = new Numeric(a));
        return inputs;
    }

    private static void trainLogicalNotGate() {
        Network<Numeric, Numeric> network = buildNetwork(Activation.SIGMOID, 1, 1);

        Numeric[][] inputs = oneLogicalGateInput();
        trainNetwork(network, 10000, a -> a[0].toInt() == 0 ? 1 : 0, inputs);
        testNetwork(network, inputs);
    }

    private static void trainLogicalAndGate() {
        Network<Numeric, Numeric> network = buildNetwork(Activation.SIGMOID, 2);

        Numeric[][] inputs = twoLogicalGateInput();
        trainNetwork(network, 100000, a -> a[0].toInt() & a[1].toInt(), inputs);
        testNetwork(network, inputs);
    }

    private static void trainLogicalXorGate() {
        Network<Numeric, Numeric> network = buildNetwork(Activation.SIGMOID, 2, 2, 2, 2);

        Numeric[][] inputs = twoLogicalGateInput();
        trainNetwork(network, 100000, a -> a[0].toInt() ^ a[1].toInt(), inputs);
        testNetwork(network, inputs);
    }

    private static void trainModular() {
        Network<Numeric, Numeric> network = buildNetwork(Activation.SINUSOID, 1, 3, 5, 2);

        Numeric[][] inputs = IntStream.range(0, 100).boxed().map(a -> new Numeric[]{new Numeric(a)}).toArray(Numeric[][]::new);
        trainNetwork(network, 1000, a -> a[0].toInt() % 2, inputs);
        testNetwork(network, inputs);
    }

    public static void main(String... args) {
        trainLogicalXorGate();
    }
}
