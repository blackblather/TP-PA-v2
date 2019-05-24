package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

public class ChooseJourney extends GameSetupStateAdapter {
    ChooseJourney(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState SelectDefaultJourney() {
        return new ChooseCrewMembers(gameDataHandler);
    }

    @Override
    public IGameSetupState SelectCustomJourney(String[] customJourney) {
        try{
            gameDataHandler.CreateCustomJourney(customJourney);
            return new ChooseCrewMembers(gameDataHandler);
        } catch (ArrayStoreException ex){
            gameDataHandler.SetErrorMessage("Invalid custom journey.");
            return this;
        }
    }

}
