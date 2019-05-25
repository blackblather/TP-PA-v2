package gamelogic.states.gameSetup;

import gamelogic.data.Constants;
import gamelogic.data.GameDataHandler;

import java.util.ArrayList;

public class ChooseCrewMembers extends GameSetupStateAdapter {
    ChooseCrewMembers(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState SelectCrewMemberIn(int pos) {
        try {
            gameDataHandler.AddCrewMember(pos);
            //Só passa para o próximo estado quando o utilizador tiver escolhido 2 crew members válidos
            if(gameDataHandler.GetTotalChosenCrewMembers() == Constants.MAX_CHOSEN_CREWMEMBERS)
                return new SetCrewMemberShipLocation(gameDataHandler);              //Já escolheu 2
            else
                return this;                                                            //Ainda não escolheu 2
        } catch (Exception ex) {
            //TODO: Fazer catch das excepções especificas para mostrar mensagens de erro
            return this;
        }
    }

    @Override
    public IGameSetupState SelectCrewMembers(ArrayList<Integer> pos){
        try {
            if(pos.size() == Constants.MAX_CHOSEN_CREWMEMBERS) {
                for (Integer i : pos)
                    gameDataHandler.AddCrewMember(i);
                return new SetCrewMemberShipLocation(gameDataHandler);
            }
            else{
                gameDataHandler.SetErrorMessage("Please select 2 crew members");
                return this;
            }

        } catch (Exception ex) {
            gameDataHandler.SetErrorMessage("Invalid crew member selected");
            return this;
        }
    }
}
