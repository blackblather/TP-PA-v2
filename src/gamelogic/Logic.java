package gamelogic;

import gamelogic.data.GameDataHandler;
import gamelogic.states.game.IGameState;
import gamelogic.states.game.RoundPhase;
import gamelogic.states.gameSetup.IGameSetupState;
import gamelogic.states.gameSetup.InitialMenu;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Logic extends Observable {
    //TODO: Decrementar os user-inputs aqui em vez do gamedatahandler
    //Private vars
    private GameDataHandler gameDataHandler = new GameDataHandler();
    private IGameState gameState = null;
    private IGameSetupState gameSetupState;

    //Constructor
    public Logic(Observer o){
        addObserver(o);
    }
    public Logic(){
        gameSetupState = new InitialMenu(gameDataHandler);
    }


    //Getters
    public IGameSetupState GetGameSetupState(){ return gameSetupState; }
    public IGameState GetGameState(){ return gameState; }
    public GameDataHandler GetGameDataHandler(){ return gameDataHandler; }

    //Interact with gameSetupState:
    public void InitialMenu(){
        gameSetupState = new InitialMenu(gameDataHandler);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void NewGame(){
        gameSetupState = gameSetupState.NewGame();
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void ExitGame() {
        gameSetupState = gameSetupState._Exit();
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void SelectDefaultJourney(){
        gameSetupState = gameSetupState.SelectDefaultJourney();
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void SelectCustomJourney(String[] customJourneyStr){
        gameSetupState = gameSetupState.SelectCustomJourney(customJourneyStr);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void SelectCrewMember(int deckPos){
        gameSetupState = gameSetupState.SelectCrewMemberIn(deckPos);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void SelectCrewMembers(ArrayList<Integer> deckPos){
        gameSetupState = gameSetupState.SelectCrewMembers(deckPos);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void SetCrewMemberShipLocation(int roomPos, int crewMemberPos){
        gameSetupState = gameSetupState.SetCrewMemberShipLocation(roomPos, crewMemberPos);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void SetCrewMembersShipLocation(ArrayList<Integer> rooms){
        gameSetupState = gameSetupState.SetCrewMembersShipLocation(rooms);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void EndGame(){
        gameSetupState = gameSetupState.EndGame();
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }

    //Interact with gameState:
    public void StartGame(){
        gameDataHandler.LoadChosenCrewMemberSpecials();
        gameState = new RoundPhase(gameDataHandler);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void EvaluateRound(){
        gameState = gameState.EvaluateRound();
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void Skip(){
        gameState = gameState.Skip();
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void EvaluateChosenUpgrade(int opt){
        gameState = gameState.EvaluateChosenUpgrade(opt-1);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void EvaluateChosenAction(int opt){
        gameState = gameState.EvaluateChosenAction(opt-1);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void EvaluateChosenCrewMember(int opt){
        gameState = gameState.EvaluateChosenCrewMember(opt-1);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void EvaluateChosenTrap(int opt){
        gameState = gameState.EvaluateChosenTrap(opt-1);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void EvaluateChosenRoom(int opt){
        gameState = gameState.EvaluateChosenRoom(opt-1);
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
    public void EvaluateAndExecuteEffect(){
        gameState = gameState.EvaluateAndExecuteEffect();
        setChanged();
        notifyObservers(gameDataHandler.GetErrorMessage());
    }
}