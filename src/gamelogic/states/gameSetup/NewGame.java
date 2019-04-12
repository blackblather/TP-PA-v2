package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public class NewGame extends GameSetupStateAdapter {
    public NewGame(EncapsulatedGameData encapsulatedGameData) {
        super(encapsulatedGameData);
    }

    public IGameSetupState InitialMenu(int opt){

        return this;
    }

    @Override
    public IGameSetupState _NewGame() {
        return new ChooseJourney(GetEncapsulatedGameData());
    }
    @Override
    public IGameSetupState _Exit() {
        return new Exit(GetEncapsulatedGameData());
    }
}
