package gamelogic.data;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToIntFunction;

/*TODO: Maybe só inicializar o playerBoard e o shipBoard no inicio do jogo, para evitar andar a criar vars e a mudar os saus
        seus parametros depois de inicializadas. Por essa razão é que se costuma fazer override aos construtores default.*/
public class GameDataHandler {
    //Private vars
    private PlayerBoard playerBoard;
    private ShipBoard shipBoard;
    private Deck deck;

    //Private Lambdas
    //Roll rangre = [NrOfDice, (NrOfDice*6)+1]
    private final ToIntFunction<Integer> rollDiceLambda = (NrOfDice) -> (ThreadLocalRandom.current().nextInt(NrOfDice, (NrOfDice*6)+1))-1;

    //Constructor
    public GameDataHandler(){
        shipBoard = new ShipBoard();
        playerBoard = new PlayerBoard();
        deck = new Deck();
    }

    //Public functions -> GameSetup
    public void CreateCustomJourney(String[] customJourney) throws ArrayStoreException{
        shipBoard.ChangeJourney(customJourney);
    }
    public void AddCrewMember(int pos) throws IndexOutOfBoundsException, IllegalAccessException {
        playerBoard.AddCrewMember(deck, pos);
    }
    public void MoveCrewMemberToRoom(int roomPos, int crewMemberPos) throws IndexOutOfBoundsException, UnsupportedOperationException {
        shipBoard.MoveCrewMemberToRoom(roomPos, playerBoard.GetCrewMemberAt(crewMemberPos));
    }
    //Getters -> GameSetup
    public int GetTotalDeckCards(){
        return deck.GetCards().size();
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

    //Public functions -> Game
    public void LoadChosenCrewMemberSpecials(){
        //TODO: Alterar as infos no shipBoard e no playerBoard aqui (com base nas cartas escolhidas)
    }
    public void MoveJourneyTracker() throws UnsupportedOperationException {
        shipBoard.MoveJourneyTracker();
    }
    public void SpawnAliens(){
        shipBoard.SpawnAliens(rollDiceLambda);
    }
    public boolean PlayerIsAlive(){
        return (shipBoard.GetHull() >= 1 && playerBoard.GetHealth() >= 1);
    }
    //Getters -> Game
    public String GetCurrentJourneyPart(){
        return shipBoard.GetCurrentJourneyPart();
    }
}
