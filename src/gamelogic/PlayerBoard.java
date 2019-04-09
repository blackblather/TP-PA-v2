package gamelogic;

import java.util.ArrayList;

public class PlayerBoard {
    //Private vars
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();
    private Integer inspirationTracker = 0, healthTracker, actionPointsPerTurn, actionPoints, alienCounter = 15;
    //Constructor
    public PlayerBoard(){
        healthTracker = 8;
    }
    boolean AddCrewMember(int pos){
        CrewDeck deck = new CrewDeck();
        try {
            CrewMember crewMember = deck.ChooseCardAt(pos);
            if(crewMember != null){
                crewMembers.add(crewMember);
                return true;
            }
        }catch (IndexOutOfBoundsException ex){
            return false;
        }
        return false;
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
}
