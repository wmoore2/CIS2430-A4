package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ChamberView extends GridPane {
    private String floor;
    private String treasure;
    private int length;
    private int width;


    public ChamberView(int len, int wid){
        floor = "/res/floor.png";
        treasure = "/res/tres.png";
        length = len;
        width = wid; //user these values to decide the size of the view and how many tiles

        Node[] tiles = makeTiles(len * wid, floor);

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < wid; j++) {
                add(tiles[wid * i + j], i, j, 1, 1);
            }
        }

    }


    private Node[] makeTiles(int num, String theFloor) {  //should have a parameter and a loop

        Node[] toReturn = new Node[num];
        for (int i = 0; i < num; i++) {
            toReturn[i] = floorFactory(theFloor);
        }
        return toReturn;
    }


    public Node floorFactory(String img) {
        Image floor = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(floor);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        toReturn.setGraphic(imageView);
        return toReturn;
    }


}
