package gamelogic.states.game;

import gamelogic.data.GameDataHandler;
import gamelogic.data.TrapKilledMemberException;

public class CrewPhase extends GameStateAdapter {
    CrewPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState Skip() {
        return new AlienPhase(gameDataHandler);
    }

    @Override
    public IGameState EvaluateChosenAction(int opt) {
        try {
            return EffectBufferInitialRoute(1, opt);
        } catch (IndexOutOfBoundsException ex){
            return this;    //Escolheu um efeito inválido
        }
    }
}
