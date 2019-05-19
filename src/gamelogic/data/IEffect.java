package gamelogic.data;

import java.util.List;

@FunctionalInterface
interface IEffect {
    void execute(List<Integer> inputs);
}
