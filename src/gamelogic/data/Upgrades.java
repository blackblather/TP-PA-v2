package gamelogic.data;

import java.util.ArrayList;
import java.util.Arrays;

class Upgrades {
    //Private vars
    private GameDataHandler gameDataHandler;
    private ArrayList<Effect> upgrade = new ArrayList<>(Arrays.asList(
            new Effect(1, "Add one to Health", ()->gameDataHandler.GetPlayerBoard().HealHealthBy(1)),
            new Effect(1, "Repair one Hull", ()->gameDataHandler.GetShipBoard().HealHullBy(1)),
            new Effect(2, "Build one Organic Detonator", ()->{
                /*Room room = gameDataHandler.GetShipBoard().GetRooms().get(addInputs[0]);//Throws exception if it tries to get an invalid position
                gameDataHandler.GetPlayerBoard().DecrementOrganicDetonatorCounter();    //Throws exception when trying to decrement below 0
                room.IncrementOrganicDetonatorCounter();*/
                gameDataHandler.GetPlayerBoard().IncrementOrganicDetonatorCounter();
            }),
            new Effect(4, "Add one to Movement", (addInputs)->{
                CrewMember crewMember = gameDataHandler.GetPlayerBoard().GetCrewMembers().get(addInputs.get(0));
                crewMember.SetMovement(crewMember.GetMovement()+1);
            }, 1, 0,0),
            new Effect(5, "Build one particle Desperser", ()->{
                gameDataHandler.GetPlayerBoard().IncrementParticleDisperserCounter();   //Throws exception when trying to decrement below 0
            }),
            new Effect(5, "Gain one Sealed Room Token", ()->gameDataHandler.GetPlayerBoard().SetSealedRoomTokens(gameDataHandler.GetPlayerBoard().GetSealedRoomTokens()+1)),
            new Effect(6, "Gain one extra Attack Die", (addInputs)->{
                    CrewMember crewMember = gameDataHandler.GetPlayerBoard().GetCrewMembers().get(addInputs.get(0));
                    crewMember.SetAttack(crewMember.GetAttack()+1);
            }, 1, 0,0),
            new Effect(6, "Add 1 to the result of an Attack Dice", (addInputs) ->{
                    CrewMember crewMember = gameDataHandler.GetPlayerBoard().GetCrewMembers().get(addInputs.get(0));
                    crewMember.SetAttackRollBonus(crewMember.GetAttackRollBonus()+1);
            }, 1, 0,0)));

    //Package-protected constructor
    Upgrades(GameDataHandler gameDataHandler){
        this.gameDataHandler = gameDataHandler;
    }

    //Getters
    Effect GetUpgradeAt(int pos){
        return upgrade.get(pos);
    }
    ArrayList<Effect> GetUpgrades(){
        return upgrade;
    }
}
