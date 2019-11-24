package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.*;
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
import javafx.stage.Stage;
import java.util.ArrayList;

public class ListButtonPane{

    private ListView leftPanelList;
    private Label leftPanelLabel;
    private Label emptyLabel;
    private VBox leftPanel;
    private VBox rightPanel;
    private Button topButton;
    private Button bottomButton;
    private BorderPane thePane;
    private Stage theStage;
    private Scene theScene;

    public ListButtonPane(String title, int x, int y) {
        initElements();
        setUpStage();
        Scene theScene = new Scene(thePane, x, y);
        theScene.getStylesheets().add("/res/stylesheet.css");
        theStage.setTitle(title);
        theStage.setScene(theScene);
    }

    private void setUpStage() {
        theStage = new Stage();
        theStage.setOnCloseRequest(event -> {
            getLeftPanelList().getSelectionModel().clearSelection();
        });

    }

    private void initElements() {
        leftPanelList = createLeftPanelList();
        leftPanelLabel = createLeftPanelLabel();
        topButton = createRightPanelTopButton();
        bottomButton = createRightPanelBottomButton();
        emptyLabel = createEmptyLabel();
        thePane = setUpRoot();
    }

    private Label createEmptyLabel() {
        emptyLabel = new Label("The List is Currently Empty");
        return emptyLabel;
    }

    /**
     * sets up the root of the monster editing window.
     * @return the borderpane for editing and selecting monsters
     */
    private BorderPane setUpRoot() {
        thePane = new BorderPane();
        thePane.styleProperty().set("-fx-background-color: #262626");
        thePane.setLeft(setLeftPanel());
        thePane.setRight(setRightPanel());
        return thePane;
    }

    /**
     * handles the setting up of the right panel of the monster selection window.
     * @return the vbox created.
     */
    private Node setRightPanel() {
        rightPanel = new VBox();
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setSpacing(20);
        rightPanel.getChildren().add(topButton);
        rightPanel.getChildren().add(bottomButton);
        return rightPanel;
    }

    /**
     * creates the button for editing a monster.
     * @return the button itself.
     */
    private Button createRightPanelTopButton() {
        topButton = new Button("Top");
        topButton.setPrefWidth(180);
        
        return topButton;
    }

    /**
     * creates the button for selecting a monster.
     * @return the button itself.
     */
    private Button createRightPanelBottomButton() {
        bottomButton = new Button("Bottom");
        bottomButton.setPrefWidth(180);
        return bottomButton;
    }

    /**
     * creates the list of monsters.
     * @return the list of monsters element of the gui.
     */
    private ListView createLeftPanelList() {
        leftPanelList = new ListView();
        
        return leftPanelList;
    }

    /**
     * creates the label for the monster list.
     * @return the label created
     */
    private Label createLeftPanelLabel() {
        leftPanelLabel = new Label("Default");
        return leftPanelLabel;
    }

    /**
     * handles the creation of the entire left panel for the monster selection window.
     * @return the node created.
     */
    private Node setLeftPanel() {
        leftPanel = new VBox();
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPrefWidth(380);
        leftPanel.getChildren().add(leftPanelLabel);
        leftPanel.getChildren().add(leftPanelList);
        return leftPanel;
    }

    /**
     * returns a boolean describing wether or not a selection is made on the list.
     * @return [description]
     */
    public boolean listHasSelection() {
        return (this.getSelectedIndex() != -1);
    }

    /**
     * sets the list view items to be the give arraylist of strings.
     * @param theList the new list of strings to set in the listview.
     */
    public void setList(ArrayList<String> theList) {
        getLeftPanelList().getItems().clear();
        for (String s : theList) {
            getLeftPanelList().getItems().add(s);
        }

        if (theList.size() == 0) {
            getLeftPanel().getChildren().clear();
            getLeftPanel().getChildren().add(emptyLabel);
            getLeftPanel().getChildren().add(leftPanelList);
        } else {
            if (!getLeftPanel().getChildren().contains(leftPanelLabel)) {
                getLeftPanel().getChildren().clear();
                getLeftPanel().getChildren().add(leftPanelLabel);
                getLeftPanel().getChildren().add(leftPanelList);
            }
        }
    }

    /**
     * gets the index of the selected list entry
     * @return the index
     */
    public Integer getSelectedIndex() {
        return (Integer)leftPanelList.getSelectionModel().getSelectedIndex();
    }

    /**
     * gets the selected entry of the list.
     * @return the string
     */
    public String getSelectedEntry() {
        return (String)getLeftPanelList().getItems().get((Integer)getSelectedIndex());
    }

    /**
     * gets the left panel list.
     * @return the list
     */
    public ListView getLeftPanelList() {
        return leftPanelList;
    }

    /**
     * gets the left panel label.
     * @return the label
     */
    public Label getLeftPanelLabel() {
        return leftPanelLabel;
    }

    /**
     * gets the left panel.
     * @return the left panel
     */
    public VBox getLeftPanel() {
        return leftPanel;
    }

    /**
     * gets the right panel.
     * @return the right panel.
     */
    public VBox getRightPanel() {
        return rightPanel;
    }

    /**
     * gets the top button in the right panel.
     * @return the button
     */
    public Button getRightPanelTopButton() {
        return topButton;
    }

    /**
     * gets the bottom button in the right panel.
     * @return the button
     */
    public Button getRightPanelBottomButton() {
        return bottomButton;
    }

    /**
     * gets the border pane.
     * @return the border pane
     */
    public BorderPane getBorderPane() {
        return thePane;
    }

    /**
     * gets the stage.
     * @return the stage
     */
    public Stage getStage() {
        return theStage;
    }

    /**
     * gets the scene.
     * @return the scene
     */
    public Scene getScene() {
        return theScene;
    }
}
