package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class SpawnAliensPhase extends GameStateAdapter {
    SpawnAliensPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState _SpawnAliensPhase() {
        gameDataHandler.SpawnAliens();
        return new CrewPhase(gameDataHandler);
    }
}
