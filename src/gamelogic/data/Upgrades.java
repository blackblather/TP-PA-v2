package gamelogic.data;

import java.util.ArrayList;
import java.util.Arrays;

class Upgrades {
    //Private vars
    private GameDataHandler gameDataHandler;
    private ArrayList<Effect> upgrade = new ArrayList<>(Arrays.asList(
            new Effect(1, "Add one to Health", (gdh)->gdh.GetPlayerBoard().HealHealthBy(1)),
            new Effect(1, "Repair one Hull", (gdh)->gdh.GetShipBoard().HealHullBy(1)),
            new Effect(2, "room", "Build one Organic Detonator", (gdh, val)->{
                    Room room = gdh.GetShipBoard().GetRooms().get(val);         //Throws exception if it tries to get an invalid position
                    gdh.GetPlayerBoard().DecrementOrganicDetonatorCounter();    //Throws exception when trying to decrement below 0
                    room.IncrementOrganicDetonatorCounter();                    //Throws exception when trying to increment above Constants.MAX_ORGANIC_DETONATORS (never going to happen on this scenario)
            }),
            new Effect(4, "crew member", "Add one to Movement", (gdh, val)->{
                CrewMember crewMember = gdh.GetPlayerBoard().GetCrewMembers().get(val);
                crewMember.SetMovement(crewMember.GetMovement()+1);
            })/*,
            new Effect(5, "Build one particle Desperser"),
            new Effect(5, "Gain one Sealed Room Token"),
            new Effect(6, "Gain one extra Attack Die"),
            new Effect(6, "Add 1 to the result of an Attack Dice")*/));

    //Package-protected constructor
    Upgrades(GameDataHandler gameDataHandler){
        this.gameDataHandler = gameDataHandler;
    }

    void ExecuteUpgradeAt(int pos) throws IndexOutOfBoundsException{
        upgrade.get(pos).ExecuteEffect(gameDataHandler);
    }
    void ExecuteUpgradeAt(int pos, int value) throws IndexOutOfBoundsException{
        upgrade.get(pos).ExecuteEffect(gameDataHandler, value);
    }
    void ReplaceEffectAt(int pos, Effect newEffect) throws IndexOutOfBoundsException {
        upgrade.set(pos, newEffect);
    }

    //Getters
    Effect GetUpgradeAt(int pos){
        return upgrade.get(pos);
    }
    ArrayList<Effect> GetUpgrades(){
        return upgrade;
    }
}
