package gamelogic.data;

import java.util.ArrayList;

class Deck {
    private ArrayList<CrewMember> cards = new ArrayList<>(12);
    Deck(){
        cards.add(new CrewMember(1,"Captain",1, 1, 3));
        cards.add(new CrewMember(2,"Transporter Chief",0, 1));
    }
    ArrayList<CrewMember> GetCards(){
        return cards;
    }
    CrewMember ChooseCardAt(int pos) throws IndexOutOfBoundsException, IllegalAccessException {
        CrewMember crewMember = cards.get(pos);
        if(crewMember.Choose())
            return crewMember;
        else
            throw new IllegalAccessException("Can't choose the same card twice.");
    }
}
