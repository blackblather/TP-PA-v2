package gamelogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ShipBoard {
    //Private vars
    private Integer hull = 8;
    private Integer journeyTracker = 0;
    private LinkedList<String> journey = new LinkedList<>(Arrays.asList("S","2A","3A","4A","5A*","R","4A","5A","6A*","R","6A","7A*","R","8A","E"));
    private ArrayList<Room> rooms = new ArrayList<>();
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
    private void CreateDefaultRooms(){
        rooms.add(new Room(1, "Bridge"));
        rooms.add(new Room(2, "Sick Bay"));
        rooms.add(new Room(3, "Brig"));
        rooms.add(new Room(4, "Crew Quarters"));
        rooms.add(new Room(5, "Conference Room"));
        rooms.add(new Room(6, "Shuttle Bay"));
        rooms.add(new Room(7, "Weapons Bay"));
        rooms.add(new Room(8, "Mess Hall"));
        rooms.add(new Room(9, "Engineering"));
        rooms.add(new Room(10, "Astrometrics"));
        rooms.add(new Room(11, "Holodeck"));
        rooms.add(new Room(12, "Hydroponics"));
    }
    //Constructor
    public ShipBoard(){
        CreateDefaultRooms();
    }
    public ShipBoard(String[] customJourney) throws ArrayStoreException, ArrayIndexOutOfBoundsException{
        /* This is done in 2 steps, because I only want to copy to the "journeyTracker" array, if
         * and only if, all the values in "customJourney" are valid.*/
        if(customJourney.length == 13) {
            for (int i = 1; i < 14; i++)
                if (IsValidJourneyPart(customJourney[i]))
                    throw new ArrayStoreException("Invalid customJourney element in position: " + i);
            System.arraycopy(customJourney, 0, journey, 1, 13);
            CreateDefaultRooms();
        }
        else
            throw new ArrayIndexOutOfBoundsException("customJourney array must have 13 elements.");
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
    Integer GetJourneyTracker(){
        return journeyTracker;
    }

}
