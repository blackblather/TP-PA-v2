package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class RestPhase extends GameStateAdapter{
    RestPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState _RestPhase() {
        return new JourneyPhase(gameDataHandler);
    }
}
