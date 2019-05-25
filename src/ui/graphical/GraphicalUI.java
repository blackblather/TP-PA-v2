package ui.graphical;

import gamelogic.*;
import gamelogic.data.Constants;
import gamelogic.states.gameSetup.*;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

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
        //USED FOR: GetChooseCrewMembersScene()
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
        //USED FOR: GetChooseCrewMemberShipLocationScene()
        //Removes all styles    (default style is not empty. Is 3px solid white)
        for (StackPane s : stackPanes)
            s.setStyle("-fx-border-width: 3px; -fx-border-style: solid; -fx-border-color: white;");

        //Add style to selected borderStackPane
        StackPane borderStackPane = stackPanes.get(index);
        ArrayList<Double> RGB = logic.GetGameDataHandler().GetChosenCrewMemberColorRGBAt(index);
        borderStackPane.setStyle("-fx-border-width: 3px; -fx-border-style: solid; -fx-border-color: rgb("+RGB.get(0)+","+RGB.get(1)+","+RGB.get(2)+");");
    }
    private boolean CardIsChecked(Group group){
        //USED FOR: GetChooseCrewMembersScene()
        return group.getChildren().size() == 2;
    }
    private boolean CardIsChecked(StackPane stackPane){
        //USED FOR: GetChooseCrewMemberShipLocationScene()
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
        //USED FOR: GetChooseCrewMembersScene()
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
        //USED FOR: GetChooseCrewMemberShipLocationScene()
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
    private void SetIVCardEvent(ArrayList<StackPane> borderStackPanes, Integer index){
        StackPane borderStackPane = borderStackPanes.get(index);
        ImageView ivCard = (ImageView) borderStackPane.getChildren().get(0);  //Downcast from Node to ImageView
        ivCard.addEventFilter(MouseEvent.MOUSE_CLICKED, (e -> {
            if(!CardIsChecked(borderStackPane))
                CheckCard(borderStackPanes, index);
        }));
    }

    //User interfaces for each state
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
    private Scene GetChooseCrewMembersScene(){
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
    private Scene GetChooseCrewMemberShipLocationScene(){
        //Create inputArray
        ArrayList<Integer> inputArray = new ArrayList<>();

        //Create flowPane
        FlowPane flowPane = GetFlowPane(Orientation.HORIZONTAL);
        flowPane.setStyle("-fx-background-color: white;");

        //Add map image to the left
        Group gMap = new Group();
        ImageView ivMap = new ImageView(new Image("file:map.png"));
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
        return new Scene(flowPane, 1000,550);
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
                if (logic.GetGameSetupState() instanceof InitialMenu)
                    stage.setScene(GetInitialMenuScene()); //Set scene to "Initial Menu"
                else if (logic.GetGameSetupState() instanceof ChooseJourney)
                    stage.setScene(GetChooseJourneyScene()); //Set scene to "Choose journey"
                else if (logic.GetGameSetupState() instanceof ChooseCrewMembers)
                    stage.setScene(GetChooseCrewMembersScene());
                else if (logic.GetGameSetupState() instanceof SetCrewMemberShipLocation)
                    stage.setScene(GetChooseCrewMemberShipLocationScene());
                else if (logic.GetGameSetupState() instanceof StartGame) {

                }
                this.stage.show();
            } else
                Platform.exit();
        }
    }
}