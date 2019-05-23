package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public abstract class GameStateAdapter implements IGameState {
    GameDataHandler gameDataHandler;
    GameStateAdapter(GameDataHandler gameDataHandler) {
        this.gameDataHandler = gameDataHandler;
    }

    IGameState EffectBufferInitialRoute(int type, int opt){
        gameDataHandler.SetEffectBuffer(type, opt);
        if (gameDataHandler.CanPayForEffectBuffer()) {
            if (!gameDataHandler.EffectBufferNeedsAdditionalInput())
                return new ExecuteEffect(gameDataHandler);
            else {
                if (gameDataHandler.EffectBufferNeedsCrewMemberInput())
                    return new SelectCrewMember(gameDataHandler);
                else
                    return new SelectRoom(gameDataHandler);
            }
        }
        switch (type) {
            case 0: return new RoundPhase(gameDataHandler);
            case 1: return new AlienPhase(gameDataHandler);
            default: return this;
        }
    }

    //Estes métodos estão aqui para caso se use um objecto de uma classe filha para chamar um método que não se deva, o estado nao mudar
    @Override
    public IGameState EvaluateRound() {
        return this;
    }

    @Override
    public IGameState Skip() {
        return this;
    }

    @Override
    public IGameState EvaluateChosenUpgrade(int opt) {
        return this;
    }

    @Override
    public IGameState EvaluateChosenCrewMember(int opt) {
        return this;
    }

    @Override
    public IGameState EvaluateChosenTrap(int opt) {
        return this;
    }

    @Override
    public IGameState EvaluateChosenRoom(int opt) {
        return this;
    }

    @Override
    public IGameState EvaluateAndExecuteEffect() {
        return this;
    }

    @Override
    public IGameState EvaluateChosenAction(int opt) {
        return this;
    }
}
