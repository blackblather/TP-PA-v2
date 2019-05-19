package gamelogic;

import gamelogic.data.GameDataHandler;
import gamelogic.states.game.IGameState;
import gamelogic.states.game.RoundPhase;
import gamelogic.states.gameSetup.IGameSetupState;
import gamelogic.states.gameSetup.InitialMenu;

public class Logic {
    //TODO: Decrementar os user-inputs aqui em vez do gamedatahandler
    //Private vars
    private GameDataHandler gameDataHandler = new GameDataHandler();
    private IGameState gameState;
    private IGameSetupState gameSetupState = new InitialMenu(gameDataHandler);

    //Constructor
    public Logic(){}

    //Getters
    public IGameSetupState GetGameSetupState(){ return gameSetupState; }
    public IGameState GetGameState(){ return gameState; }
    public GameDataHandler GetGameDataHandler(){ return gameDataHandler; }

    //Interact with gameSetupState:
    public void NewGame(){ gameSetupState = gameSetupState.NewGame(); }
    public void ExitGame() { gameSetupState = gameSetupState._Exit(); }
    public void ChooseJourney(){ gameSetupState = gameSetupState._ChooseJourney(); }
    public void ChooseJourney(String[] customJourneyStr){ gameSetupState = gameSetupState._ChooseJourney(customJourneyStr); }
    public void SelectCrewMembers(int deckPos){ gameSetupState = gameSetupState._SelectCrewMembers(deckPos); }
    public void SetCrewMemberShipLocation(int roomPos, int crewMemberPos){ gameSetupState = gameSetupState._SetCrewMemberShipLocation(roomPos, crewMemberPos); }
    public void EndGame(){ gameSetupState = gameSetupState._EndGame(); }

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
    public void CrewPhase(){ gameState = gameState._CrewPhase(); }
    public void CrewPhase(int opt){ gameState = gameState._CrewPhase(opt); }
    public void CrewPhase(int opt, int[] additionalInputs){ gameState = gameState._CrewPhase(opt, additionalInputs); }
}
