package gamelogic.data;

import java.security.InvalidParameterException;

public class CrewMember {
    //Private vars
    private String name;
    private int movement, attack, minimumAttackRoll = 5, attackRollBonus = 0;    //1 of attack -> means 1 D6 die
    private Room currentRoom;
    private boolean isAvailable = true;

    //Private functions
    private boolean IsValidMovementValue(int movement){
        //0 means teleport
        return (movement >= 0 && movement <= Constants.MAX_CREWMEMBER_MOVEMENT);
    }
    private boolean IsValidAttackValue(int attack){
        return (attack >= 1 && attack <= Constants.MAX_CREWMEMBER_ATTACK);
    }

    //Constrcutor
    CrewMember(String name, Integer movement, Integer attack){
        if(IsValidMovementValue(movement) && IsValidAttackValue(attack)){
            this.name = name;
            this.movement = movement;
            this.attack = attack;
        }
    }
    CrewMember(String name, Integer movement, Integer attack, Integer minimumAttackRoll){
        if(IsValidMovementValue(movement) && IsValidAttackValue(attack)){
            this.name = name;
            this.movement = movement;
            this.attack = attack;
            this.minimumAttackRoll = minimumAttackRoll;
        }
    }

    //Package-private functions
    boolean Choose(){
        if(isAvailable){
            isAvailable = false;
            return true;
        }
        return false;
    }

    //Getters
    public String GetName(){
        return name;
    }
    public Integer GetMovement(){
        return movement;
    }
    public Integer GetAttack(){
        return attack;
    }
    public Integer GetMinimumAttackRoll(){
        return minimumAttackRoll;
    }
    public boolean IsAvailable(){
        return isAvailable;
    }
    Room GetRoom(){
        return currentRoom;
    }
    int GetAttackRollBonus(){
        return attackRollBonus;
    }

    //Setters
    void SetRoom(Room room){
        currentRoom = room;
    }
    void SetMovement(int val){
        if(IsValidMovementValue(val))
            movement = val;
        else
            throw new InvalidParameterException("Movement must have a value between 0 and " + Constants.MAX_CREWMEMBER_MOVEMENT);
    }
    void SetAttack(int val){
        if(IsValidAttackValue(val))
            attack = val;
        else
            throw new InvalidParameterException("Attack must have a value between 0 and " + Constants.MAX_CREWMEMBER_ATTACK);
    }
    void SetAttackRollBonus(int val){
        if(val > 0)
            attackRollBonus = val;
        else
            throw new InvalidParameterException("Attack Roll Bonus must have a value greater than 0");
    }

    //Overrides
    @Override
    public String toString(){
        //TODO: List specials
        return "Name: " + this.name + "\nMovement: " + this.movement + "\nAttack: " + this.attack;
    }
}
