package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public class ChooseJourney extends GameSetupStateAdapter {
    ChooseJourney(EncapsulatedGameData encapsulatedGameData) {
        super(encapsulatedGameData);
    }


    @Override
    public IGameSetupState _ChooseJourney() {
        return new SelectCrewMembers(GetEncapsulatedGameData());
    }

    @Override
    public IGameSetupState _ChooseJourney(String[] customJourney) {
        try{
            GetEncapsulatedGameData().CreateCustomJourney(customJourney);
            return new SelectCrewMembers(GetEncapsulatedGameData());
        } catch (ArrayStoreException ex){
            return this;
        }
    }

}
