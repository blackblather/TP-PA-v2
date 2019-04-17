package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class RestPhase extends GameStateAdapter{
    RestPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    private IGameState UpdatedState(){
        if(gameDataHandler.GetInsirationPoints() > 0)
            return this;
        else
            return new JourneyPhase(gameDataHandler);
    }

    @Override
    public IGameState _RestPhase() {
        return new JourneyPhase(gameDataHandler);
    }

    @Override
    public IGameState _RestPhase(int opt) {
        gameDataHandler.ExecuteUpgradeAt(opt);
        return UpdatedState();
    }

    @Override
    public IGameState _RestPhase(int opt, int value) {
        gameDataHandler.ExecuteUpgradeAt(opt, value);
        return UpdatedState();
    }
}
