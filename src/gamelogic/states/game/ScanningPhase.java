package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class ScanningPhase extends GameStateAdapter {
    ScanningPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState _ScanningPhase() {
        if(gameDataHandler.GetCurrentJourneyPart().toLowerCase().equals("r"))
            return new RestPhase(gameDataHandler);
        else
            return new SpawnAliensPhase(gameDataHandler);
    }
}
