package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class RoundPhase extends GameStateAdapter {
    public RoundPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState EvaluateRound() {
        try{
            gameDataHandler.MoveJourneyTracker();
            if(gameDataHandler.GetCurrentJourneyPart().toLowerCase().equals("r"))
                return new RestPhase(gameDataHandler);
            else{
                gameDataHandler.SpawnAliens();
                return new CrewPhase(gameDataHandler);
            }
        }catch (UnsupportedOperationException ex){
            return new Win(gameDataHandler);
        }
    }
}
