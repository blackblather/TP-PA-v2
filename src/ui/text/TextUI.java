package ui.text;

import gamelogic.Logic;
import gamelogic.data.CrewMember;
import gamelogic.states.gameSetup.ChooseJourney;
import gamelogic.states.gameSetup.Exit;
import gamelogic.states.gameSetup.NewGame;
import gamelogic.states.gameSetup.SelectCrewMembers;

import java.util.Scanner;

public class TextUI {
    //Private vars
    private Logic logic = new Logic();
    private Scanner s = new Scanner(System.in);
    private boolean printedDeck = false;
    //Helper functions
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

            System.out.println("Crew Member " + i + ":\n  Name: " + c.GetName() + "\n  Movement: " + c.GetMovement() + "\n  Attack: " + c.GetAttack() + "\n");
            i++;
        }
        printedDeck = true;
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
        if(!printedDeck)
            PrintDeck();

        //Used to print "1st"  or "2nd"
        int ChosenCrewMembersPos = logic.GetEncapsulatedGameData().GetTotalChosenCrewMembers() + 1;
        String FirstOrSecond = ChosenCrewMembersPos + (ChosenCrewMembersPos == 1?"st":"nd");

        int totalCards = logic.GetEncapsulatedGameData().GetDeck().GetCards().size();

        int opt = DisplayMenu("Choose the " + FirstOrSecond + " crew member:", 1, totalCards);
        logic.SelectCrewMembers(opt);
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
        }
        System.out.println("Exiting Destination Earth...");
    }
}
