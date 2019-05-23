package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

import java.util.Observer;

public class Exit extends GameSetupStateAdapter {
    Exit(GameDataHandler gameDataHandler, Observer o) {
        super(gameDataHandler, o);
        setChanged();
    }
    Exit(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }
}
