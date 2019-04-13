package ui.text;

import gamelogic.Logic;
import gamelogic.data.CrewMember;
import gamelogic.states.gameSetup.*;

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
            //a func. "nextInt()" não consome o \n do final, esta chamada é só para limpar o buffer de entrada.

            s.nextLine();
        }while(opt < optMin || opt > optMax);
        return opt;
    }
    private void PrintDeck(){
        int i = 1;
        System.out.println("Available Crew Members:");
        for (CrewMember c : logic.GetEncapsulatedGameData().GetDeck().GetCards()) {
            if(c.IsAvailable())
                System.out.println("Crew Member " + i + ":\n" + c.toString()+"\n.........................");
            i++;
        }
    }
    private void HorizontalLine() {
        System.out.println("--------------------------------------------------");
    }

    //Functions that read user-input
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
        int ChosenCrewMembersPos = logic.GetEncapsulatedGameData().GetTotalChosenCrewMembers() + 1;
        String FirstOrSecond = ChosenCrewMembersPos + (ChosenCrewMembersPos == 1?"st":"nd");

        PrintDeck();
        int opt = DisplayMenu("Choose the " + FirstOrSecond + " crew member:", 1, logic.GetEncapsulatedGameData().GetTotalDeckCards());
        logic.SelectCrewMembers(opt);
    }
    private void WaitToSetCrewMemberShipLocation(){
        HorizontalLine();
        int totalCrewMembersInRooms = logic.GetEncapsulatedGameData().GetTotalCrewMembersInRooms();
        String chosenCrewMember = logic.GetEncapsulatedGameData().GetChosenCrewMemberToStringAt(totalCrewMembersInRooms);
        int opt = DisplayMenu("Select room (1 - 12) to spawn crew member #" + (totalCrewMembersInRooms+1) + ":\n" + chosenCrewMember,1,12);
        //Posso usar "totalCrewMembersInRooms" para referenciar o crewMember a seleccionar
        logic.SetCrewMemberShipLocation(opt, totalCrewMembersInRooms);
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
        }
        System.out.println("Exiting Destination Earth...");
    }
}
