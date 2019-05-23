package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

import java.util.Observer;

public class InitialMenu extends GameSetupStateAdapter {
    private Observer o = null;
    public InitialMenu(GameDataHandler gameDataHandler, Observer o) {
        super(gameDataHandler, o);
        this.o = o;
        setChanged();
    }
    public InitialMenu(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState NewGame() {
        if(o != null)
            return new ChooseJourney(gameDataHandler, o);
        return new ChooseJourney(gameDataHandler);
    }
    @Override
    public IGameSetupState _Exit() {
        if(o != null)
            return new Exit(gameDataHandler, o);
        return new Exit(gameDataHandler);
    }
}
