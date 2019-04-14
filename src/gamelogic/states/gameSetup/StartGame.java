package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

public class StartGame extends GameSetupStateAdapter {
    public StartGame(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }
    @Override
    public IGameSetupState _StartGame() {
        return this;
    }
}
