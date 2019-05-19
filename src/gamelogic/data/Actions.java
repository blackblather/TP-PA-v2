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
                CrewMember crewMember = gameDataHandler.GetPlayerBoard().GetCrewMembers().get(addInputs.get(0));
                ArrayList<Room> availableRooms = gameDataHandler.GetShipBoard().GetRoomsFrom(crewMember.GetRoom(), crewMember.GetMovement());
                Room room = gameDataHandler.GetShipBoard().GetRooms().get(addInputs.get(1));
                int index;
                if((index = availableRooms.indexOf(room)) >= 0){
                    crewMember.GetRoom().RemoveCrewMemberFromHere(crewMember);
                    availableRooms.get(index).MoveCrewMemberHere(crewMember);
                }
            }, 1, 1,0),
            new Effect(1, "Attack", (addInputs)->{
                //Só executa o efeito se houver >=1 alien no room
                CrewMember crewMember = gameDataHandler.GetPlayerBoard().GetCrewMembers().get(addInputs.get(0));
                Room room = crewMember.GetRoom();
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
            }, 1,0,0),
            new Effect(1, "Set Trap",  (addInputs)->{
                PlayerBoard playerBoard = gameDataHandler.GetPlayerBoard();
                Room room = playerBoard.GetCrewMembers().get(addInputs.get(0)).GetRoom();
                switch (addInputs.get(1)){
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

            }, 1,0,1),
            new Effect(1, "Detonate Particle Disperser", (addInputs)->{
                //Não interessa se existe aliens no room. Pode detonar à mesma e será cobrado os action points
                /*Não diz explicitamente no rulebook, mas porque os organic detonators vão para o playerboard
                depois de "rebentarem", assumo que acontece o mesmo com os particle dispersers*/
                Room room = gameDataHandler.GetShipBoard().GetRooms().get(addInputs.get(0));
                if(room.GetTotalCrewMembers() == 0) {
                    PlayerBoard playerBoard = gameDataHandler.GetPlayerBoard();
                    room.DecrementParticleDisperserCounter();
                    playerBoard.IncrementParticleDisperserCounter();
                    room.KillAlien();
                } else
                    throw new TrapKilledMemberException("The particle disperser killed a crew member.");
            }, 0,1,0),
            new Effect(1, "Seal Room", (addInputs)->{
                Room room = gameDataHandler.GetShipBoard().GetRooms().get(addInputs.get(0));
                if(room.GetAlienCounter() == 0 && room.GetTotalCrewMembers() == 0){
                    if(room.GetId() == 3 || room.GetId() == 9 || room.GetId() == 12 ||
                       room.GetId() == 4 || room.GetId() == 11 || room.GetId() == 7) {
                        PlayerBoard playerBoard = gameDataHandler.GetPlayerBoard();
                        playerBoard.SetSealedRoomTokens(playerBoard.GetSealedRoomTokens()-1);   //Throws exception if there's no mmore tokens to use
                        room.SealRoom();
                        for (Room r : room.GetNextRooms()) {
                            r.GetNextRooms().remove(room);
                        }
                        room.GetNextRooms().clear();
                    }
                    else
                        throw new IllegalStateException("Must choose a valid room to seal (Brig, Engineering, Hydroponics, Crew Quarters, Holodeck or Weapons Bay)");
                }
                else
                    throw new IllegalStateException("Room must be empty of crew members and aliens");
            }, 0,1,0)
    ));

    //Package-protected constructor
    Actions(GameDataHandler gameDataHandler, ToIntFunction<Integer> rollDice){
        this.gameDataHandler = gameDataHandler;
        this.rollDice = rollDice;
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
