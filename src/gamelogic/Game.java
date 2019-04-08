package gamelogic;

public class Game {
    ShipBoard shipBoard;
    PlayerBoard playerBoard;
    GameSetupState gameSetupState;
    GameState gameState;
    public void GameSetupStateMachine(){
        switch (gameSetupState){
        }
    }
    public void GameStateMachine(){
        switch (gameState){
        }
    }
}