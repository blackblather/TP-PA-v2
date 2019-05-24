package gamelogic.states.gameSetup;

import gamelogic.data.Constants;
import gamelogic.data.GameDataHandler;

public class ChooseCrewMembers extends GameSetupStateAdapter {
    ChooseCrewMembers(GameDataHandler gameDataHandler) {
        super(gameDataHandler);
    }

    @Override
    public IGameSetupState SelectCrewMemberIn(int pos) {
        try {
            gameDataHandler.AddCrewMember(pos);
            //Só passa para o próximo estado quando o utilizador tiver escolhido 2 crew members válidos
            if(gameDataHandler.GetTotalChosenCrewMembers() == Constants.MAX_SELECTED_CREWMEMBERS)
                return new SetCrewMemberShipLocation(gameDataHandler);              //Já escolheu 2
            else
                return this;                                                            //Ainda não escolheu 2
        } catch (Exception ex) {
            //TODO: Fazer catch das excepções especificas para mostrar mensagens de erro
            return this;
        }
    }
}
