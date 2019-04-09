package gamelogic;

class Room {
    //Private vars
    private int id, alienCounter;
    private String name;
    private boolean sealed = false;
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

}