package gamelogic.states.game;

public interface IGameState {
    IGameState _JourneyPhase();
    IGameState _ScanningPhase();
    IGameState _SpawnAliensPhase();
    IGameState _RestPhase();
    IGameState _CrewPhase();
    IGameState _AlienPhase();
    IGameState _GameOver();
    IGameState _Win();
}
