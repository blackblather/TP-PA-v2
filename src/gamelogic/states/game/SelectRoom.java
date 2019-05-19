package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class SelectRoom extends GameStateAdapter {
    SelectRoom(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState EvaluateChosenRoom(int opt) {
        if(opt >= 0 && opt <= gameDataHandler.GetTotalRooms()-1){
            gameDataHandler.AddInputToEffectBuffer(opt);
            return new ExecuteEffect(gameDataHandler);
        }
        return this;
    }
}
