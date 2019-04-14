package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class ScanningPhase extends GameStateAdapter {
    ScanningPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState _ScanningPhase() {
        if(gameDataHandler.GetCurrentJourneyPart().equals("R"))
            return new RestPhase(gameDataHandler);
        else
            return new SpawnAliensPhase(gameDataHandler);
    }
}
