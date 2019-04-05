package gamelogic;

public class crewMember {
    private Integer movement, attack;

    private boolean IsValidMovementValue(Integer movement){
        return (movement >= 1 && movement <= 3);
    }
    private boolean IsValidAttackValue(Integer attack){
        return (attack == 106 || attack == 206 || attack == 306);
    }

    public crewMember(Integer movement, Integer attack){
        if(IsValidMovementValue(movement) && IsValidAttackValue(attack)){
            this.movement = movement;
            this.attack = attack;
        }
    }
}
