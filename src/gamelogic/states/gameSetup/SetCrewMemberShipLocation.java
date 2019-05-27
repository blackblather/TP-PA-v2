package gamelogic.states.gameSetup;

import gamelogic.data.Constants;
import gamelogic.data.GameDataHandler;

import java.util.ArrayList;

public class
SetCrewMemberShipLocation extends GameSetupStateAdapter {
    SetCrewMemberShipLocation(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState SetCrewMemberShipLocation(int roomPos, int crewMemberPos) {
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

    @Override
    public IGameSetupState SetCrewMembersShipLocation(ArrayList<Integer> rooms) {
        if(rooms.size() == Constants.MAX_CHOSEN_CREWMEMBERS) {
            for (int i = 0; i < rooms.size(); i++)
                if (rooms.get(i) < 1 || rooms.get(i) > 12){
                    gameDataHandler.SetErrorMessage("Please choose a valid room for every crew member");
                    return this;
                }
            //I've got 2 "for loops" instead of one, because i dont want to add the 1st crew member and have an error on 2nd one, and then have to manually remove the 1st one
            for (int i = 0; i < rooms.size(); i++)
                gameDataHandler.MoveCrewMemberToRoom(rooms.get(i), i);
            return new StartGame(gameDataHandler);
        }
        else
            gameDataHandler.SetErrorMessage("Please choose a room for each crew member");
        return this;
    }
}
