package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;


public class GuiDemo<toReturn> extends Application {
    /* Even if it is a GUI it is useful to have instance variables
    so that you can break the processing up into smaller methods that have
    one responsibility.
     */
    private MenuBar topBar;
    private FileChooser fileDialog;
    private Controller theController;
    private TextArea mainText;      //main text for description in centre screen
    private ChoiceBox doors;
    private ListView list;
    private BorderPane root;  //the root element of this GUI
    private BorderPane editChoicePane;
    private Popup descriptionPane;
    private Stage primaryStage;  //The stage that is passed in on initialization
    private Stage editChoiceStage;
    private ListButtonPane monsterEditPane;
    private ListButtonPane monsterAddRemovePane;
    private ListButtonPane treasureAddRemovePane;
    private ListButtonPane treasureAddPane;
    private ListButtonPane monsterEditDBPane;

    /*a call to start replaces a call to the constructor for a JavaFX GUI*/
    @Override
    public void start(Stage assignedStage) {
        /*Initializing instance variables */
        theController = new Controller(this);
        primaryStage = assignedStage;
        root = setUpRoot();
        createMonsterEditPane();
        createMonsterEditDBPane();
        createMonsterAddRemovePane();
        createTreasureAddRemovePane();
        createTreasureAddPane();
        createEditChoiceStage();

        /*Border Panes have  top, left, right, center and bottom sections */
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("/res/stylesheet.css");
        primaryStage.setTitle("Dungeon Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createMonsterEditPane() {
        monsterEditPane = new ListButtonPane("Add/Edit Monster", 600, 300);
        monsterEditPane.getLeftPanelLabel().setText("Monsters from Database");
        monsterEditPane.getRightPanelTopButton().setText("Confirm Add");
        monsterEditPane.getRightPanelBottomButton().setText("Edit Database");
        updateMonsterEditPaneList();
        setMonsterEditPaneButtonActions();
    }

    private void updateMonsterEditPaneList() {
        monsterEditPane.setList(theController.getMonsterListDatabase());

        if (mainText != null) {
            updateMainDescriptionText();
        }
    }

    private void setMonsterEditPaneButtonActions() {
        monsterEditPane.getRightPanelTopButton().setOnAction(event -> {
            if (monsterEditPane.listHasSelection()) {
                theController.addMonsterToSpace(monsterEditPane.getSelectedIndex(), getSelectedSpace());
                updateMonsterEditPaneList();
                updateMonsterAddRemovePaneList();
            }
        });

        monsterEditPane.getRightPanelBottomButton().setOnAction(event -> {
            monsterEditDBPane.getStage().show();
        });
    }



    private void createMonsterEditDBPane() {
        monsterEditDBPane = new ListButtonPane("Edit Database", 600, 300);
        monsterEditDBPane.getLeftPanelLabel().setText("Monsters from Database");
        monsterEditDBPane.getRightPanelTopButton().setText("Create New Monster");
        monsterEditDBPane.getRightPanelBottomButton().setText("Edit Selected");
        updateMonsterEditDBPaneList();
        setMonsterEditDBPaneButtonActions();
    }

    private void updateMonsterEditDBPaneList() {
        monsterEditDBPane.setList(theController.getMonsterListDatabase());

        if (mainText != null) {
            updateMainDescriptionText();
        }
    }

    private void setMonsterEditDBPaneButtonActions() {
        monsterEditDBPane.getRightPanelTopButton().setOnAction(event -> {
            if (monsterEditDBPane.listHasSelection()) {
                theController.addMonsterToSpace(monsterEditDBPane.getSelectedIndex(), getSelectedSpace());
                updateMonsterEditDBPaneList();
                updateMonsterEditPaneList();
                updateMonsterAddRemovePaneList();
            }
        });

        monsterEditDBPane.getRightPanelBottomButton().setOnAction(event -> {
            System.out.println("edit monster button");
        });
    }



    private void createMonsterAddRemovePane() {
        monsterAddRemovePane = new ListButtonPane("Add/Remove Monster", 600, 300);
        monsterAddRemovePane.getLeftPanelLabel().setText("Monsters in Space");
        monsterAddRemovePane.getRightPanelTopButton().setText("Add/Edit New Monster");
        monsterAddRemovePane.getRightPanelBottomButton().setText("Confirm Remove");
        updateMonsterAddRemovePaneList();
        setMonsterAddRemovePaneButtonActions();
    }

    private void updateMonsterAddRemovePaneList() {
        monsterAddRemovePane.setList(theController.getMonsterListInSpace(getSelectedSpace()));
        
        if (mainText != null) {
            updateMainDescriptionText();
        }
    }

    private void setMonsterAddRemovePaneButtonActions() {
        monsterAddRemovePane.getRightPanelTopButton().setOnAction(event -> {
            monsterEditPane.getStage().show();
        });

        monsterAddRemovePane.getRightPanelBottomButton().setOnAction(event -> {
            if (monsterAddRemovePane.listHasSelection()) {
                theController.removeMonsterFromSpace(monsterAddRemovePane.getSelectedIndex(), getSelectedSpace());
                updateMonsterAddRemovePaneList();
            }
        });
    }

    private void createTreasureAddRemovePane() {
        treasureAddRemovePane = new ListButtonPane("Add/Remove Treasure", 600, 300);
        treasureAddRemovePane.getLeftPanelLabel().setText("Treasure in Space");
        treasureAddRemovePane.getRightPanelTopButton().setText("Add New Treasure");
        treasureAddRemovePane.getRightPanelBottomButton().setText("Confirm Remove");
        updateTreasureAddRemovePaneList();
        setTreasureAddRemovePaneButtonActions();
    }

    private void updateTreasureAddRemovePaneList() {
        treasureAddRemovePane.setList(theController.getTreasureListInSpace(getSelectedSpace()));

        if (mainText != null) {
            updateMainDescriptionText();
        }
    }

    private void setTreasureAddRemovePaneButtonActions() {
        treasureAddRemovePane.getRightPanelTopButton().setOnAction(event ->{
                treasureAddPane.getStage().show();
        });

        treasureAddRemovePane.getRightPanelBottomButton().setOnAction(event ->{
            System.out.println("remove treasure button");
            if (treasureAddRemovePane.listHasSelection()) {
                theController.removeTreasureFromSpace(treasureAddRemovePane.getSelectedIndex(), getSelectedSpace());
                updateTreasureAddRemovePaneList();
            }
        });
    }

    private void createTreasureAddPane() {
        treasureAddPane = new ListButtonPane("Add Treasure", 600, 300);
        treasureAddPane.getLeftPanelLabel().setText("Treasure");
        treasureAddPane.getRightPanelTopButton().setText("Confirm Add");
        treasureAddPane.getRightPanelBottomButton().setText("Cancel");
        updateTreasureAddPaneList();
        setTreasureAddPaneButtonActions();
    }

    private void updateTreasureAddPaneList() {
        treasureAddPane.setList(theController.getTreasureListDatabase());

        if (mainText != null) {
            updateMainDescriptionText();
        }
    }

    private void setTreasureAddPaneButtonActions() {
        treasureAddPane.getRightPanelTopButton().setOnAction(event ->{
            System.out.println("add treasure button");
            if (treasureAddPane.listHasSelection()) {
                theController.addTreasureToSpace(treasureAddPane.getSelectedIndex(), getSelectedSpace());
                updateTreasureAddRemovePaneList();
            }
        });

        treasureAddPane.getRightPanelBottomButton().setOnAction(event ->{
            treasureAddPane.getStage().hide();
        });
    }

    private void createEditChoiceStage() {
        editChoiceStage = new Stage();
        createEditChoicePane();
        Scene editChoiceScene = new Scene(editChoicePane, 400, 50);
        editChoiceScene.getStylesheets().add("/res/stylesheet.css");
        editChoiceStage.setTitle("Add Treasure or Monster");
        editChoiceStage.setScene(editChoiceScene);
    }

    private BorderPane createEditChoicePane() {
        editChoicePane = new BorderPane();

        editChoicePane.styleProperty().set("-fx-background-color: #262626");
        editChoicePane.setCenter(createEditSceneChoices());

        return editChoicePane;
    }

    private Node createEditSceneChoices() {
        HBox toReturn = new HBox();
        Button monsterButton = new Button("Add/Remove Monster");
        Button treasureButton = new Button("Add/Remove Treasure");
        toReturn.setAlignment(Pos.CENTER);
        toReturn.setSpacing(30);
        monsterButton.setOnAction(event ->{
            monsterAddRemovePane.getStage().show();
            editChoiceStage.hide();
        });

        treasureButton.setOnAction(event ->{
            treasureAddRemovePane.getStage().show();
            editChoiceStage.hide();
        });

        toReturn.getChildren().add(monsterButton);
        toReturn.getChildren().add(treasureButton);

        return toReturn;
    }

    /**
     * utility function gets the currently selected space.
     * @return the index of the selected space
     */
    private Integer getSelectedSpace() {
        return (Integer)list.getSelectionModel().getSelectedIndex();
    }

//this method sets up the main sort of stage
//this is where methods that set up different buttons will go
    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        temp.styleProperty().set("-fx-background-color: #262626");
        Node top = setTopPanel();
        temp.setTop(top);
        Node right = setRightPanel();
        temp.setRight(right);
        Node left = setLeftButtonPanel();  //separate method for the left section
        temp.setLeft(left);
        Node center = setMainPanel();
        temp.setCenter(center);
        return temp;
    }

