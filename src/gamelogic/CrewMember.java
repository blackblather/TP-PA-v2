package gamelogic;

import java.util.concurrent.ThreadLocalRandom;

public class CrewMember {
    //Private vars
    private String name;
    private Integer movement, attack, minimumAttackRoll = 5;
    private boolean isAvailable = true;
    //-------------------------------------------------
    //Private functions
    private boolean IsValidMovementValue(Integer movement){
        //0 means teleport
        return (movement >= 0 && movement <= 3);
    }
    private boolean IsValidAttackValue(Integer attack){
        return (attack >= 1 && attack <= 3);
    }
    private boolean IsAvailable(){
        return isAvailable;
    }
    //-------------------------------------------------
    //Constrcutor
    public CrewMember(String name, Integer movement, Integer attack){
        if(IsValidMovementValue(movement) && IsValidAttackValue(attack)){
            this.name = name;
            this.movement = movement;
            this.attack = attack;
        }
    }
    public CrewMember(String name, Integer movement, Integer attack, Integer minimumAttackRoll){
        if(IsValidMovementValue(movement) && IsValidAttackValue(attack)){
            this.name = name;
            this.movement = movement;
            this.attack = attack;
            this.minimumAttackRoll = minimumAttackRoll;
        }
    }
    //-------------------------------------------------
    //Package-private functions
    boolean Choose(){
        if(IsAvailable()){
            isAvailable = false;
            return true;
        }
        return false;
    }
    Integer RollAttackDice(){
        //rangre = [attack, (attack*6)+1]
        return ThreadLocalRandom.current().nextInt(attack, (attack*6)+1);
    }
    //-------------------------------------------------
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
    //-------------------------------------------------

}
