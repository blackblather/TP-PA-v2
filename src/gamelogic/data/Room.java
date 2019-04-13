package gamelogic.data;

import java.util.ArrayList;

class Room {
    //Private vars
    private int id, alienCounter;
    private String name;
    private boolean sealed = false;
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();
    //Constructor
    Room(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    //Package-Protected functions
    private boolean IsSealed(){
        return sealed;
    }
    void SealRoom() throws IllegalStateException {
        if(!IsSealed())
            sealed = true;
        else
            throw new IllegalStateException("Cant' seal a room that's already been sealed before.");
    }
    void SpawnAlien(){
        alienCounter++;
    }
    void MoveCrewMemberHere(CrewMember crewMember) throws UnsupportedOperationException{
        //Assume que só se pode escolher dos dois crewmembers escolhidos no "SelectCrewMembers" state.
        //Por causa disso não é preciso escolehr
        if(crewMember.GetRoom() == null || crewMember.GetRoom().GetId() != this.id){
            crewMembers.add(crewMember);
            crewMember.SetRoom(this);
        } else
            throw new UnsupportedOperationException("Can't move a crew member to the same room");
    }
    void KillAlien(){
        if(alienCounter-1>=0)
            alienCounter--;
    }
    //Getters
    Integer GetId(){
        return id;
    }
    String GetName(){
        return name;
    }
    int GetTotalCrewMembers(){
        return crewMembers.size();
    }
}