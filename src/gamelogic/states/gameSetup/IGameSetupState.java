package gamelogic.states.gameSetup;

import java.util.ArrayList;

public interface IGameSetupState {
    IGameSetupState NewGame();
    IGameSetupState SelectDefaultJourney();                           //Default Journey
    IGameSetupState SelectCustomJourney(String[] customJourney);     //Custom Journey
    IGameSetupState SelectCrewMemberIn(int pos);
    IGameSetupState SelectCrewMembers(ArrayList<Integer> pos);
    IGameSetupState SetCrewMembersShipLocation(ArrayList<Integer> rooms);
    IGameSetupState SetCrewMemberShipLocation(int roomPos, int crewMemberPos);
    IGameSetupState _StartGame();
    IGameSetupState _EndGame();
    IGameSetupState _Exit();
}
