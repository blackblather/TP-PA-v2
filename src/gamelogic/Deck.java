package gamelogic;

import java.util.ArrayList;

public class Deck {
    private ArrayList<CrewMember> cards = new ArrayList<>(12);
    public Deck(){
        //cards.add(new CrewMember("Doctor",1, 106, 3));
    }
    public ArrayList<CrewMember> GetCards(){
        return cards;
    }
    public CrewMember ChooseCardAt(Integer pos) throws IndexOutOfBoundsException{
        CrewMember crewMember = cards.get(pos);
        if(crewMember.Choose())
            return crewMember;
        return null;
    }
}
