package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

public class InitialMenu extends GameSetupStateAdapter {
    public InitialMenu(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState NewGame() {
        return new ChooseJourney(gameDataHandler);
    }
    @Override
    public IGameSetupState _Exit() {
        return new Exit(gameDataHandler);
    }
}
