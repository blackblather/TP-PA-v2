package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public class SelectCrewMember extends GameStateAdapter {
    SelectCrewMember(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameState EvaluateChosenCrewMember(int opt) {
        if(opt >= 0 && opt <= gameDataHandler.GetTotalChosenCrewMembers()-1){
            gameDataHandler.AddInputToEffectBuffer(opt);
            if(gameDataHandler.EffectBufferNeedsTrapInput())
                return new SelectTrap(gameDataHandler);
            else if(gameDataHandler.EffectBufferNeedsRoomInput())
                return new SelectRoom(gameDataHandler);
            return new ExecuteEffect(gameDataHandler);
        }
        return this;
    }
}
