package gamelogic.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;

class PlayerBoard {
    //Private vars
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();
    private int inspirationPoints = 5,      //TODO: DEFAULT VALUE = 0 (5 is for tests)
            health = 8,
            maxActionPoints = 5,
            actionPoints,
            alienCounter = Constants.MAX_ALIENS,
            organicDetonatorCounter = Constants.MAX_ORGANIC_DETONATORS;

    //Package-Private functions
    String CrewMemberToStringAt(int pos) throws IndexOutOfBoundsException{
        return crewMembers.get(pos).toString();
    }
    void AddCrewMember(Deck deck, int pos) throws IndexOutOfBoundsException, IllegalAccessException {
        CrewMember crewMember = deck.ChooseCardAt(pos);
        if(crewMember != null)
            crewMembers.add(crewMember);
    }

    //Getters
    ArrayList<CrewMember> GetCrewMembers(){
        return crewMembers;
    }
    int GetHealth(){
        return health;
    }
    int GetIspirationPoints(){
        return inspirationPoints;
    }

    //Setters
    void SetInspirationPoints(int val) throws InvalidParameterException {
        if(val>=0 && val<=Constants.MAX_INSPIRATION_POINTS )
            inspirationPoints = val;
        else
            throw new InvalidParameterException("Inspiration points me have a value between 0 and " + Constants.MAX_INSPIRATION_POINTS);
    }
    int DecreaseAlienCounterBy(int ammount){
        if(alienCounter-ammount >= 0)
            alienCounter -= ammount;
        else{
            ammount = alienCounter;
            alienCounter = 0;
        }
        return ammount;
    }
    void HealHealthBy(int ammount){
        if(health+ammount <= Constants.MAX_HEALTH)
            health += ammount;
        else{
            health = Constants.MAX_HEALTH;
        }
    }
    void DecrementOrganicDetonatorCounter() throws IllegalStateException{
        if(organicDetonatorCounter > 0)
            organicDetonatorCounter--;
        else
            throw new IllegalStateException("Cant decrement \"Organic Detonator Counter\" below 0");
    }
    void IncrementOrganicDetonatorCounter(){
        if(organicDetonatorCounter < Constants.MAX_ORGANIC_DETONATORS)
            organicDetonatorCounter++;
    }
}
