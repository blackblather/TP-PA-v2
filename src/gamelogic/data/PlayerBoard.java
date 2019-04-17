package gamelogic.data;

import java.util.ArrayList;

class PlayerBoard {
    //Private vars
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();
    private int inspirationPoints = 0,
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
    CrewMember GetCrewMemberAt(int pos){
        return crewMembers.get(pos);
    }
    int GetHealth(){
        return health;
    }
    int GetIspirationPoints(){
        return inspirationPoints;
    }

    //Setters
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
    void DecrementOrganicDetonatorCounter(){
        if(organicDetonatorCounter > 0)
            organicDetonatorCounter--;
    }
    void IncrementOrganicDetonatorCounter(){
        if(organicDetonatorCounter < Constants.MAX_ORGANIC_DETONATORS)
            organicDetonatorCounter++;
    }
}
