package ui.text;

import gamelogic.Logic;
import gamelogic.data.CrewMember;
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
    private void PrintDeck(){
        int i = 1;
        System.out.println("Available Crew Members:");
        for (CrewMember c : logic.GetGameDataHandler().GetDeck().GetCards()) {
            if(c.IsAvailable())
                System.out.println("Crew Member " + i + ":\n" + c.toString()+"\n.........................");
            i++;
        }
    }
    private String ListUpgrades(){
        ArrayList<String> upgradesDesciption = logic.GetGameDataHandler().GetUpgradesDesciption();
        int i = 1;
        StringBuilder retVal = new StringBuilder();
        for(String s : upgradesDesciption)
            retVal.append("Upgrade ").append(i++).append(":\n").append(s).append("\n.........................\n");
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

        PrintDeck();
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
    private void WaitToChooseUpgrades() {
        HorizontalLine();
        int opt = DisplayMenu("Available Inspiration Points: " + logic.GetGameDataHandler().GetInsirationPoints()+"\n.........................\n" + ListUpgrades()+"0 -> Skip\n.........................", 0, logic.GetGameDataHandler().GetTotalUpgrades());
        if(opt == 0)
            logic.RestPhase();
        else{
            if(logic.GetGameDataHandler().UpgradeNeedsAditionalInput(opt)) {
                int aditionalInput;
                System.out.print("Choose the " + logic.GetGameDataHandler().GetUpgradeAffetedElementAt(opt) + " to apply the upgrade to:\nOption: ");
                aditionalInput = s.nextInt();
                s.nextLine(); //a func. "nextInt()" não consome o '\n' do final, esta chamada é só para limpar o buffer de entrada.
                logic.RestPhase(opt, aditionalInput);
            }
            else
                logic.RestPhase(opt);
        }
    }
    private void GameLoop(){
        logic.StartGame();
        while (!(logic.GetGameState() instanceof GameOver) && !(logic.GetGameState() instanceof Win)){
            if(logic.GetGameState() instanceof JourneyPhase){
                //TODO: Add text
                logic.JourneyPhase();
            } else if(logic.GetGameState() instanceof ScanningPhase){
                //TODO: Add text
                logic.ScanningPhase();
            } else if(logic.GetGameState() instanceof SpawnAliensPhase){
                //TODO: Add text
                logic.SpawnAliensPhase();
            } else if(logic.GetGameState() instanceof RestPhase){
                WaitToChooseUpgrades();
            } else if(logic.GetGameState() instanceof CrewPhase){

            } else if(logic.GetGameState() instanceof AlienPhase){

            }
        }
        if(logic.GetGameState() instanceof GameOver)
            WaitInitialMenuInput();
        else if(logic.GetGameState() instanceof Win)
            WaitJourneyChoice();
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
