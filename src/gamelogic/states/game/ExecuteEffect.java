package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class ExecuteEffect extends GameStateAdapter {
    ExecuteEffect(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState EvaluateAndExecuteEffect() {
        gameDataHandler.ExecuteEffectBuffer();
        gameDataHandler.PayEffectBuffer();    //Se chega aqui, nenhum excepção ocorreu na instrução anterior (aka, o efeito foi bem executado)
        switch (gameDataHandler.GetEffectBufferType()){
            case 0: {
                if (gameDataHandler.GetInsirationPoints() > 0)
                    return new RestPhase(gameDataHandler);  //Still has inspiration points
                return new RoundPhase(gameDataHandler);     //No more inspiration points to spend
            }
            case 1: {
                if (gameDataHandler.GetActionPoints() > 0)
                    return new CrewPhase(gameDataHandler);  //Still has action points
                return new AlienPhase(gameDataHandler);     //No more action points to spend
            }
            default: return this;   //Invalid effect type (state does NOT advance)
        }

    }
}
