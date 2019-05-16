package gamelogic.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToIntFunction;

/*TODO: Maybe só inicializar o playerBoard e o shipBoard no inicio do jogo, para evitar andar a criar vars e a mudar os saus
        seus parametros depois de inicializadas. Por essa razão é que se costuma fazer override aos construtores default.*/
public class GameDataHandler {
    //Private vars
    private PlayerBoard playerBoard;
    private ShipBoard shipBoard;
    private Deck deck;
    private Upgrades upgrades;
    private Actions actions;

    //Private Lambdas
    //Roll rangre = [NrOfDice, (NrOfDice*6)+1]
    private final ToIntFunction<Integer> rollDiceLambda = (NrOfDice) -> (ThreadLocalRandom.current().nextInt(NrOfDice, (NrOfDice*6)+1))-1;

    //Constructor
    public GameDataHandler(){
        shipBoard = new ShipBoard();
        playerBoard = new PlayerBoard();
        deck = new Deck();
        upgrades = new Upgrades(this);
        actions = new Actions(this, rollDiceLambda);
    }

    //Package-private functions (used ONLY in upgrades/actions classes, that's why it's package-private and not public)
    PlayerBoard GetPlayerBoard(){
        return playerBoard;
    }
    ShipBoard GetShipBoard(){
        return shipBoard;
    }


    //------------------------GameSetup-----------------------------
    //Public functions
    public void CreateCustomJourney(String[] customJourney) throws ArrayStoreException{
        shipBoard.ChangeJourney(customJourney);
    }
    public void AddCrewMember(int pos) throws IndexOutOfBoundsException, IllegalAccessException {
        playerBoard.AddCrewMember(deck, pos-1);
    }
    public void MoveCrewMemberToRoom(int roomPos, int crewMemberPos) throws IndexOutOfBoundsException, UnsupportedOperationException {
        shipBoard.MoveCrewMemberToRoom(roomPos-1, playerBoard.GetCrewMembers().get(crewMemberPos));
    }

    //Getters
    public int GetTotalDeckCards(){
        return deck.GetCards().size();
    }
    public int GetTotalRooms(){
        return shipBoard.GetRooms().size();
    }
    public int GetTotalChosenCrewMembers(){
        return playerBoard.GetCrewMembers().size();
    }
    public int GetTotalCrewMembersInRooms(){
        int total = 0;
        for(Room r : shipBoard.GetRooms())
            total += r.GetTotalCrewMembers();
        return total;
    }
    public ArrayList<String> GetListDeckAvailableCrewMembers(){
        ArrayList<String> info = new ArrayList<>();
        for (CrewMember c : deck.GetCards())
            if(c.IsAvailable())
                info.add("Crew Member " + c.GetId() + ":\n" + c.toString()+"\n.........................");
        return info;
    }
    public String GetChosenCrewMemberToStringAt(int pos){
        return playerBoard.CrewMemberToStringAt(pos);
    }


    //---------------------------Game-------------------------------
    //Public functions
    public void LoadChosenCrewMemberSpecials(){}        //TODO: Alterar (com base nas cartas escolhidas) as infos do shipBoard e do playerBoard AQUI
    public void MoveJourneyTracker() throws UnsupportedOperationException {
        shipBoard.MoveJourneyTracker();
    }
    public void SpawnAliens(){
        int nrOfAliensToSpawn = playerBoard.DecreaseAlienCounterBy(shipBoard.GetNrOfAliensToSpawnOnCurrentJourneyPart());
        shipBoard.SpawnAliens(rollDiceLambda, nrOfAliensToSpawn);
    }
    public boolean PlayerIsAlive(){
        return (shipBoard.GetHull() >= 1 && playerBoard.GetHealth() >= 1);
}                 //TODO: Use function in Alien Phase

    //Upgrades
    public boolean CanPayForUpgrade(int opt){
        return ((playerBoard.GetIspirationPoints() - upgrades.GetUpgradeAt(opt-1).GetCost()) >= 0);
    }
    public boolean UpgradeNeedsAditionalInput(int opt){
        return (upgrades.GetUpgradeAt(opt-1).NeedsAdditionalInputs());
    }
    public void ExecuteUpgradeAt(int pos){
        upgrades.ExecuteUpgradeAt(pos-1);
    }
    public void ExecuteUpgradeAt(int pos, int[] additionalInputs){
        for(int i = 0; i < additionalInputs.length; i++)    //Decrements additionalInputs, because arrays are zero-based, but user options are one-based
            additionalInputs[i]--;
        upgrades.ExecuteUpgradeAt(pos-1, additionalInputs);
    }
    public void PayUpgrade(int opt) throws InvalidParameterException {
        playerBoard.SetInspirationPoints(playerBoard.GetIspirationPoints() - upgrades.GetUpgradeAt(opt-1).GetCost());
    }
    //Actions
    public boolean CanPayForAction(int opt){
        return ((playerBoard.GetActionPoints() - actions.GetActionAt(opt-1).GetCost()) >= 0);
    }
    public boolean ActionNeedsAditionalInput(int opt){
        return (actions.GetActionAt(opt-1).NeedsAdditionalInputs());
    }
    public void ExecuteActionAt(int pos){
        actions.ExecuteActionAt(pos-1);
    }
    public void ExecuteActionAt(int pos, int[] additionalInputs){
        for(int i = 0; i < additionalInputs.length; i++)    //Decrements additionalInputs, because arrays are zero-based, but user options are one-based
            additionalInputs[i]--;
        actions.ExecuteActionAt(pos-1, additionalInputs);
    }
    public void PayAction(int opt) throws InvalidParameterException {
        playerBoard.SetActionPoints(playerBoard.GetActionPoints() - actions.GetActionAt(opt-1).GetCost());
    }


    //Getters
    public String GetCurrentJourneyPart(){
        return shipBoard.GetCurrentJourneyPart();
    }
    //Upgrades
    public int GetInsirationPoints(){
        return playerBoard.GetIspirationPoints();
    }
    public ArrayList<String> GetUpgradesDesciption() {
        ArrayList<String> upgradesDesciption = new ArrayList<>();
        for(Effect e : upgrades.GetUpgrades())
            upgradesDesciption.add(e.toString());
        return upgradesDesciption;
    }
    public int GetTotalUpgrades(){
        return upgrades.GetUpgrades().size();
    }
    public ArrayList<String> GetUpgradeAffetedElementsAt(int opt){ return upgrades.GetUpgradeAt(opt-1).GetAffectedElements(); }
    //Actions
    public int GetActionPoints(){
        return playerBoard.GetActionPoints();
    }
    public ArrayList<String> GetActionsDesciption() {
        ArrayList<String> actionsDesciption = new ArrayList<>();
        for(Effect e : actions.GetActions())
            actionsDesciption.add(e.toString());
        return actionsDesciption;
    }
    public int GetTotalActions(){
        return actions.GetActions().size();
    }
    public ArrayList<String> GetActionAffetedElementsAt(int opt){ return actions.GetActionAt(opt-1).GetAffectedElements(); }
}
