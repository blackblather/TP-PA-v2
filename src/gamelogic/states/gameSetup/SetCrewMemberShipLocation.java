package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public class SetCrewMemberShipLocation extends GameSetupStateAdapter {
    SetCrewMemberShipLocation(EncapsulatedGameData encapsulatedGameData) {
        super(encapsulatedGameData);
    }

    @Override
    public IGameSetupState _SetCrewMemberShipLocation(int roomPos, int crewMemberPos) {
        try{
            encapsulatedGameData.MoveCrewMemberToRoom(roomPos, crewMemberPos);
            if(encapsulatedGameData.GetTotalCrewMembersInRooms() < 2)
                return this;
            else
                return new StartGame(encapsulatedGameData);
        } catch (Exception ex){
            return this;
        }
    }
}
