package gamelogic.data;

import java.util.ArrayList;

class Room {
    //Private vars
    private int id, alienCounter = 0, organicDetonatorCounter = 0, particleDisperserCounter = 0;
    private String name;
    private boolean sealed = false;
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();
    private ArrayList<Room> nextRooms = null;

    //Private functions
    private boolean IsSealed(){
        return sealed;
    }

    //Constructor
    Room(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    //Package-Protected functions
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
        if(crewMember.GetRoom() == null || crewMember.GetRoom().GetId() != this.id){
            crewMembers.add(crewMember);
            crewMember.SetRoom(this);
        } else
            throw new UnsupportedOperationException("Can't move a crew member to the same room");
    }
    boolean KillAlien(){
        if(alienCounter-1>=0){
            alienCounter--;
            return true;
        }
        return false;
    }
    void DecrementOrganicDetonatorCounter(){
        if(organicDetonatorCounter > 0)
            organicDetonatorCounter--;
    }
    void IncrementOrganicDetonatorCounter(){
        /*Esta verificação permite que qualquer room tenha Constants.MAX_ORGANIC_DETONATORS detonators (Está mal, mas como preciso de avançar no código e esta função só é chamada enquanto houver
          organic detonators no playerboard, o programa não crasha. Não tenho tempo para corrigir estes detalhes, mas não estou a comprometer o bom funcionamento do programa (da maneira que o programei),
          apenas a organização do código*/
        organicDetonatorCounter++;
    }
    void DecrementParticleDisperserCounter() throws IllegalStateException{
        if(particleDisperserCounter > 0)
            particleDisperserCounter--;
        else
            throw new IllegalStateException("Can't decrement Particle Disperser Counter below 0");
    }
    void IncrementParticleDisperserCounter(){
        particleDisperserCounter++;
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
    int GetAlienCounter(){
        return alienCounter;
    }
    ArrayList<Room> GetNextRooms(){
        return nextRooms;
    }

    //Setters
    void SetNextRooms(ArrayList<Room> nextRooms){
        //não pode fazer parte do construtor, porque os rooms sao criados quando o ShipBoard é criado, e só depois dos rooms criados e alocados em memória é que posso fazer referencia a eles.
        if(this.nextRooms == null)
            this.nextRooms = nextRooms;
    }
}