package gamelogic.data;

import java.security.InvalidParameterException;
import java.util.*;

class Effect{
    //Private vars
    private int cost, affectsCrewMember = 0, affectsRoom = 0, affectsTrap = 0;
    private String description;
    private ISimpleEffect simpleEffect = null;
    private IEffect effect = null;
    private List<Integer> additionalInputs = new LinkedList<>();


    //Constructor
    Effect(int cost, String description, IEffect effect, int affectsCrewMember, int affectsRoom, int affectsTrap) throws InvalidParameterException {
        if(effect == null)
            throw new InvalidParameterException("effect cannot be null.");
        this.affectsCrewMember = affectsCrewMember;
        this.affectsRoom = affectsRoom;
        this.affectsTrap = affectsTrap;
        this.cost = cost;
        this.description = description;
        this.effect = effect;
    }
    Effect(int cost, String description, ISimpleEffect simpleEffect) throws InvalidParameterException {
        if(simpleEffect == null)
            throw new InvalidParameterException("effect cannot be null.");
        this.cost = cost;
        this.description = description;
        this.simpleEffect = simpleEffect;
    }

    //Private functions
    private int GetTotalAdditionalInputs(){
        return affectsCrewMember + affectsRoom +  affectsTrap;
    }
    private boolean AdditionalInputsAreFilledIn(){
        return additionalInputs.size() == GetTotalAdditionalInputs();
    }

    //Getters
    int GetCost(){
        return cost;
    }
    String GetDescription(){
        return description;
    }

    //Package-Private functions
    void ExecuteEffect(){
        if(effect != null){
            if(AdditionalInputsAreFilledIn())
                effect.execute(additionalInputs);
            else
                throw new InputMismatchException("effect needs " + GetTotalAdditionalInputs() + "user-inputs to execute properly");
        } else
            simpleEffect.execute();

    }
    boolean NeedsAdditionalInputs(){
        return effect != null;
    }
    boolean NeedsCrewMemberInputs(){
        return affectsCrewMember > 0;
    }
    boolean NeedsRoomInputs(){
        return affectsRoom > 0;
    }
    boolean NeedsTrapInputs(){
        return affectsTrap > 0;
    }
    void AddInputToBuffer(Integer input){
        if(!AdditionalInputsAreFilledIn())
            additionalInputs.add(input);
    }


    //Overrides -> Used mainly in the TextUI
    @Override
    public String toString(){
        return ("Cost: " + cost + "\nDescription: " + description);
    }
}
