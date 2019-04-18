package gamelogic.data;

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

    //Private Lambdas
    //Roll rangre = [NrOfDice, (NrOfDice*6)+1]
    private final ToIntFunction<Integer> rollDiceLambda = (NrOfDice) -> (ThreadLocalRandom.current().nextInt(NrOfDice, (NrOfDice*6)+1))-1;

    //Constructor
    public GameDataHandler(){
        shipBoard = new ShipBoard();
        playerBoard = new PlayerBoard();
        deck = new Deck();
        upgrades = new Upgrades(this);
    }

    //Package-private functions
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
        shipBoard.MoveCrewMemberToRoom(roomPos-1, playerBoard.GetCrewMemberAt(crewMemberPos));
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
    public String GetChosenCrewMemberToStringAt(int pos){
        return playerBoard.CrewMemberToStringAt(pos);
    }
    public Deck GetDeck()
    {
        return deck;
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
    public boolean UpgradeNeedsAditionalInput(int opt){
        return (upgrades.GetUpgradeAt(opt-1).NeedsAditionalInput());
    }
    public void ExecuteUpgradeAt(int pos){
        upgrades.ExecuteUpgradeAt(pos-1);
    }
    public void ExecuteUpgradeAt(int pos, int value){
        upgrades.ExecuteUpgradeAt(pos-1, value);
    }

    //Getters
    public String GetCurrentJourneyPart(){
        return shipBoard.GetCurrentJourneyPart();
    }
    public int GetInsirationPoints(){
        return playerBoard.GetIspirationPoints();
    }
    public boolean CanPayForUpgrade(int opt){
        return ((playerBoard.GetIspirationPoints() - upgrades.GetUpgradeAt(opt-1).GetCost()) > 0);
    }
    public void PayUpgrade(int opt){
        playerBoard.SetInspirationPoints(playerBoard.GetIspirationPoints() - upgrades.GetUpgradeAt(opt-1).GetCost());
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
    public String GetUpgradeAffetedElementAt(int opt){ return upgrades.GetUpgradeAt(opt-1).GetAffectedElement(); }
}
