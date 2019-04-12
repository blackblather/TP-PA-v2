package gamelogic;

import gamelogic.data.EncapsulatedGameData;
import gamelogic.states.game.IGameState;
import gamelogic.states.gameSetup.IGameSetupState;
import gamelogic.states.gameSetup.NewGame;

public class Logic {
    //Private vars
    private EncapsulatedGameData encapsulatedGameData;
    private IGameState gameState;
    private IGameSetupState gameSetupState;

    //Constructor
    public Logic(){
        encapsulatedGameData = new EncapsulatedGameData();
        this.gameSetupState = new NewGame(encapsulatedGameData);
        //TODO: Inicializer gameState aqui
            //TODO: Criar classes filhas de GameStateAdapter
    }

    //Getters
    public IGameSetupState GetGameSetupState(){
        return gameSetupState;
    }
    public IGameState GetGameState(){
        return gameState;
    }
    public EncapsulatedGameData GetEncapsulatedGameData(){
        return encapsulatedGameData;
    }

    //Interact with gameSetupState:
    public void NewGame(){ gameSetupState = gameSetupState._NewGame(); }
    public void ExitGame() {gameSetupState = gameSetupState._Exit();}
    public void ChooseJourney(){gameSetupState = gameSetupState._ChooseJourney();}
    public void ChooseJourney(String[] customJourneyStr){gameSetupState = gameSetupState._ChooseJourney(customJourneyStr);}
    public void SelectCrewMembers(int pos){
        gameSetupState = gameSetupState._SelectCrewMembers(pos);
    }
}
