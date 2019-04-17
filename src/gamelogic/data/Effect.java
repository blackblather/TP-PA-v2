package gamelogic.data;

import java.security.InvalidParameterException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class Effect{
    //Private vars
    private int cost;
    private String description, affectedElement;
    private Consumer<GameDataHandler> simpleEffect = null;
    private BiConsumer<GameDataHandler, Integer> effectWithValue = null;

    //Constructor
    Effect(int cost, String description, Consumer<GameDataHandler> simpleEffect) throws InvalidParameterException {
        if(simpleEffect == null)
            throw new InvalidParameterException("Effect cannot be null.");
        this.cost = cost;
        this.description = description;
        this.simpleEffect = simpleEffect;
    }

    Effect(int cost, String affectedElement, String description, BiConsumer<GameDataHandler, Integer> effectWithValue) throws InvalidParameterException {
        if(effectWithValue == null)
            throw new InvalidParameterException("Effect cannot be null.");
        this.affectedElement = affectedElement;
        this.cost = cost;
        this.description = description;
        this.effectWithValue = effectWithValue;
    }

    //Getters
    int GetCost(){
        return cost;
    }
    String GetDescription(){
        return description;
    }
    String GetAffectedElement(){
        return affectedElement;
    }

    //Package-protected functions
    void ExecuteEffect(GameDataHandler gameDataHandler){
        simpleEffect.accept(gameDataHandler);
    }
    void ExecuteEffect(GameDataHandler gameDataHandler, Integer value) throws IndexOutOfBoundsException{
        effectWithValue.accept(gameDataHandler,value);
    }
    boolean NeedsAditionalInput(){
        return (effectWithValue != null);
    }

    //Overrides -> Used mainly in the TextUI
    @Override
    public String toString(){
        return ("Cost: " + cost + "\nDescription: " + description);
    }
}
