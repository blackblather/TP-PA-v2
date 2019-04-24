package gamelogic.states.game;

public interface IGameState {
    IGameState _JourneyPhase();
    IGameState _ScanningPhase();
    IGameState _SpawnAliensPhase();
    IGameState _RestPhase();
    IGameState _RestPhase(int opt);
    IGameState _RestPhase(int opt, int[] additionalInputs);
    IGameState _CrewPhase();
    IGameState _CrewPhase(int opt);
    IGameState _CrewPhase(int opt, int[] additionalInputs);
    IGameState _AlienPhase();
    IGameState _GameOver();
    IGameState _Win();
}