    /**
     * creates the menu item on the menu bar for saving files.
     * @return the menu item created.
     */
    private MenuItem createTopBarSaveOption() {
        MenuItem saveFile = new MenuItem("Save File");
        saveFile.setOnAction(event -> {
            theController.saveLevel(fileDialog.showSaveDialog(primaryStage));
        });
        return saveFile;
    }

    /**
     * creates the menu item on the menu bar for loading files.
     * @return the menu item created
     */
    private MenuItem createTopBarLoadOption() {
        MenuItem loadFile = new MenuItem("Load File");
        loadFile.setOnAction(event -> {
            theController.loadLevel(fileDialog.showOpenDialog(primaryStage));
            updateList();
        });
        return loadFile;
    }

    /**
     * sets the top menu bar with the file entry.
     * @return the node
     */
    private Node setTopPanel() {
        topBar = new MenuBar();
        fileDialog = new FileChooser();
        Menu optionOne = new Menu("File");

        optionOne.getItems().add(createTopBarSaveOption());
        optionOne.getItems().add(createTopBarLoadOption());

        topBar.getMenus().add(optionOne);
        return topBar;
    }

    /**
     * creates the choice box used for selecting doors in the right panel.
     * @return the choicebox created.
     */
    private ChoiceBox createRightPanelDoorChoiceBox() {
        doors = new ChoiceBox();
        doors.getItems().add("Select Door");
        setDefaultRightPanelChoiceBox();

        doors.getSelectionModel().selectedIndexProperty().addListener(event -> {
            System.out.println(doors.getSelectionModel().getSelectedIndex() - 1);
            if (doors.getSelectionModel().getSelectedIndex() >= 1) {
                changeDescriptionText(theController.getNewPopUpDescription((list.getSelectionModel().getSelectedIndex()), doors.getSelectionModel().getSelectedIndex() - 1));
                descriptionPane.show(primaryStage);
            }
        });
        return doors;
    }

