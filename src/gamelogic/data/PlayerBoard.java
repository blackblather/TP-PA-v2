package gamelogic.data;

import java.util.ArrayList;

class PlayerBoard {
    //Private vars
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();
    private Integer inspirationPoints = 0, health = 8, maxActionPoints, actionPoints, alienCounter = 15;
    ArrayList<CrewMember> GetCrewMembers(){
        return crewMembers;
    }
    boolean AddCrewMember(int pos){
        Deck deck = new Deck();
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
