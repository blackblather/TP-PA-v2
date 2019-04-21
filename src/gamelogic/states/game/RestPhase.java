package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class RestPhase extends GameStateAdapter{
    RestPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    private IGameState UpdatedState(){
        if(gameDataHandler.GetInsirationPoints() > 0)
            return this;
        else
            return new JourneyPhase(gameDataHandler);
    }

    @Override
    public IGameState _RestPhase() {
        return new JourneyPhase(gameDataHandler);
    }

    @Override
    public IGameState _RestPhase(int opt) {
        if(gameDataHandler.CanPayForUpgrade(opt)){
            gameDataHandler.ExecuteUpgradeAt(opt);
            gameDataHandler.PayUpgrade(opt);    //Se chega aqui, nenhum excepção ocorreu na instrução anterior (aka, o efeito foi bem executado)
        }
        return UpdatedState();
    }

    @Override
    public IGameState _RestPhase(int opt, int[] additionalInputs) {
        try{
            if(gameDataHandler.CanPayForUpgrade(opt)){
                gameDataHandler.ExecuteUpgradeAt(opt, additionalInputs);
                gameDataHandler.PayUpgrade(opt);    //Se chega aqui, nenhum excepção ocorreu na instrução anterior (aka, o efeito foi bem executado)
            }
            return UpdatedState();
        } catch (IndexOutOfBoundsException ex){
            return this;
        }
    }
}
