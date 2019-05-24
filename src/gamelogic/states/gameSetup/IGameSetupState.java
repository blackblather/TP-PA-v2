package gamelogic.states.gameSetup;

public interface IGameSetupState {
    IGameSetupState NewGame();
    IGameSetupState SelectDefaultJourney();                           //Default Journey
    IGameSetupState SelectCustomJourney(String[] customJourney);     //Custom Journey
    IGameSetupState SelectCrewMemberIn(int pos);
    IGameSetupState _SetCrewMemberShipLocation(int roomPos, int crewMemberPos);
    IGameSetupState _StartGame();
    IGameSetupState _EndGame();
    IGameSetupState _Exit();
}
