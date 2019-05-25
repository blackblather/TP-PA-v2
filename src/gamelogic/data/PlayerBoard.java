package gamelogic.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;

class PlayerBoard {
    //Private vars
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();
    private int inspirationPoints = Constants.INITIAL_INSPIRATION_POINTS,
            health = Constants.INITIAL_HEALTH,
            maxActionPoints = Constants.INITIAL_MAX_ACTION_POINTS,
            actionPoints = maxActionPoints,
            alienCounter = Constants.MAX_ALIENS,
            organicDetonatorCounter = Constants.INITIAL_ORGANIC_DETONATORS,
            particleDisperserCounter = Constants.INITIAL_PARTICLE_DISPERSERS,
            sealedRoomTokens = Constants.INITIAL_SEALED_ROOMS;

    //Package-Private functions
    void AddCrewMember(Deck deck, int pos) throws IndexOutOfBoundsException, IllegalAccessException {
        CrewMember crewMember = deck.ChooseCardAt(pos);
        if(crewMember != null)
            crewMembers.add(crewMember);
    }

    //Getters
    ArrayList<CrewMember> GetCrewMembers(){
        return crewMembers;
    }
    int GetHealth(){
        return health;
    }
    int GetIspirationPoints(){
        return inspirationPoints;
    }
    int GetSealedRoomTokens(){
        return sealedRoomTokens;
    }
    int GetActionPoints(){
        return actionPoints;
    }

    //Setters
    void SetInspirationPoints(int val) throws InvalidParameterException {
        if(val>=0)
            inspirationPoints = val;
        else
            throw new InvalidParameterException("Inspiration points must have a value greater or equal to 0");
    }
    void SetActionPoints(int val) throws InvalidParameterException {
        if(val>=0 && val<=maxActionPoints)
            actionPoints = val;
        else if(val < 0)
            throw new InvalidParameterException("Action points must have a value between 0 and " + maxActionPoints);
    }
    void SetSealedRoomTokens(int val){
        if(val>=0)
            sealedRoomTokens = val;
        else
            throw new InvalidParameterException("The number of sealed room tokens must be greater then 0");
    }
    int DecreaseAlienCounterBy(int ammount){
        if(alienCounter-ammount >= 0)
            alienCounter -= ammount;
        else{
            ammount = alienCounter;
            alienCounter = 0;
        }
        return ammount;
    }
    void HealHealthBy(int ammount){
        if(health+ammount <= Constants.MAX_HEALTH)
            health += ammount;
        else{
            health = Constants.MAX_HEALTH;
        }
    }
    void DecrementOrganicDetonatorCounter() throws IllegalStateException{
        if(organicDetonatorCounter > 0)
            organicDetonatorCounter--;
        else
            throw new IllegalStateException("Cant decrement Organic Detonator Counter below 0");
    }
    void IncrementOrganicDetonatorCounter(){
        organicDetonatorCounter++;
    }
    void DecrementParticleDisperserCounter() throws IllegalStateException{
        if(particleDisperserCounter > 0)
            particleDisperserCounter--;
        else
            throw new IllegalStateException("Cant decrement Particle Disperser Counter below 0");
    }
    void IncrementParticleDisperserCounter(){
        particleDisperserCounter++;
    }
}
