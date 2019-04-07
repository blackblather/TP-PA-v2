package gamelogic;

import javax.crypto.IllegalBlockSizeException;

public class Room {
    //Private vars
    private Integer id;
    private String name;
    private boolean sealed = false;
    //Constructor
    Room(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    //Package-Protected functions
    boolean IsSealed(){
        return sealed;
    }
    void SealRoom() throws IllegalStateException {
        if(!IsSealed())
            sealed = true;
        else
            throw new IllegalStateException("Cant' seal a room that's already been sealed before.");
    }
    //Getters
    Integer GetId(){
        return id;
    }
    String GetName(){
        return name;
    }
}