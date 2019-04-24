package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class CrewPhase extends GameStateAdapter {
    CrewPhase(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    private IGameState UpdatedState(){
        if(gameDataHandler.GetActionPoints() > 0)
            return this;
        else
            return new AlienPhase(gameDataHandler);
    }

    @Override
    public IGameState _CrewPhase() {
        return this;
    }

    @Override
    public IGameState _CrewPhase(int opt) {
        if(gameDataHandler.CanPayForAction(opt)){
            gameDataHandler.ExecuteActionAt(opt);
            gameDataHandler.PayAction(opt);    //Se chega aqui, nenhum excepção ocorreu na instrução anterior (aka, o efeito foi bem executado)
        }
        return UpdatedState();
    }

    @Override
    public IGameState _CrewPhase(int opt, int[] additionalInputs) {
        try{
            if(gameDataHandler.CanPayForAction(opt)){
                gameDataHandler.ExecuteActionAt(opt, additionalInputs);
                gameDataHandler.PayAction(opt);    //Se chega aqui, nenhum excepção ocorreu na instrução anterior (aka, o efeito foi bem executado)
            }
            return UpdatedState();
        } catch (IndexOutOfBoundsException ex){
            return this;
        }
    }
}
