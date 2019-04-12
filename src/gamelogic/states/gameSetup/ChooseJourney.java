package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public class ChooseJourney extends GameSetupStateAdapter {
    ChooseJourney(EncapsulatedGameData encapsulatedGameData) {
        super(encapsulatedGameData);
    }

    @Override
    public IGameSetupState _ChooseJourney() {
        return new SelectCrewMembers(encapsulatedGameData);
    }

    @Override
    public IGameSetupState _ChooseJourney(String[] customJourney) {
        try{
            encapsulatedGameData.CreateCustomJourney(customJourney);
            return new SelectCrewMembers(encapsulatedGameData);
        } catch (ArrayStoreException ex){
            return this;
        }
    }

}
