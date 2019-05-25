package gamelogic.data;

import javafx.scene.image.Image;

import java.util.ArrayList;

class Deck {
    private ArrayList<CrewMember> cards = new ArrayList<>(12);
    Deck(){
        cards.add(new CrewMember(1, "file:cards/Doctor.png", "Doctor",1, 1));
        cards.add(new CrewMember(2, "file:cards/CommsOfficer.png", "Comm's Officer",1, 1));
        cards.add(new CrewMember(3, "file:cards/RedShirt.png", "Red Shirt",1, 1));
        cards.add(new CrewMember(4, "file:cards/ScienceOfficer.png", "Science Officer",1, 1));
        cards.add(new CrewMember(5, "file:cards/Engineer.png", "Engineer",1, 1));
        cards.add(new CrewMember(6, "file:cards/Captain.png", "Captain",1, 1, 3));
        cards.add(new CrewMember(7, "file:cards/Commander.png", "Commander",1, 1));
        cards.add(new CrewMember(8, "file:cards/TransporterChief.png", "Transporter Chief",0, 1));
        cards.add(new CrewMember(9, "file:cards/MoralOfficer.png", "Moral Officer",1, 1));
        cards.add(new CrewMember(10, "file:cards/SecurityOfficer.png", "Security Officer",1, 2));
        cards.add(new CrewMember(11, "file:cards/NavigationOfficer.png", "Navigation Officer",2, 1));
        cards.add(new CrewMember(12, "file:cards/ShuttlePilot.png", "Shuttle Pilot",1, 1));
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
