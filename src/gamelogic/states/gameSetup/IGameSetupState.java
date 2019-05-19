package gamelogic.states.gameSetup;

public interface IGameSetupState {
    IGameSetupState NewGame();
    IGameSetupState _ChooseJourney();                           //Default Journey
    IGameSetupState _ChooseJourney(String[] customJourney);     //Custom Journey
    IGameSetupState _SelectCrewMembers(int pos);
    IGameSetupState _SetCrewMemberShipLocation(int roomPos, int crewMemberPos);
    IGameSetupState _StartGame();
    IGameSetupState _EndGame();
    IGameSetupState _Exit();
}