    /**
     * creates the popup for the right panel.
     * @return the popup created
     */
    private Popup createRightPanelPopup() {
        descriptionPane = createPopUp(600, 600, "sample text");
        descriptionPane.setAutoHide(true);
        descriptionPane.setOnAutoHide(event -> {
            setDefaultRightPanelChoiceBox();
        });
        return descriptionPane;
    }

    /**
     * handles the creation of everything to do with the right panel.
     * @return the node containing the right panel;
     */
    private Node setRightPanel() {
        VBox toReturn = new VBox();

        toReturn.setAlignment(Pos.CENTER);
        createRightPanelPopup();
        toReturn.getChildren().add(createRightPanelDoorChoiceBox());

        return toReturn;
    }

    /**
     * simple method for setting the default value in the choice box for doors.
     */
    private void setDefaultRightPanelChoiceBox() {
        doors.setValue("Select Door");
    }

    /**
     * creates the node that will become the main panel.
     * @return the node created by the method
     */
    private Node setMainPanel() {
        VBox toReturn = new VBox();
        mainText = new TextArea();
        initMainText();
        toReturn.getChildren().add(mainText);
        return toReturn;
    }

    /**
     * initializes the styling for the main text window.
     */
    private void initMainText() {
        mainText.setPrefHeight(1000);
        mainText.setPrefWidth(1000);
        mainText.setEditable(false);
        mainText.setWrapText(true);
        setDefaultMainText();
    }

