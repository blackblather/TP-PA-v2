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
}
