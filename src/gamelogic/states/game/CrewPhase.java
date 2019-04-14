package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class CrewPhase extends GameStateAdapter {
    CrewPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState _CrewPhase() {
        return this;
    }
}
