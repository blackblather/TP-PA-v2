package gamelogic.states.gameSetup;

import gamelogic.data.GameDataHandler;

public class
SetCrewMemberShipLocation extends GameSetupStateAdapter {
    SetCrewMemberShipLocation(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState _SetCrewMemberShipLocation(int roomPos, int crewMemberPos) {
        try{
            gameDataHandler.MoveCrewMemberToRoom(roomPos, crewMemberPos);
            if(gameDataHandler.GetTotalCrewMembersInRooms() < 2)
                return this;
            else
                return new StartGame(gameDataHandler);
        } catch (Exception ex){
            return this;
        }
    }
}
