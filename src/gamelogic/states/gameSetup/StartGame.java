package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

import java.util.Observer;

public class StartGame extends GameSetupStateAdapter {
    private Observer o = null;
    StartGame(GameDataHandler gameDataHandler, Observer o) {
        super(gameDataHandler, o);
        this.o = o;
    }
    StartGame(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState _StartGame() {
        return this;
    }
    @Override
    public IGameSetupState _EndGame() {
        gameDataHandler = new GameDataHandler();
        if(o != null)
            return new InitialMenu(gameDataHandler, o);
        return new InitialMenu(gameDataHandler);
    }
}
