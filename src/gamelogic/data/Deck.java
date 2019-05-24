package gamelogic.data;

import java.util.ArrayList;

class Deck {
    private ArrayList<CrewMember> cards = new ArrayList<>(12);
    Deck(){
        cards.add(new CrewMember(1,"Doctor",1, 1));
        cards.add(new CrewMember(2,"Comm's Officer",1, 1));
        cards.add(new CrewMember(3,"Red Shirt",1, 1));
        cards.add(new CrewMember(4,"Science Officer",1, 1));
        cards.add(new CrewMember(5,"Engineer",1, 1));
        cards.add(new CrewMember(6,"Captain",1, 1, 3));
        cards.add(new CrewMember(7,"Commander",1, 1));
        cards.add(new CrewMember(8,"Transporter Chief",0, 1));
        cards.add(new CrewMember(9,"Moral Officer",1, 1));
        cards.add(new CrewMember(10,"Security Officer",1, 2));
        cards.add(new CrewMember(11,"Navigation Officer",2, 1));
        cards.add(new CrewMember(12,"Shuttle Pilot",1, 1));
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
