package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

import java.util.Observable;
import java.util.Observer;

public abstract class GameSetupStateAdapter extends Observable implements IGameSetupState{
    GameDataHandler gameDataHandler;

    GameSetupStateAdapter(GameDataHandler gameDataHandler, Observer o){
        this.gameDataHandler = gameDataHandler;
        this.addObserver(o);

    }
    GameSetupStateAdapter(GameDataHandler gameDataHandler){
        this.gameDataHandler = gameDataHandler;
    }

    @Override
    public void notifyObservers(){
        super.notifyObservers(gameDataHandler.GetErrorMessage());
    }

    //TODO: As classes da maquina de estados devem chamar os métodos com o nome dos estados seguintes

    //Estes métodos estão aqui para caso se use um objecto de uma classe filha para chamar um método que não se deva, o estado nao mudar
    @Override
    public IGameSetupState NewGame() {
        return this;
    }

    @Override
    public IGameSetupState SelectDefaultJourney() {
        return this;
    }

    @Override
    public IGameSetupState SelectCustomJourney(String[] customJourney) {
        return this;
    }

    @Override
    public IGameSetupState SelectCrewMemberIn(int pos) {
        return this;
    }

    @Override
    public IGameSetupState _SetCrewMemberShipLocation(int roomPos, int crewMemberPos) {
        return this;
    }

    @Override
    public IGameSetupState _StartGame() {
        return this;
    }

    @Override
    public IGameSetupState _EndGame() {
        return this;
    }

    @Override
    public IGameSetupState _Exit() {
        return this;
    }
}