    /**
     * updates the list whenever a new dungeon is loaded in.
     */
    private void updateList() {
        list.getItems().clear();
        for (String s : theController.getSpaceList()) {
            list.getItems().add(s);
        }
        if (mainText != null) {
            setDefaultMainText();
        }
    }

    /**
     * sets the default main text.
     */
    private void setDefaultMainText() {
        mainText.setText("Select a Chamber or Passage and see it's description here!");
    }

    /**
     * creates the list of spaces in the left panel.
     * @return the list of spaces to be added to the scene.
     */
    private ListView createLeftPanelList() {
        list = new ListView();
        updateList();
        list.setPrefHeight(1000);
        list.setOnMouseClicked(event -> {
            updateMainDescriptionText();
            updateMonsterTreasureWindows();
        });
        return list;
    }


    /**
     * updates the windows of the monster and treasure selection windows
     */
    private void updateMonsterTreasureWindows() {
        updateTreasureAddRemovePaneList();
        updateMonsterAddRemovePaneList();
    }

    /**
     * updates the main description text.
     */
    private void updateMainDescriptionText() {
        for(Object o : list.getSelectionModel().getSelectedIndices()){
            changeMainDescriptionText(theController.getNewLeftPanelDescription((Integer)o));
            updateDoorList((Integer)o);
        }

    }

    /**
     * creates the edit button in the left panel.
     * @return the button created by the method
     */
    private Button createLeftPanelEditButton() {
        Button editButton = new Button("Edit Selected Space");
        editButton.setOnAction(event -> {
            if (list.getSelectionModel().getSelectedIndex() != -1) {
                editChoiceStage.show();
            }
        });
        return editButton;
    }

    /**
     * creates the label for the left panel.
     * @return the label created
     */
    private Label createLeftPanelLabel() {
        Label toReturn = new Label("Spaces");
        return toReturn;
    }

    /**
     * generates the entirety of the left panel.
     * @return the node containing the vbox in the left panel.
     */
    private Node setLeftButtonPanel(){
        VBox toReturn = new VBox();
        toReturn.setAlignment(Pos.CENTER);

        toReturn.getChildren().add(createLeftPanelLabel());
        toReturn.getChildren().add(createLeftPanelList());
        toReturn.getChildren().add(createLeftPanelEditButton());

        return toReturn;
    }

    /**
     * updates the drop down list of doors.
     * @param num the number of the space to update
     */
    private void updateDoorList(Integer num) {
        setDefaultRightPanelChoiceBox();
        doors.getItems().remove(1, doors.getItems().size());
        doors.getItems().addAll(theController.getNewChoiceBoxEntries(num));
    }

    /* an example of a popup area that can be set to nearly any
    type of node
     */
    private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        TextArea textA = new TextArea(text);
        textA.setEditable(false);
        popup.getContent().addAll(textA);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMinWidth(80);
        textA.setMinHeight(50);
        return popup;
    }

    /*generic button creation method ensure that all buttons will have a
    similar style and means that the style only need to be in one place
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle(format);
        return btn;
    }

    /**
     * changes the main description window to the selected chamber or passage.
     * @param text the description to set.
     */
    private void changeMainDescriptionText(String text) {
        mainText.setText(text);
    }

    /**
     * changes the descriptionText of the popup descriptionpane.
     * @param text the text to change the description to.
     */
    private void changeDescriptionText(String text) {
        ObservableList<Node> theList = descriptionPane.getContent();
        for (Node t : theList) {
            if (t instanceof TextArea) {
                TextArea temp = (TextArea) t;
                temp.setText(text);
            }

        }

    }

    /**
     * the main method.
     * @param args just some args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
