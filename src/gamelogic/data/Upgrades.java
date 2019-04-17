package gamelogic.data;

import java.util.ArrayList;
import java.util.Arrays;

class Upgrades {
    //Private vars
    private GameDataHandler gameDataHandler;
    private ArrayList<Effect> upgrade = new ArrayList<>(Arrays.asList(
            new Effect(1, "Add on to Health", (gdh)->gdh.GetPlayerBoard().HealHealthBy(1)),
            new Effect(1, "Repair one Hull", (gdh)->gdh.GetShipBoard().HealHullBy(1)),
            new Effect(2, "Build one Organic Detonator", (gdh, val)->{
                    Room room = gdh.GetShipBoard().GetRooms().get(val);
                    gdh.GetPlayerBoard().DecrementOrganicDetonatorCounter();
                    room.IncrementOrganicDetonatorCounter();
            })
            /*new Effect(4, "Add one to Movement"),
            new Effect(5, "Build one particle Desperser"),
            new Effect(5, "Gain one Sealed Room Token"),
            new Effect(6, "Gain one extra Attack Die"),
            new Effect(6, "Add 1 to the result of an Attack Dice")*/));

    //Package-protected constructor
    Upgrades(GameDataHandler gameDataHandler){
        this.gameDataHandler = gameDataHandler;
    }

    //Package-protected functions
    /*void ExecuteEffectAt(int pos) throws IndexOutOfBoundsException {
        switch (upgrade.get(pos).GetEffectType()){
            case SHIPBOARD: upgrade.get(pos).ExecuteEffect(shipBoard); break;
            case PLAYERBOARD: upgrade.get(pos).ExecuteEffect(playerBoard); break;
            case BOTH: upgrade.get(pos).ExecuteEffect(shipBoard, playerBoard); break;
        }
    }*/
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
    ArrayList<String> GetUpgradesDesciption(){
        ArrayList<String> upgradesDesciption = new ArrayList<>();
        for(Effect e : upgrade)
            upgradesDesciption.add(e.toString());
        return upgradesDesciption;
    }
    boolean UpgradeNeedsAditionalInputAt(int pos){
        return upgrade.get(pos).NeedsAditionalInput();
    }
}
