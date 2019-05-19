package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class RestPhase extends GameStateAdapter{
    RestPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState Skip() {
        return new RoundPhase(gameDataHandler);
    }

    @Override
    public IGameState EvaluateChosenUpgrade(int opt) {
        try {
            return EffectBufferInitialRoute(0,opt);
        } catch (IndexOutOfBoundsException ex){
            return this;    //Escolheu um efeito inválido
        }
    }
}
