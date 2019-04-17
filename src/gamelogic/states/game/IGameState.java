package gamelogic.states.game;

public interface IGameState {
    IGameState _JourneyPhase();
    IGameState _ScanningPhase();
    IGameState _SpawnAliensPhase();
    IGameState _RestPhase();
    IGameState _RestPhase(int opt);
    IGameState _RestPhase(int opt, int value);
    IGameState _CrewPhase();
    IGameState _AlienPhase();
    IGameState _GameOver();
    IGameState _Win();
}
