package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public class StartGame extends GameSetupStateAdapter {
    public StartGame(EncapsulatedGameData encapsulatedGameData) {
        super(encapsulatedGameData);
    }
    @Override
    public IGameSetupState _StartGame() {
        return this;
    }
}
