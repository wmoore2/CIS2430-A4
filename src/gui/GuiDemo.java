package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
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


    private Node setLeftButtonPanel() {
        /*this method should be broken down into even smaller methods, maybe one per button*/
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        /*This button listener is an example of a button changing something
        in the controller but nothing happening in the view */

        Button firstButton = createButton("Hello world", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
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
        btn.setStyle("");
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

//    private GridPane createGridPanel() {
//        GridPane t = new GridPane();
//        /*t.setStyle(
//                "-fx-border-style: solid inside;" +
//                        "-fx-border-width: 2;" +
//                        "-fx-border-insets: 1;" +
//                        "-fx-border-radius: 9;" +
//                        "-fx-border-color: black;");*/
//        Node[] tiles = makeTiles();  //this should be a roomview object
//        t.add(tiles[0],0,0,1,1);
//        t.add(tiles[1],0,1,1,1);
//        t.add(tiles[2],0,2,1,1);
//        t.add(tiles[3],0,3,1,1);
//        t.add(tiles[4],1,0,1,1);
//        t.add(tiles[5],1,1,1,1);
//        t.add(tiles[6],1,2,1,1);
//        t.add(tiles[7],1,3,1,1);
//        t.add(tiles[8],2,0,1,1);
//        t.add(tiles[9],2,1,1,1);
//        t.add(tiles[10],2,2,1,1);
//        t.add(tiles[11],2,3,1,1);
//        t.add(tiles[12],3,0,1,1);
//        t.add(tiles[13],3,1,1,1);
//        t.add(tiles[14],3,2,1,1);
//        t.add(tiles[15],3,3,1,1);
//        //t.setHgap(0);
//        //t.setVgap(0);
//          return t;
//    }

//    private TilePane createTilePanel() {
//        TilePane t = new TilePane();
//        t.setStyle(
//                "-fx-border-style: solid inside;" +
//                        "-fx-border-width: 2;" +
//                        "-fx-border-insets: 1;" +
//                        "-fx-border-radius: 9;" +
//                        "-fx-border-color: black;");
//        Node[] tiles = makeTiles();  //this should be a roomview object
//        int len = tiles.length/4; //hacky way to make a 4x4
//        t.setOrientation(Orientation.HORIZONTAL);
//        t.setTileAlignment(Pos.CENTER_LEFT);
//        t.setHgap(0);
//        t.setVgap(0);
//        t.setPrefColumns(4);
//        t.setMaxWidth(4 *50);  //should be getting the size from the roomview object
//        ObservableList list = t.getChildren();
//        list.addAll(tiles);  //write a method that adds the roomview objects
//        return t;
//    }

//    private Node[] makeTiles() {
//    String floor = "/res/floor.png";
//    String treasure = "/res/tres.png";
//
//        Node[] toReturn = {
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(treasure),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor)
//            };
//
//        return toReturn;
//}
//
//    public Node floorFactory(String img) {
//        Image floor = new Image(getClass().getResourceAsStream(img));
//        Label toReturn = new Label();
//        ImageView imageView = new ImageView(floor);
//        imageView.setFitWidth(50);
//        imageView.setFitHeight(50);
//        toReturn.setGraphic(imageView);
//        return toReturn;
//    }

    public static void main(String[] args) {
        launch(args);
    }

}
