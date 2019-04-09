package gamelogic;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToIntFunction;

public class Game {
    //Private vars
    private ShipBoard shipBoard;
    private PlayerBoard playerBoard;
    private GameSetupState gameSetupState;
    private GameState gameState;
    private boolean removeAliensFlag = false;
    //Private Lambdas
    //Roll rangre = [NrOfDice, (NrOfDice*6)+1]
    private final ToIntFunction rollDiceLambda = (NrOfDice) -> ThreadLocalRandom.current().nextInt((int)NrOfDice, ((int)NrOfDice*6)+1);
    //Public functions
    public void GameSetupStateMachine(){
        do {
            switch (gameSetupState) {
                case WaitGameStart:
                    break;
                case CustomJourney:
                    break;
                case SelectCrewMembers:
                    break;
                case SetCrewMembers3rdToken:
                    break;
                case StartGame:
                    GameStateMachine();
                    break;
                case GameOver:
                    break;
                case Win:
                    break;
            }
        }while(gameSetupState != GameSetupState.Exit);
    }
    //Private functions
    private void GameStateMachine(){
        switch (gameState){
            case JourneyPhase: JourneyPhase(); break;
            case ScanningPhase: ScanningPhase(); break;
            case RestPhase: break;
            case CrewPhase: break;
            case AlienPhase: break;
        }
    }
    private void JourneyPhase(){
        try{
            shipBoard.MoveJourneyTracker();
        }
        catch (Exception ex){
            gameSetupState = GameSetupState.Win;
        }
    }
    private void ScanningPhase(){
        String journeyPart = shipBoard.GetCurrentJourneyPart();
        if(journeyPart.equals("R"))
            gameState = GameState.RestPhase;
        else{
            String[] journeyPartParts = journeyPart.split("A");
            int nrOfAliensToSpawn;
            switch (journeyPartParts.length){
                //A
                case 0: nrOfAliensToSpawn = 1; break;
                //nA || A*
                case 1: {
                    if(!journeyPartParts[0].equals("*"))
                        nrOfAliensToSpawn = Integer.parseInt(journeyPartParts[0]);
                    else {
                        nrOfAliensToSpawn = 1;
                        removeAliensFlag = true;
                    }
                } break;
                //nA*
                case 2:{
                    nrOfAliensToSpawn = Integer.parseInt(journeyPartParts[0]);
                    removeAliensFlag = true;
                } break;
                default: nrOfAliensToSpawn = 0; break;
            }
            shipBoard.SpawnAliens(playerBoard.DecreaseAlienCounterBy(nrOfAliensToSpawn), rollDiceLambda);
        }
    }
}