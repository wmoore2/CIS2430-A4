package gui;

import java.util.ArrayList;

public class Controller {
    private GuiDemo myGui;

    public Controller(GuiDemo theGui){
        myGui = theGui;
    }

    private String getNameList(){
        return "0";
    }

    public void reactToButton(){
        System.out.println("Thanks for clicking!");
    }

    public String getNewDescription(){
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return getNameList();
    }

}
