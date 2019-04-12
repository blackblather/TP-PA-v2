package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public class NewGame extends GameSetupStateAdapter {
    public NewGame(EncapsulatedGameData encapsulatedGameData) {
        super(encapsulatedGameData);
    }

    @Override
    public IGameSetupState _NewGame() {
        return new ChooseJourney(encapsulatedGameData);
    }
    @Override
    public IGameSetupState _Exit() {
        return new Exit(encapsulatedGameData);
    }
}
