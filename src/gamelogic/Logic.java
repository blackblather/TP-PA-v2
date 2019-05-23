package gamelogic;

import gamelogic.data.GameDataHandler;
import gamelogic.states.game.IGameState;
import gamelogic.states.game.RoundPhase;
import gamelogic.states.gameSetup.IGameSetupState;
import gamelogic.states.gameSetup.InitialMenu;

import java.util.Observer;

public class Logic {
    //TODO: Decrementar os user-inputs aqui em vez do gamedatahandler
    //Private vars
    private GameDataHandler gameDataHandler = new GameDataHandler();
    private IGameState gameState;
    private IGameSetupState gameSetupState;
    private Observer o;

    //Constructor
    public Logic(Observer o){
        this.o = o;
    }
    public Logic(){
        gameSetupState = new InitialMenu(gameDataHandler);
    }

    //Getters
    public IGameSetupState GetGameSetupState(){ return gameSetupState; }
    public IGameState GetGameState(){ return gameState; }
    public GameDataHandler GetGameDataHandler(){ return gameDataHandler; }

    //Interact with gameSetupState:
    public void InitialMenu(){ gameSetupState = new InitialMenu(gameDataHandler, o); gameSetupState.notifyObservers(); }
    public void NewGame(){ gameSetupState = gameSetupState.NewGame(); gameSetupState.notifyObservers();}
    public void ExitGame() { gameSetupState = gameSetupState._Exit(); gameSetupState.notifyObservers();}
    public void SelectDefaultJourney(){ gameSetupState = gameSetupState.SelectDefaultJourney(); gameSetupState.notifyObservers();}
    public void SelectCustomJourney(String[] customJourneyStr){ gameSetupState = gameSetupState.SelectCustomJourney(customJourneyStr); gameSetupState.notifyObservers();}
    public void SelectCrewMember(int deckPos){ gameSetupState = gameSetupState.SelectCrewMemberIn(deckPos); gameSetupState.notifyObservers();}
    public void SetCrewMemberShipLocation(int roomPos, int crewMemberPos){ gameSetupState = gameSetupState._SetCrewMemberShipLocation(roomPos, crewMemberPos); gameSetupState.notifyObservers();}
    public void EndGame(){ gameSetupState = gameSetupState._EndGame(); gameSetupState.notifyObservers();}

    //Interact with gameState:
    public void StartGame(){
        gameDataHandler.LoadChosenCrewMemberSpecials();
        gameState = new RoundPhase(gameDataHandler);
    }
    public void EvaluateRound(){ gameState = gameState.EvaluateRound(); }
    public void Skip(){ gameState = gameState.Skip(); }
    public void EvaluateChosenUpgrade(int opt){ gameState = gameState.EvaluateChosenUpgrade(opt-1); }
    public void EvaluateChosenAction(int opt){ gameState = gameState.EvaluateChosenAction(opt-1); }
    public void EvaluateChosenCrewMember(int opt){ gameState = gameState.EvaluateChosenCrewMember(opt-1); }
    public void EvaluateChosenTrap(int opt){ gameState = gameState.EvaluateChosenTrap(opt-1); }
    public void EvaluateChosenRoom(int opt){ gameState = gameState.EvaluateChosenRoom(opt-1); }
    public void EvaluateAndExecuteEffect(){ gameState = gameState.EvaluateAndExecuteEffect(); }
}


