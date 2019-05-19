package gamelogic.data;

import gamelogic.states.game.IGameState;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToIntFunction;

public class GameDataHandler {
    //Private classes
    private class EffectBuffer{
        public Effect effect; //Preencher esta variavel com o estado que está a ser manipulado
        public int type;
    }

    //Private vars
    private PlayerBoard playerBoard;
    private ShipBoard shipBoard;
    private Deck deck;
    private Upgrades upgrades;
    private Actions actions;
    private EffectBuffer effectBuffer = new EffectBuffer();
    //Private Lambdas
    //Roll rangre = [NrOfDice, (NrOfDice*6)+1]
    private final ToIntFunction<Integer> rollDiceLambda = (NrOfDice) -> (ThreadLocalRandom.current().nextInt(NrOfDice, (NrOfDice*6)+1))-1;

    //Constructor
    public GameDataHandler(){
        shipBoard = new ShipBoard();
        playerBoard = new PlayerBoard();
        deck = new Deck();
        upgrades = new Upgrades(this);
        actions = new Actions(this, rollDiceLambda);
    }

    //Package-private functions (used ONLY in upgrades/actions classes, that's why it's package-private and not public)
    PlayerBoard GetPlayerBoard(){
        return playerBoard;
    }
    ShipBoard GetShipBoard(){
        return shipBoard;
    }


    //------------------------GameSetup-----------------------------
    //Public functions
    public void CreateCustomJourney(String[] customJourney) throws ArrayStoreException{
        shipBoard.ChangeJourney(customJourney);
    }
    public void AddCrewMember(int pos) throws IndexOutOfBoundsException, IllegalAccessException {
        playerBoard.AddCrewMember(deck, pos-1);
    }
    public void MoveCrewMemberToRoom(int roomPos, int crewMemberPos) throws IndexOutOfBoundsException, UnsupportedOperationException {
        shipBoard.MoveCrewMemberToRoom(roomPos-1, playerBoard.GetCrewMembers().get(crewMemberPos));
    }

    //Getters
    public int GetTotalDeckCards(){
        return deck.GetCards().size();
    }
    public int GetTotalRooms(){
        return shipBoard.GetRooms().size();
    }
    public int GetTotalChosenCrewMembers(){
        return playerBoard.GetCrewMembers().size();
    }
    public int GetTotalCrewMembersInRooms(){
        int total = 0;
        for(Room r : shipBoard.GetRooms())
            total += r.GetTotalCrewMembers();
        return total;
    }
    public ArrayList<String> GetListDeckAvailableCrewMembers(){
        ArrayList<String> info = new ArrayList<>();
        for (CrewMember c : deck.GetCards())
            if(c.IsAvailable())
                info.add("Crew Member " + c.GetId() + ":\n" + c.toString()+"\n.........................");
        return info;
    }
    public String GetChosenCrewMemberToStringAt(int pos){
        return playerBoard.CrewMemberToStringAt(pos);
    }


    //---------------------------Game-------------------------------
    //Public functions
    public void LoadChosenCrewMemberSpecials(){}        //TODO: Alterar (com base nas cartas escolhidas) as infos do shipBoard e do playerBoard AQUI
    public void MoveJourneyTracker() throws UnsupportedOperationException {
        shipBoard.MoveJourneyTracker();
    }
    public void SpawnAliens(){
        int nrOfAliensToSpawn = playerBoard.DecreaseAlienCounterBy(shipBoard.GetNrOfAliensToSpawnOnCurrentJourneyPart());
        shipBoard.SpawnAliens(rollDiceLambda, nrOfAliensToSpawn);
    }
    public boolean PlayerIsAlive(){
        return (shipBoard.GetHull() >= 1 && playerBoard.GetHealth() >= 1);
}                 //TODO: Use function in Alien Phase

    //Effect Buffer
    public void SetEffectBuffer(int type, int opt){
        effectBuffer.type = type;
        switch (effectBuffer.type){
            case 0: effectBuffer.effect = upgrades.GetUpgradeAt(opt); break;
            case 1: effectBuffer.effect = actions.GetActionAt(opt); break;
            default: effectBuffer = null; break;
        }
    }
    public void AddInputToEffectBuffer(int opt){
        effectBuffer.effect.AddInputToBuffer(opt);
    }
    public boolean CanPayForEffectBuffer(){
        switch (effectBuffer.type) {
            case 0: return (playerBoard.GetIspirationPoints() - effectBuffer.effect.GetCost()) >= 0;
            case 1: return (playerBoard.GetActionPoints() - effectBuffer.effect.GetCost()) >= 0;
            default: return false;
        }
    }
    public boolean EffectBufferNeedsAdditionalInput(){
        return effectBuffer.effect.NeedsAdditionalInputs();
    }
    public boolean EffectBufferNeedsCrewMemberInput(){
        return effectBuffer.effect.NeedsCrewMemberInputs();
    }
    public boolean EffectBufferNeedsRoomInput(){
        return effectBuffer.effect.NeedsRoomInputs();
    }
    public boolean EffectBufferNeedsTrapInput(){
        return effectBuffer.effect.NeedsTrapInputs();
    }
    public void ExecuteEffectBuffer(){
        effectBuffer.effect.ExecuteEffect();
    }
    public void PayEffectBuffer() throws InvalidParameterException {
        switch (effectBuffer.type){
            case 0: playerBoard.SetInspirationPoints(playerBoard.GetIspirationPoints() - effectBuffer.effect.GetCost()); break;
            case 1: playerBoard.SetActionPoints(playerBoard.GetActionPoints() - effectBuffer.effect.GetCost()); break;
        }
    }

    //Getters
    public String GetCurrentJourneyPart(){
        return shipBoard.GetCurrentJourneyPart();
    }
    public ArrayList<String> GetDesciptionArray(String type) {
        ArrayList<String> desciption = new ArrayList<>();
        switch (type){
            case "Crew member": {
                for(CrewMember c : playerBoard.GetCrewMembers())
                    desciption.add(c.toString());
            }break;
            case "Room": {
                for(Room r : shipBoard.GetRooms())
                    desciption.add(r.GetName());
            }break;
            case "Upgrade": {
                for(Effect e : upgrades.GetUpgrades())
                    desciption.add(e.toString());
            }break;
            case "Action": {
                for(Effect e : actions.GetActions())
                    desciption.add(e.toString());
            }break;
            case "Trap": desciption = new ArrayList<>(Arrays.asList("Particle Disperser", "Organic Detonator")); break;
        }
        return desciption;
    }
    public int GetEffectBufferType(){
        return effectBuffer.type;
    }
    //Upgrades
    public int GetInsirationPoints(){
        return playerBoard.GetIspirationPoints();
    }
    public int GetTotalUpgrades(){
        return upgrades.GetUpgrades().size();
    }
    //Actions
    public int GetActionPoints(){
        return playerBoard.GetActionPoints();
    }
    public int GetTotalActions(){
        return actions.GetActions().size();
    }
}
