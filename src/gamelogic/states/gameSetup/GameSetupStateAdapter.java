package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class GameSetupStateAdapter implements IGameSetupState{
    GameDataHandler gameDataHandler;

    GameSetupStateAdapter(GameDataHandler gameDataHandler){
        this.gameDataHandler = gameDataHandler;
    }

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
    public IGameSetupState SelectCrewMembers(ArrayList<Integer> pos){
        return this;
    }

    @Override
    public IGameSetupState SetCrewMemberShipLocation(int roomPos, int crewMemberPos) {
        return this;
    }

    @Override
    public IGameSetupState SetCrewMembersShipLocation(ArrayList<Integer> rooms) {
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
