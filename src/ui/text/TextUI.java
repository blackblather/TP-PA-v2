package ui.text;

import gamelogic.Logic;
import gamelogic.states.gameSetup.*;
import gamelogic.states.game.*;

import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    //Private vars
    private Logic logic = new Logic();
    private Scanner s = new Scanner(System.in);

    //Private helper functions
    private int DisplayMenu(String text, int optMin, int optMax){
        System.out.println(text);
        int opt;
        do{
            System.out.print("Option: ");
            opt = s.nextInt();
            //a func. "nextInt()" não consome o '\n' do final, esta chamada é só para limpar o buffer de entrada.
            s.nextLine();
        }while(opt < optMin || opt > optMax);
        return opt;
    }
    private void ListDeck(){
        ArrayList<String> availableCrewMembers = logic.GetGameDataHandler().GetListDeckAvailableCrewMembers();
        System.out.println("Available Crew Members:");
        for(String s : availableCrewMembers)
            System.out.println(s);
    }
    private String ListUpgrades(){
        ArrayList<String> upgradesDesciption = logic.GetGameDataHandler().GetUpgradesDesciption();
        int i = 1;
        StringBuilder retVal = new StringBuilder();
        for(String s : upgradesDesciption)
            retVal.append("Upgrade ").append(i++).append(":\n").append(s).append("\n.........................\n");
        return retVal.toString();
    }
    private String ListActions(){
        ArrayList<String> actionsDesciption = logic.GetGameDataHandler().GetActionsDesciption();
        int i = 1;
        StringBuilder retVal = new StringBuilder();
        for(String s : actionsDesciption)
            retVal.append("Action ").append(i++).append(":\n").append(s).append("\n.........................\n");
        return retVal.toString();
    }

    private void HorizontalLine() {
        System.out.println("--------------------------------------------------");
    }

    //GameSetup
    //Functions that read user-input -> GameSetup
    private void WaitInitialMenuInput(){
        int opt = DisplayMenu("Initial menu:\n 1) - New Game;\n 2) - Exit;", 1, 2);
        if(opt == 1)
            logic.NewGame();
        else
            logic.ExitGame();
    }
    private void WaitJourneyChoice(){
        HorizontalLine();
        int opt = DisplayMenu("Chose a journey:\n 1) - Default Journey;\n 2) - Custom Journey;", 1, 2);
        if(opt == 1)
            logic.ChooseJourney();
        else {
            System.out.println("Available elements are 'nA', 'nA*' and 'R'\nEnter 13 custom journey elements separated by a space: ");
            String customJourney = s.nextLine();
            logic.ChooseJourney(customJourney.split(" "));
        }
    }
    private void WaitToSelectCrewMembers(){
        HorizontalLine();
        //Used to print "1st"  or "2nd"
        int ChosenCrewMembersPos = logic.GetGameDataHandler().GetTotalChosenCrewMembers() + 1;
        String FirstOrSecond = ChosenCrewMembersPos + (ChosenCrewMembersPos == 1?"st":"nd");

        ListDeck();
        int opt = DisplayMenu("Choose the " + FirstOrSecond + " crew member:", 1, logic.GetGameDataHandler().GetTotalDeckCards());
        logic.SelectCrewMembers(opt);
    }
    private void WaitToSetCrewMemberShipLocation(){
        HorizontalLine();
        int totalCrewMembersInRooms = logic.GetGameDataHandler().GetTotalCrewMembersInRooms();
        String chosenCrewMember = logic.GetGameDataHandler().GetChosenCrewMemberToStringAt(totalCrewMembersInRooms);
        int opt = DisplayMenu("Select room (1 - "+logic.GetGameDataHandler().GetTotalRooms()+") to spawn crew member #" + (totalCrewMembersInRooms+1) + ":\n" + chosenCrewMember,1,logic.GetGameDataHandler().GetTotalRooms());
        //"totalCrewMembersInRooms" varia entre [0 - "nrMaxDeCrewMembersNoPlayerBoard - 1"], e aumenta à medida que se vai posicionando crew members no shipboard
        logic.SetCrewMemberShipLocation(opt, totalCrewMembersInRooms);
    }

    //Game
    //Functions that read user-input -> Game
    private void PrintJourneyPhaseInfos(){
        HorizontalLine();
        System.out.println("Journey Phase:");
        logic.JourneyPhase();
        System.out.println("Current journey part: " + logic.GetGameDataHandler().GetCurrentJourneyPart());
    }
    private void PrintScanningPhaseInfos(){
        HorizontalLine();
        System.out.println("Scanning Phase:\nScanning journey part...");
        logic.ScanningPhase();
    }
    private void PrintSpawningPhaseInfos(){
        HorizontalLine();
        System.out.println("Spawning Phase:\nSpawning Aliens");
        logic.SpawnAliensPhase();
    }
    private void WaitToChooseUpgrades() {
        HorizontalLine();
        int opt = DisplayMenu("Resting Phase:\nAvailable Inspiration Points: " + logic.GetGameDataHandler().GetInsirationPoints() + "\n.........................\n" + ListUpgrades() + "0 -> Skip\n.........................", 0, logic.GetGameDataHandler().GetTotalUpgrades());
        if (opt == 0)
            logic.RestPhase();
        else {
            if (logic.GetGameDataHandler().UpgradeNeedsAditionalInput(opt)) {
                ArrayList<String> affectedElements = logic.GetGameDataHandler().GetUpgradeAffetedElementsAt(opt);
                int[] additionalInputs = new int[affectedElements.size()];
                for (int i = 0; i < affectedElements.size(); i++) {
                    System.out.print("Choose the " + affectedElements.get(i) + ":\nOption: ");
                    additionalInputs[i] = s.nextInt();
                    s.nextLine(); //a func. "nextInt()" não consome o '\n' do final, esta chamada é só para limpar o buffer de entrada.
                }
                logic.RestPhase(opt, additionalInputs);
            } else
                logic.RestPhase(opt);
        }
    }
    private void WaitToChooseActions() {
        HorizontalLine();
        int opt = DisplayMenu("Crew Phase:\nAvailable Action Points: " + logic.GetGameDataHandler().GetActionPoints() + "\n.........................\n" + ListActions() + "0 -> Skip\n.........................", 0, logic.GetGameDataHandler().GetTotalActions());
        if (opt == 0)
            logic.CrewPhase();
        else {
            if (logic.GetGameDataHandler().ActionNeedsAditionalInput(opt)) {
                ArrayList<String> affectedElements = logic.GetGameDataHandler().GetActionAffetedElementsAt(opt);
                int[] additionalInputs = new int[affectedElements.size()];
                for (int i = 0; i < affectedElements.size(); i++) {
                    System.out.print("Choose the " + affectedElements.get(i) + ":\nOption: ");
                    additionalInputs[i] = s.nextInt();
                    s.nextLine(); //a func. "nextInt()" não consome o '\n' do final, esta chamada é só para limpar o buffer de entrada.
                }
                logic.CrewPhase(opt, additionalInputs);
            } else
                logic.CrewPhase(opt);
        }
    }
    private void GameLoop(){
        logic.StartGame();
        while (!(logic.GetGameState() instanceof GameOver) && !(logic.GetGameState() instanceof Win)){
            if(logic.GetGameState() instanceof JourneyPhase)
                PrintJourneyPhaseInfos();
            else if(logic.GetGameState() instanceof ScanningPhase)
                PrintScanningPhaseInfos();
            else if(logic.GetGameState() instanceof SpawnAliensPhase)
                PrintSpawningPhaseInfos();
            else if(logic.GetGameState() instanceof RestPhase){
                WaitToChooseUpgrades();
            } else if(logic.GetGameState() instanceof CrewPhase){
                WaitToChooseActions();
            } else if(logic.GetGameState() instanceof AlienPhase){

            }
        }
        if(logic.GetGameState() instanceof GameOver)
            System.out.println("Game over...");
        else if(logic.GetGameState() instanceof Win)
            System.out.println("Congratulations, you've reached Earth!");
        logic.EndGame();
    }

    //Public function to call in the User Interfaces
    public void Start(){
        while (!(logic.GetGameSetupState() instanceof Exit)){
            if(logic.GetGameSetupState() instanceof NewGame)
                WaitInitialMenuInput();
            else if(logic.GetGameSetupState() instanceof ChooseJourney)
                WaitJourneyChoice();
            else if(logic.GetGameSetupState() instanceof SelectCrewMembers)
                WaitToSelectCrewMembers();
            else if(logic.GetGameSetupState() instanceof SetCrewMemberShipLocation)
                WaitToSetCrewMemberShipLocation();
            else if(logic.GetGameSetupState() instanceof StartGame)
                GameLoop();
        }
        System.out.println("Exiting Destination Earth...");
    }
}
