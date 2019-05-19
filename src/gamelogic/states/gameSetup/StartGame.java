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
    @Override
    public IGameSetupState _EndGame() {
        gameDataHandler = new GameDataHandler();
        return new InitialMenu(gameDataHandler);
    }
}
