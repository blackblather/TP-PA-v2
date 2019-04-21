package gamelogic.data;

@FunctionalInterface
interface IEffect {
    void execute(int ... x);
}
