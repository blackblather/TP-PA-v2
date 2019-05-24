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
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    private FlowPane GetVerticalCenterFlowPane(){
        FlowPane centerFlowPane = new FlowPane(Orientation.VERTICAL);
        centerFlowPane.setVgap(5);
        centerFlowPane.setAlignment(Pos.CENTER);            //overall alignment
        centerFlowPane.setColumnHalignment(HPos.CENTER);    //align buttons on the center of vertical column
        return centerFlowPane;
    }
    private void CheckCard(ArrayList<ImageView> ivArrChecked, ArrayList<Integer> inputArray, Integer deckIndex, ImageView cardImageView, Group group){
        for (ImageView iv : ivArrChecked) {
            if(!iv.isVisible()){
                inputArray.add(deckIndex);      //Adds input to inputArray
                iv.setX(cardImageView.getX() + (cardImageView.getImage().getWidth() / 2) - iv.getImage().getWidth() / 2);   //Set X
                iv.setY(cardImageView.getY() + (cardImageView.getImage().getHeight() / 2) - iv.getImage().getHeight() / 2); //Set Y
                iv.setVisible(true);            //Makes ivCheck visible
                group.getChildren().add(iv);    //Adds ivCheck to group
                break;                          //break out of the loop
            }
        }
    }
    private boolean CardIsChecked(Group group){
        return group.getChildren().size() == 2;
    }
    private void UncheckCard(ArrayList<ImageView> ivArrChecked, ArrayList<Integer> inputArray, Integer deckIndex, Group group){
        for (ImageView iv : ivArrChecked) {
            if(group.getChildren().get(1) == iv){                   //NOTE: "checked image view" is always at the index 1
                inputArray.removeAll(Arrays.asList(deckIndex));     //Remove from inputArray
                group.getChildren().remove(iv);                     //Remove ivCheck from group
                iv.setVisible(false);                               //Make ivCheck invisible again
                break;                                              //break out of the loop
            }
        }
    }
    private Group AddCard(ArrayList<ImageView> ivArrChecked, ArrayList<Integer> inputArray, Integer deckIndex, Image image){
        Group group = new Group();
        ImageView ivCard = new ImageView(image);
        ivCard.setCursor(Cursor.HAND);
        ivCard.addEventFilter(MouseEvent.MOUSE_CLICKED, (e -> {
            if(!CardIsChecked(group))
                CheckCard(ivArrChecked, inputArray, deckIndex, ivCard, group);
            else
                UncheckCard(ivArrChecked, inputArray, deckIndex, group);
        }));
        group.getChildren().add(ivCard);
        return group;
    }

    //User interfaces for each state
    private Scene GetInitialMenuScene(){
        //Empty GridPane layout
        GridPane layout = GetGrid(25, 50, 25, 25, 50, 25);

        //Create centerFlowPane
        FlowPane centerFlowPane = GetVerticalCenterFlowPane();

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
        FlowPane centerFlowPane = GetVerticalCenterFlowPane();

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
    private Scene GetChooseCrewMembers(){
        //
        ArrayList<Integer> inputArray = new ArrayList<>();

        //Create centerFlowPane
        FlowPane centerFlowPane = GetVerticalCenterFlowPane();

        //Create label
        Label title = new Label("Choose 2 crew members");
        title.setStyle("-fx-font-weight: bold");

        //Add label to centerFlowPane
        centerFlowPane.getChildren().add(title);

        //Create "check" image view array (maybe there's better ways of doing this "checked image" thing)
        ArrayList<ImageView> ivArrChecked = new ArrayList<>();
        for(int i = 0; i < Constants.MAX_SELECTED_CREWMEMBERS; i++){
            ivArrChecked.add(new ImageView(new Image("file:cards/checked.png")));
            ivArrChecked.get(i).setCursor(Cursor.HAND);
            ivArrChecked.get(i).setVisible(false);
        }

        //Create crew members
        Group gCaptain = AddCard(ivArrChecked, inputArray, 0, new Image("file:cards/Captain.png"));
        Group gTransporterChief = AddCard(ivArrChecked, inputArray, 1, new Image("file:cards/TransporterChief.png"));

        //Create resources for "row 1" of cards
        Group gRow1 = new Group();
        HBox row1 = new HBox(5);
        row1.getChildren().addAll(gCaptain, gTransporterChief);
        gRow1.getChildren().add(row1);

        //Add rows of cards to centerFlowPane
        centerFlowPane.getChildren().addAll(gRow1);

        //Button "Next"
        Button btnNext = new Button("Next");
        btnNext.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnNext.setCursor(Cursor.HAND);
        btnNext.setOnAction(e -> {
            //logic.SelectCustomJourney(txtCustomJourney.getText().split(" "));
        });

        //Add button "Next" to centerFlowPane
        centerFlowPane.getChildren().add(btnNext);

        return new Scene(centerFlowPane, 800,400);
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
                    stage.setScene(GetChooseCrewMembers());
                else if (logic.GetGameSetupState() instanceof SetCrewMemberShipLocation) {

                } else if (logic.GetGameSetupState() instanceof StartGame) {

                }
                this.stage.show();
            } else
                Platform.exit();
        }
    }
}