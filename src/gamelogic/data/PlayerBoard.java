package gamelogic.data;

import java.util.ArrayList;

class PlayerBoard {
    //Private vars
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();
    private Integer inspirationPoints = 0, health = 8, maxActionPoints = 5, actionPoints, alienCounter = 15;

    //Package-Private functions
    ArrayList<CrewMember> GetCrewMembers(){
        return crewMembers;
    }
    CrewMember GetCrewMemberAt(int pos){ return crewMembers.get(pos); }
    void AddCrewMember(Deck deck, int pos) throws IndexOutOfBoundsException, IllegalAccessException {
        CrewMember crewMember = deck.ChooseCardAt(pos);
        if(crewMember != null)
            crewMembers.add(crewMember);
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
    String CrewMemberToStringAt(int pos) throws IndexOutOfBoundsException{
        return crewMembers.get(pos).toString();
    }
}
