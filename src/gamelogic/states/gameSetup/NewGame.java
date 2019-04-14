package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

public class NewGame extends GameSetupStateAdapter {
    public NewGame(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState _NewGame() {
        return new ChooseJourney(gameDataHandler);
    }
    @Override
    public IGameSetupState _Exit() {
        return new Exit(gameDataHandler);
    }
}
