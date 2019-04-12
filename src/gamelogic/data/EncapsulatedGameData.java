package gamelogic.data;

/*TODO: Maybe só inicializar o playerBoard e o shipBoard no inicio do jogo, para evitar andar a criar vars e a mudar os saus
        seus parametros depois de inicializadas. Por essa razão é que se costuma fazer override aos construtores default.*/
public class EncapsulatedGameData {
    private PlayerBoard playerBoard;
    private ShipBoard shipBoard;
    private Deck deck;

    public EncapsulatedGameData(){
        shipBoard = new ShipBoard();
        playerBoard = new PlayerBoard();
        deck = new Deck();
    }

    private  void LoadChosenCrewMemberSpecials(){
        //TODO: Alterar as infos no shipBoard e no playerBoard aqui (com base nas cartas escolhidas)
    }

    public void CreateCustomJourney(String[] customJourney) throws ArrayStoreException{
        shipBoard.ChangeJourney(customJourney);
    }
    public ShipBoard GetShipBoard(){
        return shipBoard;
    }
    public PlayerBoard GetPlayerBoard(){
        return playerBoard;
    }
    public void AddCrewMember(int pos) throws IndexOutOfBoundsException, IllegalAccessException {
        playerBoard.AddCrewMember(deck, pos);
    }
    public int GetTotalChosenCrewMembers(){
        return playerBoard.GetCrewMembers().size();
    }
    public Deck GetDeck()
    {
        return deck;
    }
}
