package gamelogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.ToIntFunction;

public class ShipBoard {
    //Private vars
    private Integer hull = 8;
    private LinkedList<String> journey = new LinkedList<>(Arrays.asList("2A","3A","4A","5A*","R","4A","5A","6A*","R","6A","7A*","R","8A"));
    private Iterator<String> journeyTracker = journey.iterator();
    private String currentJourneyPart;
    private ArrayList<Room> rooms = new ArrayList<>(Arrays.asList(
            new Room(1, "Bridge"),
            new Room(2, "Sick Bay"),
            new Room(3, "Brig"),
            new Room(4, "Crew Quarters"),
            new Room(5, "Conference Room"),
            new Room(6, "Shuttle Bay"),
            new Room(7, "Weapons Bay"),
            new Room(8, "Mess Hall"),
            new Room(9, "Engineering"),
            new Room(10, "Astrometrics"),
            new Room(11, "Holodeck"),
            new Room(12, "Hydroponics")
    ));
    //Private functions
    private boolean IsValidJourneyPart(String part){
        //TODO: IsValidJourneyPart
        /*Há 2 opções:
         * -> Ou é um "R"
         * -> Ou é um numero, seguido de um "A" e de (opcionalmente) um "*"
         * TODO: Usar RegEx para isto
         */
        return false;
    }
    private void CreateCustomJourney(String[] customJourney) throws ArrayStoreException{
        /* This is done in 2 steps, because I only want to copy to the "journeyTracker" array, if
         * and only if, all the values in "customJourney" are valid.*/
        if(customJourney.length == 13) {
            for (int i = 1; i < 14; i++)
                if (IsValidJourneyPart(customJourney[i]))
                    throw new ArrayStoreException("Invalid customJourney element in position: " + i);
            System.arraycopy(customJourney, 0, journey, 1, 13);
            //TODO: Fix this part above
        }
        else
            throw new ArrayStoreException("customJourney array must have 13 elements.");
    }
    private boolean ReachedEarth(){
        return !journeyTracker.hasNext();
    }
    //Constructor
    public ShipBoard(){
    }
    public ShipBoard(String[] customJourney) throws ArrayStoreException{
        CreateCustomJourney(customJourney);
    }
    //Package-Protected functions
    ArrayList<Room> GetRooms(){
        return rooms;
    }
    void DamageHullBy(int value){
        if(hull-value >= 0)
            hull -= value;
        else
            hull = 0;
    }
    void HealHullBy(int value){
        if(hull+value <= 12)
            hull += value;
        else
            hull = 12;
    }
    String GetCurrentJourneyPart(){
        return currentJourneyPart;
    }
    void MoveJourneyTracker() throws Exception{
        if(!ReachedEarth())
            currentJourneyPart = journeyTracker.next();
        else{
            throw new Exception("Reached Earth");
        }
    }
    void SpawnAliens(int nrAliens, final ToIntFunction RollDice){
        for(int i = 0; i < nrAliens; i++)
            rooms.get(RollDice.applyAsInt(2)).SpawnAlien();
    }
}
