package gamelogic.states.game;

public interface IGameState {
    IGameState EvaluateRound();
    IGameState Skip();
    IGameState EvaluateChosenUpgrade(int opt);
    IGameState EvaluateChosenCrewMember(int opt);
    IGameState EvaluateChosenTrap(int opt);
    IGameState EvaluateChosenRoom(int opt);
    IGameState EvaluateAndExecuteEffect();
    IGameState EvaluateChosenAction(int opt);




    IGameState _CrewPhase();
    IGameState _CrewPhase(int opt);
    IGameState _CrewPhase(int opt, int[] additionalInputs);
    IGameState _AlienPhase();
    IGameState _GameOver();
    IGameState _Win();
}
