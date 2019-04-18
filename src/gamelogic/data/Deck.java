package gamelogic.data;

import java.util.ArrayList;

public class Deck {
    private ArrayList<CrewMember> cards = new ArrayList<>(12);
    Deck(){
        cards.add(new CrewMember("Captain",1, 1, 3));
        cards.add(new CrewMember("Transporter Chief",0, 1));
    }
    public ArrayList<CrewMember> GetCards(){
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
