package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

import java.util.Observer;

public class
SetCrewMemberShipLocation extends GameSetupStateAdapter {
    private Observer o = null;
    SetCrewMemberShipLocation(GameDataHandler gameDataHandler, Observer o) {
        super(gameDataHandler, o);
        this.o = o;
    }
    SetCrewMemberShipLocation(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState _SetCrewMemberShipLocation(int roomPos, int crewMemberPos) {
        try{
            gameDataHandler.MoveCrewMemberToRoom(roomPos, crewMemberPos);
            if(gameDataHandler.GetTotalCrewMembersInRooms() < 2)
                return this;
            else {
                if(o != null)
                    return new StartGame(gameDataHandler, o);
                return new StartGame(gameDataHandler);
            }
        } catch (Exception ex){
            return this;
        }
    }
}
