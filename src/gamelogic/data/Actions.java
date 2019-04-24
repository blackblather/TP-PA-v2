package gamelogic.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.ToIntFunction;

class Actions {
    //Private vars
    private GameDataHandler gameDataHandler;
    private ToIntFunction<Integer> rollDice;
    private ArrayList<Effect> action = new ArrayList<>(Arrays.asList(
            new Effect(1, "Move", (addInputs)->{
                CrewMember crewMember = gameDataHandler.GetPlayerBoard().GetCrewMembers().get(addInputs[0]);
                //TODO: Se calhar passar esta função recursiva para o GameDataHandler para poder mostrar as opções válidas ao utilizador
                ArrayList<Room> availableRooms = gameDataHandler.GetShipBoard().GetRoomsFrom(crewMember.GetRoom(), 2);
            }, "crew member", "room"),
            new Effect(1, "Attack", (addInputs)->{
                //Só executa o efeito se houver >=1 alien no room
                Room room = gameDataHandler.GetShipBoard().GetRooms().get(addInputs[1]);
                CrewMember crewMember = gameDataHandler.GetPlayerBoard().GetCrewMembers().get(addInputs[0]);
                PlayerBoard playerBoard = gameDataHandler.GetPlayerBoard();
                int attack = crewMember.GetAttack(),
                    minimumAttack = crewMember.GetMinimumAttackRoll(),
                    aliensInRoom = room.GetAlienCounter();
                if (aliensInRoom > 0) {
                    for (int i = 0; i < attack; i++)
                        if (rollDice.applyAsInt(1) >= minimumAttack) {
                            if(room.KillAlien())
                                playerBoard.SetInspirationPoints(playerBoard.GetIspirationPoints()+1);
                            else
                                break;
                        }
                } else
                    throw new IllegalStateException("Room has no aliens");
            }, "crew member", "room"),
            new Effect(1, "Set Trap",  (addInputs)->{
                PlayerBoard playerBoard = gameDataHandler.GetPlayerBoard();
                Room room = playerBoard.GetCrewMembers().get(addInputs[0]).GetRoom();
                switch (addInputs[1]){
                    case 0:{
                        playerBoard.DecrementParticleDisperserCounter();    //Throws exception when trying to decrement below 0
                        room.IncrementParticleDisperserCounter();           //Doesn't throw any exception, but execution only reaches here if no exception was thrown before
                    } break;
                    case 1:{
                        playerBoard.DecrementOrganicDetonatorCounter();    //Throws exception when trying to decrement below 0
                        room.IncrementOrganicDetonatorCounter();           //Doesn't throw any exception, but execution only reaches here if no exception was thrown before
                    } break;
                    default: throw new IllegalArgumentException("Must choose a valid trap.");
                }

            }, "crew member", "trap ('1' -> particle disperser / '2' -> organic detonator)"),
            new Effect(1, "Detonate Particle Disperser", (addInputs)->{
                //Não interessa se existe aliens no room. Pode detonar à mesma e ser cobrado os action points
                Room room = gameDataHandler.GetShipBoard().GetRooms().get(addInputs[0]);
                PlayerBoard playerBoard = gameDataHandler.GetPlayerBoard();
                room.DecrementParticleDisperserCounter();
                playerBoard.IncrementParticleDisperserCounter();
                if(room.KillAlien())
                    playerBoard.SetInspirationPoints(playerBoard.GetIspirationPoints()+1);
            }, "room"),
            new Effect(1, "Seal Room", ()->{})
    ));

    //Package-protected constructor
    Actions(GameDataHandler gameDataHandler, ToIntFunction<Integer> rollDice){
        this.gameDataHandler = gameDataHandler;
        this.rollDice = rollDice;
    }

    void ExecuteActionAt(int pos) throws IndexOutOfBoundsException{
        action.get(pos).ExecuteEffect();
    }
    void ExecuteActionAt(int pos, int[] additionalInputs) throws IndexOutOfBoundsException{
        action.get(pos).ExecuteEffect(additionalInputs);
    }
    void AddAction(Effect newEffect) throws IndexOutOfBoundsException {
        action.add(newEffect);
    }

    //Getters
    Effect GetActionAt(int pos){
        return action.get(pos);
    }
    ArrayList<Effect> GetActions(){
        return action;
    }
}
