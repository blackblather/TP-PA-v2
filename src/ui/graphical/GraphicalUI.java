package ui.graphical;

import gamelogic.*;
import gamelogic.data.Constants;
import gamelogic.states.gameSetup.*;
import gamelogic.states.game.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class GraphicalUI extends Application implements Observer {
    private Logic logic;
    private Stage stage;

    //Constructor
    public GraphicalUI(){
        super();
        logic = new Logic(this);
    }

    //Helper functions
    private GridPane GetGrid(int col1Val, int col2Val, int col3Val, int row1Val, int row2Val, int row3Val){
        //Empty GridPane layout
        GridPane layout = new GridPane();

        //Set Columns
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(col1Val);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(col2Val);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(col3Val);
        layout.getColumnConstraints().addAll(col1,col2,col3);

        //Set Rows
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(row1Val);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(row2Val);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(row3Val);
        layout.getRowConstraints().addAll(row1,row2,row3);
        return layout;
    }
    private FlowPane GetFlowPane(Orientation orientation){
        FlowPane centerFlowPane = new FlowPane(orientation);
        centerFlowPane.setHgap(5);
        centerFlowPane.setVgap(5);
        centerFlowPane.setAlignment(Pos.CENTER);            //overall alignment
        if(orientation == Orientation.HORIZONTAL)
            centerFlowPane.setRowValignment(VPos.CENTER);
        else if(orientation == Orientation.VERTICAL)
            centerFlowPane.setColumnHalignment(HPos.CENTER);
        return centerFlowPane;
    }

    private void CheckCard(ArrayList<ImageView> ivArrChecked, ArrayList<Integer> inputArray, Integer deckIndex, ImageView cardImageView, Group group){
        //USED FOR: GetChoosePlayerBoardCrewMembersScene()
        for (ImageView iv : ivArrChecked) {
            if(!iv.isVisible()){
                inputArray.add(deckIndex+1);      //Adds input to inputArray
                iv.setX(cardImageView.getX() + (cardImageView.getImage().getWidth() / 2) - iv.getImage().getWidth() / 2);   //Set X
                iv.setY(cardImageView.getY() + (cardImageView.getImage().getHeight() / 2) - iv.getImage().getHeight() / 2); //Set Y
                iv.setVisible(true);            //Makes ivCheck visible
                group.getChildren().add(iv);    //Adds ivCheck to group
                break;                          //break out of the loop
            }
        }
    }
    private void CheckCard(ArrayList<StackPane> stackPanes, Integer index){
        //USED FOR: GetChooseCrewMemberShipLocationScene_OLD()
        //Removes all styles    (default style is not empty. Is 3px solid white)
        for (StackPane s : stackPanes)
            s.setStyle("-fx-border-width: 3px; -fx-border-style: solid; -fx-border-color: white;");

        //Add style to selected borderStackPane
        StackPane borderStackPane = stackPanes.get(index);
        ArrayList<Double> RGB = logic.GetGameDataHandler().GetChosenCrewMemberColorRGBAt(index);
        borderStackPane.setStyle("-fx-border-width: 3px; -fx-border-style: solid; -fx-border-color: rgb("+RGB.get(0)+","+RGB.get(1)+","+RGB.get(2)+");");
    }
    private boolean CardIsChecked(Group group){
        //USED FOR: GetChoosePlayerBoardCrewMembersScene()
        return group.getChildren().size() == 2;
    }
    private boolean CardIsChecked(StackPane stackPane){
        //USED FOR: GetChooseCrewMemberShipLocationScene_OLD()
        return !stackPane.getStyle().equals("-fx-border-width: 3px; -fx-border-style: solid; -fx-border-color: white;");
    }
    private void UncheckCard(ArrayList<ImageView> ivArrChecked, ArrayList<Integer> inputArray, Integer deckIndex, Group group){
        for (ImageView iv : ivArrChecked) {
            if(group.getChildren().get(1) == iv){                   //NOTE: "checked image view" is always at the index 1
                inputArray.removeAll(Arrays.asList(deckIndex+1));     //Remove from inputArray
                group.getChildren().remove(iv);                     //Remove ivCheck from group
                iv.setVisible(false);                               //Make ivCheck invisible again
                break;                                              //break out of the loop
            }
        }
    }
    private Group NewCard(ArrayList<ImageView> ivArrChecked, Button btnNext, ArrayList<Integer> inputArray, Integer deckIndex){
        //USED FOR: GetChoosePlayerBoardCrewMembersScene()
        Group group = new Group();
        ImageView ivCard = new ImageView(new Image(logic.GetGameDataHandler().GetDeckCrewMemberImageUrlAt(deckIndex)));
        ivCard.setCursor(Cursor.HAND);
        ivCard.addEventFilter(MouseEvent.MOUSE_CLICKED, (e -> {
            if(!CardIsChecked(group)) {
                CheckCard(ivArrChecked, inputArray, deckIndex, ivCard, group);
                if(inputArray.size() == 2)
                    btnNext.setDisable(false);
            }
            else {
                UncheckCard(ivArrChecked, inputArray, deckIndex, group);
                btnNext.setDisable(true);
            }
        }));
        group.getChildren().add(ivCard);
        return group;
    }
    private StackPane NewCard(Integer index){
        //USED FOR: GetChooseCrewMemberShipLocationScene_OLD()
        //Create borderStackPane
        StackPane borderStackPane = new StackPane();

        //Create image view of crew member image
        ImageView ivCard = new ImageView(new Image(logic.GetGameDataHandler().GetChosenCrewMemberImageUrlAt(index)));
        ivCard.setCursor(Cursor.HAND);

        //Add image view to stack pane
        borderStackPane.getChildren().add(ivCard);

        //Set default border style(default style is not empty. Is 3px solid white)
        borderStackPane.setStyle("-fx-border-width: 3px; -fx-border-style: solid; -fx-border-color: white;");

        return borderStackPane;
    }
    private void SetIVCardEvent(ArrayList<StackPane> borderStackPanes, Integer index, Button btnNext){
        StackPane borderStackPane = borderStackPanes.get(index);
        ImageView ivCard = (ImageView) borderStackPane.getChildren().get(0);  //Downcast from Node to ImageView
        ivCard.addEventFilter(MouseEvent.MOUSE_CLICKED, (e -> {
            if(!CardIsChecked(borderStackPane)) {
                CheckCard(borderStackPanes, index);
                btnNext.setDisable(false);
            }
        }));
    }

    //User interfaces for each state (Game Setup)
    private Scene GetInitialMenuScene(){
        //Empty GridPane layout
        GridPane layout = GetGrid(25, 50, 25, 25, 50, 25);

        //Create centerFlowPane
        FlowPane centerFlowPane = GetFlowPane(Orientation.VERTICAL);

        //Position centerFlowPane (col = 1; row = 1)
        GridPane.setConstraints(centerFlowPane, 1, 1);

        //Add centerFlowPane to layout
        layout.getChildren().addAll(centerFlowPane);

        //Create 2 labels
        Label title = new Label("Destination Earth");
        title.setStyle("-fx-font-weight: bold");
        Label lblMenu = new Label("Menu");

        //Button "New Game"
        Button btwNewGame = new Button("New Game");
        btwNewGame.setStyle("-fx-background-color: #337ab7; -fx-text-fill: white");
        btwNewGame.setCursor(Cursor.HAND);
        btwNewGame.setOnAction(e -> logic.NewGame());

        //Button "Exit"
        Button btnExit = new Button("Exit");
        btnExit.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white");
        btnExit.setCursor(Cursor.HAND);
        btnExit.setOnAction(e -> logic.ExitGame());

        //Add buttons to centerFlowPane
        centerFlowPane.getChildren().addAll(title, lblMenu, btwNewGame, btnExit);

        //Returns scene with layout in it
        return new Scene(layout, 270,250);
    }
    private Scene GetChooseJourneyScene(){
        //Create centerFlowPane
        FlowPane centerFlowPane = GetFlowPane(Orientation.VERTICAL);

        //Create label
        Label title = new Label("Choose a journey");
        title.setStyle("-fx-font-weight: bold");

        //Create textfield for custom journey
        TextField txtCustomJourney = new TextField();
        txtCustomJourney.setDisable(true);

        //Create 2 radio buttons
        ToggleGroup group = new ToggleGroup();
        RadioButton btnDefault = new RadioButton("Default journey: S 2A 3A 4A 5A* R 4A 5A 6A* R 6A 7A* R 8A E");
        btnDefault.setToggleGroup(group);
        btnDefault.setSelected(true);
        btnDefault.setOnAction(e -> txtCustomJourney.setDisable(true));
        RadioButton btnCustom = new RadioButton("Custom journey:");
        btnCustom.setToggleGroup(group);
        btnCustom.setOnAction(e -> txtCustomJourney.setDisable(false));

        //Button "Next"
        Button btnChooseJourney = new Button("Next");
        btnChooseJourney.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnChooseJourney.setCursor(Cursor.HAND);
        btnChooseJourney.setOnAction(e -> {
            if(btnDefault.isSelected())
                logic.SelectDefaultJourney();
            else
                logic.SelectCustomJourney(txtCustomJourney.getText().split(" "));
        });

        //Add buttons to centerFlowPane
        centerFlowPane.getChildren().addAll(title,
                                            btnDefault,
                                            btnCustom,
                                            txtCustomJourney,
                                            btnChooseJourney);

        //Returns scene with layout in it
        return new Scene(centerFlowPane, 400,150);
    }
    private Scene GetChoosePlayerBoardCrewMembersScene(){
        //Create inputArray
        ArrayList<Integer> inputArray = new ArrayList<>();

        //Create mainFlowPane
        FlowPane mainFlowPane = GetFlowPane(Orientation.VERTICAL);

        //Create label
        Label title = new Label("Choose 2 crew members");
        title.setStyle("-fx-font-weight: bold");

        //Add label to centerFlowPane
        mainFlowPane.getChildren().add(title);

        //Create centerFlowPane
        FlowPane scrollFlowPane = new FlowPane();
        scrollFlowPane.setAlignment(Pos.TOP_CENTER);
        scrollFlowPane.prefWidth(800);

        //Create "check" image view array (maybe there's better ways of doing this "checked image" thing)
        ArrayList<ImageView> ivArrChecked = new ArrayList<>();
        for(int i = 0; i < Constants.MAX_CHOSEN_CREWMEMBERS; i++){
            ivArrChecked.add(new ImageView(new Image("file:cards/checked.png")));
            ivArrChecked.get(i).setCursor(Cursor.HAND);
            ivArrChecked.get(i).setVisible(false);
        }

        //Button "Next"
        Button btnNext = new Button("Next");
        btnNext.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnNext.setCursor(Cursor.HAND);
        btnNext.setOnAction(e -> {
            logic.SelectCrewMembers(inputArray);
        });
        btnNext.setDisable(true);

        //Create array of groups of imageviews of crew members
        ArrayList<Group> gCrewMember = new ArrayList<>();
        for(int i = 0; i < logic.GetGameDataHandler().GetTotalDeckCards(); i++) {
            //Add new card at gCrewMember[i]
            gCrewMember.add(i,NewCard(ivArrChecked, btnNext, inputArray, i));
            //Add gCrewMembers[i] to scrollFlowPane
            scrollFlowPane.getChildren().add(gCrewMember.get(i));
        }

        //Create scroll pane with  scrollFlowPane
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(800, 700);
        s1.setFitToWidth(true);
        s1.setContent(scrollFlowPane);

        //Add s1 to mainFlowPane
        mainFlowPane.getChildren().add(s1);

        //Add button "Next" to centerFlowPane
        mainFlowPane.getChildren().add(btnNext);

        //Returns scene with layout in it
        return new Scene(mainFlowPane, 800,800);
    }
    private Scene GetChooseCrewMemberShipLocationScene_OLD(){
       /* //Create inputArray
        ArrayList<Integer> inputArray = new ArrayList<>();

        //Create flowPane
        FlowPane flowPane = GetFlowPane(Orientation.HORIZONTAL);
        flowPane.setStyle("-fx-background-color: white;");

        //Add map image to the left
        Group gMap = new Group();
        ImageView ivMap = new ImageView(new Image("file:map.png"));
        //Salas
        // 1 -> (156,73)
        // 2 -> (186,312)
        // 3 -> (59,141)
        // 4 -> (252,160)
        // 5 -> (129,160)
        // 6 -> (158,431)
        // 7 -> (252,330)
        // 8 -> (186,183)
        // 9 -> (59,244)
        // 10 -> (129,312)
        // 11 -> (252,244)
        // 12 -> (59,330)

        ivMap.setCursor(Cursor.HAND);
        ivMap.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        Circle c1 = new Circle(20,156,73, Color.rgb(255,0,0,0.5));
        gMap.getChildren().add(ivMap);

        //Add map to flowPane
        flowPane.getChildren().add(gMap);

        //Create rightFlowPane
        FlowPane rightFlowPane = GetFlowPane(Orientation.VERTICAL);

        //Create label
        Label title = new Label("Select a crew member and choose his/her location");
        title.setStyle("-fx-font-weight: bold");

        //Add lablel to rightFlowPane
        rightFlowPane.getChildren().add(title);

        //Create crewMembersFlowPane
        FlowPane crewMembersFlowPane = GetFlowPane(Orientation.HORIZONTAL);
        crewMembersFlowPane.setPrefWrapLength(600);

        //Create an array of groups of image views of chosen crew members
        ArrayList<StackPane> borderStackPanes = new ArrayList<>();
        for(int i = 0; i < logic.GetGameDataHandler().GetTotalChosenCrewMembers(); i++){
            borderStackPanes.add(i, NewCard(i));
            crewMembersFlowPane.getChildren().add(borderStackPanes.get(i));
        }

        //Add events to each Image View
        //Events are set in a seperate function, because the whole array of stackPanes is required
        for (Integer index = 0; index < borderStackPanes.size(); index++)
            SetIVCardEvent(borderStackPanes, index);

        //Add crewMembersFlowPane to rightFlowPane (after label)
        rightFlowPane.getChildren().add(crewMembersFlowPane);

        //Button "Next"
        Button btnNext = new Button("Next");
        btnNext.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnNext.setCursor(Cursor.HAND);
        btnNext.setOnAction(e -> {

        });
        btnNext.setDisable(true);

        rightFlowPane.getChildren().add(btnNext);

        //Add rightFlowPane to flowPane (after map)
        flowPane.getChildren().add(rightFlowPane);

        //Returns scene with layout in it
        return new Scene(flowPane, 1000,550);*/
       return new Scene(new FlowPane());
    }
    private Scene GetChooseCrewMemberShipLocationScene(){
        //Create centerFlowPane
        FlowPane centerFlowPane = GetFlowPane(Orientation.VERTICAL);

        //Create label
        Label title = new Label("Choose the ship location");
        title.setStyle("-fx-font-weight: bold");

        ArrayList<Spinner> spnrRooms = new ArrayList<>();
        Label lblCM1 = new Label(logic.GetGameDataHandler().GetChosenCrewMemberNameAt(0) + ":");
        spnrRooms.add(new Spinner(1,12,1));
        Label lblCM2 = new Label(logic.GetGameDataHandler().GetChosenCrewMemberNameAt(1) + ":");
        spnrRooms.add(new Spinner(1,12,1));

        //Button "Next"
        Button btnNext = new Button("Next");
        btnNext.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnNext.setCursor(Cursor.HAND);
        btnNext.setOnAction(e -> {
            ArrayList<Integer> rooms = new ArrayList<>();
            rooms.add((Integer)spnrRooms.get(0).getValue());
            rooms.add((Integer)spnrRooms.get(1).getValue());
            logic.SetCrewMembersShipLocation(rooms);
        });

        //Add buttons to centerFlowPane
        centerFlowPane.getChildren().addAll(title,
                                            lblCM1,
                                            spnrRooms.get(0),
                                            lblCM2,
                                            spnrRooms.get(1),
                                            btnNext);

        //Returns scene with layout in it
        return new Scene(centerFlowPane, 240,180);
    }

    //User interfaces for each state (Game Setup)
    private Scene GetChooseUpgradeScene(){
        return new Scene(new FlowPane());
    }
    private Scene GetChooseActionScene(){
        //Create flowPane
        FlowPane flowPane = GetFlowPane(Orientation.VERTICAL);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setColumnHalignment(HPos.LEFT);

        //Create title label
        Label title = new Label("Choose an action to execute");
        title.setStyle("-fx-font-weight: bold");

        //Add title to flowPane
        flowPane.getChildren().add(title);

        //Create info label
        Label info = new Label("Available action points: " + logic.GetGameDataHandler().GetActionPoints());

        //Add info to flowPane
        flowPane.getChildren().add(info);

        //Get action descriptions
        ArrayList<String> actionDescriptions = logic.GetGameDataHandler().GetDesciptionArray("Action");

        //Create toggle group
        ToggleGroup toggleGroup = new ToggleGroup();

        //Create array of radio buttons
        ArrayList<RadioButton> rbActions = new ArrayList<>();

        //Create and add radio button to flow pane
        for (int i = 0; i <actionDescriptions.size(); i++) {
            //Create new radio button
            rbActions.add(new RadioButton(actionDescriptions.get(i)));
            rbActions.get(i).setToggleGroup(toggleGroup);

            //Add radio button to flowPane
            flowPane.getChildren().add(rbActions.get(i));
        }

        //Create "skip" radio button
        rbActions.add(new RadioButton("Skip"));
        rbActions.get(actionDescriptions.size()).setToggleGroup(toggleGroup);

        //Add radio button to flowPane
        flowPane.getChildren().add(rbActions.get(actionDescriptions.size()));

        //Button "Next"
        Button btnNext = new Button("Next");
        btnNext.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnNext.setCursor(Cursor.HAND);
        btnNext.setOnAction(e -> {
            if(rbActions.get(actionDescriptions.size()).isSelected())
                logic.Skip();
            else{
                int selected = (rbActions.indexOf((RadioButton)toggleGroup.getSelectedToggle())) + 1;
                logic.EvaluateChosenAction(selected);
            }
        });

        //Add btnNext to flowPane
        flowPane.getChildren().add(btnNext);

        //Returns scene with layout in it
        return new Scene(flowPane, 300, 350);
    }
    private Scene GetChooseEffectAdditionalInputSceneFor(String type){
        //Create vars for Scene size
        int width = 0, height = 0;

        //Create rightFlowPane
        FlowPane flowPane = GetFlowPane(Orientation.VERTICAL);

        //Create title label
        Label title = new Label("Choose a " + type);
        title.setStyle("-fx-font-weight: bold");

        //Add lablel to rightFlowPane
        flowPane.getChildren().add(title);

        //Button "Next"
        Button btnNext = new Button("Next");
        btnNext.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnNext.setCursor(Cursor.HAND);

        switch (type){
            case "crew member":{
                //Disable button "Next" initially
                btnNext.setDisable(true);

                //Create crewMembersFlowPane
                FlowPane horizontalFlowPane = GetFlowPane(Orientation.HORIZONTAL);
                horizontalFlowPane.setPrefWrapLength(600);

                //Create an array of groups of image views of chosen crew members
                ArrayList<StackPane> borderStackPanes = new ArrayList<>();
                for(int i = 0; i < logic.GetGameDataHandler().GetTotalChosenCrewMembers(); i++){
                    borderStackPanes.add(i, NewCard(i));
                    horizontalFlowPane.getChildren().add(borderStackPanes.get(i));
                }

                //Add events to each Image View
                //Events are set in a seperate function, because the whole array of stackPanes is required
                for (Integer index = 0; index < borderStackPanes.size(); index++)
                    SetIVCardEvent(borderStackPanes, index, btnNext);

                //Set button "Next" event
                btnNext.setOnAction(e -> {
                    for(int i = 0; i < logic.GetGameDataHandler().GetTotalChosenCrewMembers(); i++)
                        if(CardIsChecked(borderStackPanes.get(i))) {
                            logic.EvaluateChosenCrewMember(i + 1);
                            break;
                        }
                });

                //Add crewMembersFlowPane to flowPane
                flowPane.getChildren().add(horizontalFlowPane);

                //Set Scene size
                width = 700;
                height = 400;
            } break;
            case "room":{
                //Create spinner to select room
                Spinner spnrRoom = new Spinner(1,12,1);

                //Set button "Next" event
                btnNext.setOnAction(e -> {
                    logic.EvaluateChosenRoom((Integer)spnrRoom.getValue());
                });

                //Add spnrRoom to flowPane
                flowPane.getChildren().add(spnrRoom);

                //Set Scene size
                width = 250;
                height = 150;
            } break;
            case "trap":{
                //Get action descriptions
                ArrayList<String> trapDescriptions = logic.GetGameDataHandler().GetDesciptionArray("Trap");

                //Create toggle group
                ToggleGroup toggleGroup = new ToggleGroup();

                //Create array of radio buttons
                ArrayList<RadioButton> rbTraps = new ArrayList<>();

                //Create and add radio button to flow pane
                for (int i = 0; i < trapDescriptions.size(); i++) {
                    //Create new radio button
                    rbTraps.add(new RadioButton(trapDescriptions.get(i)));
                    rbTraps.get(i).setToggleGroup(toggleGroup);

                    //Add radio button to flowPane
                    flowPane.getChildren().add(rbTraps.get(i));
                }

                //Set button "Next" event
                btnNext.setOnAction(e -> {
                    int selected = (rbTraps.indexOf((RadioButton)toggleGroup.getSelectedToggle())) + 1;
                    logic.EvaluateChosenTrap(selected);
                });

                //Set Scene size
                width = 250;
                height = 150;
            } break;
        }

        //Add button "Next" to flowPane
        flowPane.getChildren().add(btnNext);

        //Returns scene with layout in it
        return new Scene(flowPane, width,height);
    }

    @Override
    public void start(Stage stage) {
        //Add private reference to stage from params
        this.stage = stage;

        //Add scene to stage
        this.stage.setScene(GetInitialMenuScene());

        //Set stage's title and icon
        this.stage.setTitle("Destination Earth");
        this.stage.getIcons().add(new Image("file:earth.png"));

        logic.InitialMenu();
    }

    public void Run(){
        launch();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg != null) {
            if(arg instanceof String){   //There's some argument (maybe error info)
                Alert alert = new Alert(Alert.AlertType.ERROR, logic.GetGameDataHandler().GetErrorMessage(), ButtonType.OK);
                alert.showAndWait();
                logic.GetGameDataHandler().ResetErrorMessage();
            }
        } else {
            if (!(logic.GetGameSetupState() instanceof Exit)) {
                stage.close();
                //Game Setup State
                if (logic.GetGameSetupState() instanceof InitialMenu)
                    stage.setScene(GetInitialMenuScene());
                else if (logic.GetGameSetupState() instanceof ChooseJourney)
                    stage.setScene(GetChooseJourneyScene());
                else if (logic.GetGameSetupState() instanceof ChooseCrewMembers)
                    stage.setScene(GetChoosePlayerBoardCrewMembersScene());
                else if (logic.GetGameSetupState() instanceof SetCrewMemberShipLocation)
                    stage.setScene(GetChooseCrewMemberShipLocationScene());
                else if (logic.GetGameSetupState() instanceof StartGame) {
                    //Game State
                    if(logic.GetGameState() == null)
                        logic.StartGame();
                    else if(logic.GetGameState() instanceof RoundPhase)
                        logic.EvaluateRound();
                    else if(logic.GetGameState() instanceof RestPhase)
                        stage.setScene(GetChooseUpgradeScene());    //TODO
                    else if(logic.GetGameState() instanceof CrewPhase)
                        stage.setScene(GetChooseActionScene());
                    else if(logic.GetGameState() instanceof SelectCrewMember)
                        stage.setScene(GetChooseEffectAdditionalInputSceneFor("crew member"));
                    else if(logic.GetGameState() instanceof SelectTrap){
                        stage.setScene(GetChooseEffectAdditionalInputSceneFor("trap"));
                    } else if(logic.GetGameState() instanceof SelectRoom){
                        stage.setScene(GetChooseEffectAdditionalInputSceneFor("room"));
                    } else if(logic.GetGameState() instanceof ExecuteEffect)
                        logic.EvaluateAndExecuteEffect();
                    else if(logic.GetGameState() instanceof AlienPhase){
                        //TODO
                    } else if(logic.GetGameState() instanceof GameOver || logic.GetGameState() instanceof Win){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, (logic.GetGameState() instanceof GameOver?"Game over": "Win"), ButtonType.OK);
                        alert.showAndWait();
                        logic.EndGame();
                    }
                }
                this.stage.show();
            } else
                Platform.exit();
        }
    }
}