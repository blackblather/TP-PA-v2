package gamelogic.states.gameSetup;

import gamelogic.data.EncapsulatedGameData;

public class SelectCrewMembers extends GameSetupStateAdapter {
    SelectCrewMembers(EncapsulatedGameData encapsulatedGameData) {
        super(encapsulatedGameData);
    }

    @Override
    public IGameSetupState _SelectCrewMembers(int pos) {
        EncapsulatedGameData egd = GetEncapsulatedGameData();

        try {
            egd.AddCrewMember(pos);
            //Só passa para o próximo estado quando o utilizador tiver escolhido 2 crew members válidos
            if(egd.GetTotalChosenCrewMembers() == 2)
                return new SetCrewMemberShipLocation(GetEncapsulatedGameData());        //Já escolheu 2
            else
                return this;                                                            //Ainda não escolheu 2
        } catch (Exception ex) {
            //TODO: Fazer catch das excepções especificas para mostrar mensagens de erro
            return this;
        }
    }
}
