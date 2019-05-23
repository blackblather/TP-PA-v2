package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

import java.util.Observer;

public class ChooseJourney extends GameSetupStateAdapter {
    private Observer o = null;

    ChooseJourney(GameDataHandler gameDataHandler, Observer o) {
        super(gameDataHandler, o);
        this.o = o;
        setChanged();
    }
    ChooseJourney(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState SelectDefaultJourney() {
        if(o != null)
            return new ChooseCrewMembers(gameDataHandler, o);
        return new ChooseCrewMembers(gameDataHandler);
    }

    @Override
    public IGameSetupState SelectCustomJourney(String[] customJourney) {
        try{
            gameDataHandler.CreateCustomJourney(customJourney);
            if(o != null)
                return new ChooseCrewMembers(gameDataHandler, o);
            return new ChooseCrewMembers(gameDataHandler);
        } catch (ArrayStoreException ex){
            gameDataHandler.SetErrorMessage("Invalid custom journey.");
            setChanged();
            return this;
        }
    }

}
