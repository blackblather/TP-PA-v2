package gamelogic.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ShipBoard {
    //Private vars
    private int hull = 8;
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
        /* Regular Expression: ^(?<!.)([1-9]|1[0-5])?[Aa][*]?(?!.)$|^(?<!.)R(?!.)$
         * Explanation:
         *
         * ^                    -> Matches the beginning of the input
         * (?<!.)               -> Lookbehind -> There's no character behind
         * ([1-9]|1[0-5])?      -> Matches range [1 - 15], 0 or 1 times
         * [Aa]                 -> Matches literal "A" or "a"
         * [*]?                 -> Matches literal "*", 0 or 1 times
         * (?!.)                -> Lookaheead -> There's no character after
         * $                    -> Matches the end of the input
         * ----------------------------------------------------
         * |                    -> OR
         * ----------------------------------------------------
         * ^                    -> Matches the beginning of the input
         * (?<!.)               -> Lookbehind -> There's no character behind
         * R                    -> Matches literal "R"
         * (?!.)                -> Lookaheead -> There's no character after
         * $                    -> Matches the end of the input
         *
         ***************************************************************************
         *
         * Simple explanation:
         * -> Ou é (opcionalmente) um número de 1 a 15 , seguido (obrigatóriamente) de um "A" ou "a" e de (opcionalmente) um "*"
         * -> Ou é um "R"
         */
        Pattern p = Pattern.compile("^(?<!.)([1-9]|1[0-5])?[Aa][*]?(?!.)$|^(?<!.)R(?!.)$");
        Matcher m = p.matcher(part);
        return m.find();
    }
    void ChangeJourney(String[] customJourney) throws ArrayStoreException{
        /* This is done in 2 steps, because I only want to copy to the "journeyTracker" array, if
         * and only if, all the values in "customJourney" are valid.*/
        final int journeySize = 13;
        if(customJourney.length == journeySize) {
            for (int i = 0; i < journeySize; i++)
                if (!IsValidJourneyPart(customJourney[i])) {
                    throw new ArrayStoreException("Invalid customJourney element in position: " + i);
                }
            //Copies custom journey (garbage collector removes old one)
            journey = new LinkedList<>(Arrays.asList(customJourney));
        }
        else
            throw new ArrayStoreException("customJourney array must have 13 elements.");
    }
    private boolean ReachedEarth(){
        return !journeyTracker.hasNext();
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
