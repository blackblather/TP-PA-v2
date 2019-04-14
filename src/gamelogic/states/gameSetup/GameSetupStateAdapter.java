package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

public abstract class GameSetupStateAdapter implements IGameSetupState{
    GameDataHandler gameDataHandler;
    GameSetupStateAdapter(GameDataHandler gameDataHandler){
        this.gameDataHandler = gameDataHandler;
    }

    //Estes métodos estão aqui para caso se use um objecto de uma classe filha para chamar um método que não se deva, o estado nao mudar
    @Override
    public IGameSetupState _NewGame() {
        return this;
    }

    @Override
    public IGameSetupState _ChooseJourney() {
        return this;
    }

    @Override
    public IGameSetupState _ChooseJourney(String[] customJourney) {
        return this;
    }

    @Override
    public IGameSetupState _SelectCrewMembers(int pos) {
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
