package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class JourneyPhase extends GameStateAdapter {
    public JourneyPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }
    @Override
    public IGameState _JourneyPhase() {
        try{
            gameDataHandler.MoveJourneyTracker();
            return new ScanningPhase(gameDataHandler);
        }catch (UnsupportedOperationException ex){
            return new Win(gameDataHandler);
        }
    }
}
