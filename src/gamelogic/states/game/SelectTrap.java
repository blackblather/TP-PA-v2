package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class SelectTrap extends GameStateAdapter {
    SelectTrap(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState EvaluateChosenTrap(int opt) {
        if(opt >= 0 && opt <= 1){
            gameDataHandler.AddInputToEffectBuffer(opt);
            if(gameDataHandler.EffectBufferNeedsRoomInput())
                return new SelectRoom(gameDataHandler);
            return new ExecuteEffect(gameDataHandler);
        }
        return this;
    }
}
