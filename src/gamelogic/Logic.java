package gamelogic;

import gamelogic.data.GameDataHandler;
import gamelogic.states.game.IGameState;
import gamelogic.states.game.JourneyPhase;
import gamelogic.states.gameSetup.IGameSetupState;
import gamelogic.states.gameSetup.NewGame;

public class Logic {
    //Private vars
    private GameDataHandler gameDataHandler = new GameDataHandler();
    private IGameState gameState;
    private IGameSetupState gameSetupState = new NewGame(gameDataHandler);

    //Constructor
    public Logic(){}

    //Getters
    public IGameSetupState GetGameSetupState(){
        return gameSetupState;
    }
    public IGameState GetGameState(){
        return gameState;
    }
    public GameDataHandler GetGameDataHandler(){
        return gameDataHandler;
    }

    //Interact with gameSetupState:
    public void NewGame(){ gameSetupState = gameSetupState._NewGame(); }
    public void ExitGame() {gameSetupState = gameSetupState._Exit();}
    public void ChooseJourney(){gameSetupState = gameSetupState._ChooseJourney();}
    public void ChooseJourney(String[] customJourneyStr){gameSetupState = gameSetupState._ChooseJourney(customJourneyStr);}
    public void SelectCrewMembers(int deckPos){
        gameSetupState = gameSetupState._SelectCrewMembers(deckPos);
    }
    public void SetCrewMemberShipLocation(int roomPos, int crewMemberPos){
        gameSetupState = gameSetupState._SetCrewMemberShipLocation(roomPos, crewMemberPos);
    }

    //Interact with gameState:
    public void StartGame(){
        gameDataHandler.LoadChosenCrewMemberSpecials();
        gameState = new JourneyPhase(gameDataHandler);
    }
    public void JourneyPhase(){
        gameState = gameState._JourneyPhase();
    }
    public void ScanningPhase(){
        gameState = gameState._ScanningPhase();
    }
    public void SpawnAliensPhase(){
        gameState = gameState._SpawnAliensPhase();
    }
    public void RestPhase(){
        gameState = gameState._RestPhase();
    }
    public void RestPhase(int opt){
        gameState = gameState._RestPhase(opt);
    }
    public void RestPhase(int opt, int value){
        gameState = gameState._RestPhase(opt, value);
    }
}
