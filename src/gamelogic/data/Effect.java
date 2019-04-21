package gamelogic.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

class Effect{
    //Private vars
    private int cost;
    private String description;
    private ISimpleEffect simpleEffect = null;
    private IEffect effect = null;
    private ArrayList<String> affectedElements;

    //Constructor
    Effect(int cost, String description, IEffect effect, String ... affectedElements) throws InvalidParameterException {
        if(effect == null)
            throw new InvalidParameterException("Effect cannot be null.");
        this.cost = cost;
        this.description = description;
        this.effect = effect;
        this.affectedElements = new ArrayList<>(Arrays.asList(affectedElements));
    }
    Effect(int cost, String description, ISimpleEffect simpleEffect) throws InvalidParameterException {
        if(simpleEffect == null)
            throw new InvalidParameterException("Effect cannot be null.");
        this.cost = cost;
        this.description = description;
        this.simpleEffect = simpleEffect;
    }

    //Getters
    int GetCost(){
        return cost;
    }
    String GetDescription(){
        return description;
    }
    ArrayList<String> GetAffectedElements(){
        return affectedElements;
    }

    //Package-Private functions
    void ExecuteEffect(int ... additionalInputs){
        if(effect != null){
            if(additionalInputs.length == affectedElements.size())
                effect.execute(additionalInputs);
            else
                throw new InputMismatchException("Effect needs " + affectedElements.size() + "user-inputs to execute properly");
        } else
            throw new IllegalStateException("Effect is not defined");

    }
    void ExecuteEffect(){
        if(simpleEffect != null)
            simpleEffect.execute();
        else
            throw new IllegalStateException("Simple Effect is not defined");
    }
    boolean NeedsAdditionalInputs(){
        return (effect != null);
    }

    //Overrides -> Used mainly in the TextUI
    @Override
    public String toString(){
        return ("Cost: " + cost + "\nDescription: " + description);
    }
}
