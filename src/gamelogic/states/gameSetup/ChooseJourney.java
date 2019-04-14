package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

public class ChooseJourney extends GameSetupStateAdapter {
    ChooseJourney(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState _ChooseJourney() {
        return new SelectCrewMembers(gameDataHandler);
    }

    @Override
    public IGameSetupState _ChooseJourney(String[] customJourney) {
        try{
            gameDataHandler.CreateCustomJourney(customJourney);
            return new SelectCrewMembers(gameDataHandler);
        } catch (ArrayStoreException ex){
            return this;
        }
    }

}
