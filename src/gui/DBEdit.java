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
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import database.DBConnection;
import database.DBMonster;


public class DBEdit implements java.io.Serializable {
    private Stage theStage;
    private Scene theScene;
    private Button confirmButton;
    private HBox buttonBox;
    private GridPane thePane;
    private Label nameLabel;
    private Label upperLabel;
    private Label lowerLabel;
    private Label descLabel;
    private TextField nameArea;
    private TextField upperArea;
    private TextField lowerArea;
    private TextField descArea;
    private DBConnection db;
    private DBMonster origMonster;

    /**
     * empty constructor.
     * @return its a constructor
     */
    public DBEdit() {
        db = new DBConnection();
        initWindow();
    }

    /**
     * constructor for creating with a monster.
     * @param  theMonster the monster to edit
     * @return            constructor yo
     */
    public DBEdit(String theMonsterStr) {
        db = new DBConnection();
        DBMonster theMonster = db.findMonster(theMonsterStr);
        origMonster = theMonster;
        initWindow();
        setText(theMonster);
        setDeleteButton();
    }

    /**
     * sets up the gridpane.
     * @return the description idkkkkk
     */
    public GridPane setUpRoot() {
        thePane = new GridPane();
        thePane.styleProperty().set("-fx-background-color: #262626");
        thePane.setAlignment(Pos.CENTER);
        thePane.setPadding(new Insets(30, 30, 30, 30));
        thePane.setVgap(10);
        thePane.setHgap(10);
        addElements();
        return thePane;
    }

    /**
     * adds the elements to the gtidpane.
     */
    private void addElements() {
        thePane.add(nameLabel, 0, 0);
        thePane.add(nameArea, 1, 0);

        thePane.add(upperLabel, 0, 1);
        thePane.add(upperArea, 1, 1);

        thePane.add(lowerLabel, 0, 2);
        thePane.add(lowerArea, 1, 2);

        thePane.add(descLabel, 0, 3);
        thePane.add(descArea, 1, 3);

        Node buttonNode = setUpButton();
        thePane.add(buttonNode, 0, 5);
    }


    /**
     * initializes the window.
     */
    private void initWindow() {
        theStage = new Stage();
        theStage.setTitle("Adding/Editing Monster");

        // theStage.focusedProperty().addListener(event -> {
        //     theStage.hide();
        // });

        initFields();
        thePane = setUpRoot();
        theScene = new Scene(thePane, 400, 250);
        theScene.getStylesheets().add("/res/stylesheet.css");
        theStage.setScene(theScene);
    }

    /**
     * sets up the confirm button.
     * @return the node
     */
    private Node setUpButton() {
        confirmButton = new Button("Confirm");
        buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.getChildren().add(confirmButton);

        confirmButton.setOnAction(event -> {
            updateDatabase();
            getStage().hide();
        });

        return buttonBox;
    }

    /**
     * sets up the delete button.
     */
    private void setDeleteButton() {
        confirmButton = new Button("Delete");
        buttonBox.getChildren().add(confirmButton);

        confirmButton.setOnAction(event -> {
            db.deleteMonster(origMonster.getName());
            getStage().hide();
        });
    }

    /**
     * inits the fields.
     */
    private void initFields() {
        nameLabel = new Label("Name");
        upperLabel = new Label("Upper Bound");
        lowerLabel = new Label("Lower Bound");
        descLabel = new Label("Description");

        nameArea = new TextField("");
        upperArea = new TextField("");
        lowerArea = new TextField("");
        descArea = new TextField("");
    }

    /**
     * sets up the existing fields if given a monster.
     * @param theMonster the monster to use
     */
    private void setText(DBMonster theMonster) {
        nameArea.setText(theMonster.getName());
        upperArea.setText(theMonster.getUpper());
        lowerArea.setText(theMonster.getLower());
        descArea.setText(theMonster.getDescription());
    }

    /**
     * returns the monster created from the fields shown to the user.
     * @return the monster.
     */
    public DBMonster getMonster() {
        DBMonster toReturn = new DBMonster();

        if (nameArea.getText().equals("")) {
            toReturn.setName(" ");
        } else {
            toReturn.setName(nameArea.getText());
        }

        if (upperArea.getText().equals("")) {
            toReturn.setUpperBound(" ");
        } else {
            toReturn.setUpperBound(upperArea.getText());
        }

        if (lowerArea.getText().equals("")) {
            toReturn.setLowerBound(" ");
        } else {
            toReturn.setLowerBound(lowerArea.getText());
        }

        if (descArea.getText().equals("")) {
            toReturn.setDescription(" ");
        } else {
            toReturn.setDescription(descArea.getText());
        }

        return toReturn;
    }

    /**
     * updates the databse entry.
     */
    public void updateDatabase() {
        if (origMonster != null) {
            db.deleteMonster(origMonster.getName());
        }

        db.addMonster(getMonster());
    }

    /**
     * returns the confirmbutton.
     * @return the button
     */
    public Button getButton() {
        return confirmButton;
    }

    /**
     * gets the gridpane.
     * @return the gridpane
     */
    public GridPane getGridPane() {
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