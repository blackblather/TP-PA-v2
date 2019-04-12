package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public abstract class GameSetupStateAdapter implements IGameSetupState{
    private EncapsulatedGameData encapsulatedGameData;
    GameSetupStateAdapter(EncapsulatedGameData encapsulatedGameData){
        this.encapsulatedGameData = encapsulatedGameData;
    }

    EncapsulatedGameData GetEncapsulatedGameData(){
        return encapsulatedGameData;
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
    public IGameSetupState _SetCrewMemberShipLocation() {
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
