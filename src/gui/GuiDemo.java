package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class GuiDemo<toReturn> extends Application {
    /* Even if it is a GUI it is useful to have instance variables
    so that you can break the processing up into smaller methods that have
    one responsibility.
     */
    private Controller theController;
    private BorderPane root;  //the root element of this GUI
    private Popup descriptionPane;
    private Stage primaryStage;  //The stage that is passed in on initialization

    /*a call to start replaces a call to the constructor for a JavaFX GUI*/
    @Override
    public void start(Stage assignedStage) {
        /*Initializing instance variables */
        theController = new Controller(this);
        primaryStage = assignedStage;
        /*Border Panes have  top, left, right, center and bottom sections */
        root = setUpRoot();
        descriptionPane = createPopUp(200, 300, "Example Description of something");
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("Hello GUI Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
//this method sets up the main sort of stage
//this is where methods that set up different buttons will go
    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        temp.setTop(new Label("The name or identifier of the thing below"));
        Node left = setLeftButtonPanel();  //separate method for the left section
        temp.setLeft(left);
        //TilePane room = createTilePanel();
        GridPane room = new ChamberView(4,4);
        temp.setCenter(room);
        return temp;
    }

    private Node setLeftButtonPanel(){
        VBox toReturn = new VBox();
        toReturn.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: purple;");
        ListView<String> list = new ListView<String>();
        ObservableList<String> theItems = FXCollections.observableArrayList("test1", "second entry", "third and so on");
        list.setItems(theItems);
        toReturn.getChildren().add(list);

        Button testButton = new Button("Show Info");
        testButton.setOnAction(event -> {
            ObservableList selected = list.getSelectionModel().getSelectedIndices();
            for(Object o : selected){
                System.out.println("o = " + o + " (" + o.getClass() + ")");
            }
        });
        toReturn.getChildren().add(testButton);
        return toReturn;
    }


    private Node setLeftButtonPanel1() {
        /*this method should be broken down into even smaller methods, maybe one per button*/
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: purple;");
        /*This button listener is an example of a button changing something
        in the controller but nothing happening in the view */

        Button firstButton = createButton("Hello world", "-fx-background-color: #FFFFFF; -fx-background-radius: 10, 10, 10, 10;");
        firstButton.setOnAction((ActionEvent event) -> {
            theController.reactToButton();
        });
        temp.getChildren().add(firstButton);

        /*This button listener is only changing the view and doesn't need
        to contact the controller
         */
        Button showButton = createButton("Show Description", "-fx-background-color: #FFFFFF; ");
        showButton.setOnAction((ActionEvent event) -> {
            descriptionPane.show(primaryStage);
        });
        temp.getChildren().add(showButton);

        /*this button listener is an example of getting data from the controller */
        Button hideButton = createButton("Hide Description", "-fx-background-color: #FFFFFF; ");
        hideButton.setOnAction((ActionEvent event) -> {
            descriptionPane.hide();
            changeDescriptionText(theController.getNewDescription());
        });
        temp.getChildren().add(hideButton);

        Button button = new Button();
        return temp;

    }

    /* an example of a popup area that can be set to nearly any
    type of node
     */
    private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        TextArea textA = new TextArea(text);
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

    private void changeDescriptionText(String text) {
        ObservableList<Node> list = descriptionPane.getContent();
        for (Node t : list) {
            if (t instanceof TextArea) {
                TextArea temp = (TextArea) t;
                temp.setText(text);
            }

        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
