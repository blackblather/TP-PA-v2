package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public class SetCrewMemberShipLocation extends GameSetupStateAdapter {
    public SetCrewMemberShipLocation(EncapsulatedGameData encapsulatedGameData) {
        super(encapsulatedGameData);
    }

    @Override
    public IGameSetupState _SetCrewMemberShipLocation() {
        return new StartGame(GetEncapsulatedGameData());
    }
}
