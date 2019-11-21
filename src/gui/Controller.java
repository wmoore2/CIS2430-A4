package gui;

import java.util.ArrayList;
import dungeon.DungeonGenerator;

public class Controller {
    private DungeonGenerator dungeon;
    private GuiDemo myGui;

    public Controller(GuiDemo theGui) {
        myGui = theGui;
        dungeon = new DungeonGenerator();
        dungeon.buildDungeon();
    }

    private String getNameList() {
        return "0";
    }

    public void reactToButton() {
        System.out.println("Thanks for clicking!");
    }

    /**
     * reacts to the left bar getting clicked.
     * @param index the index of the entry in the list to use for the update.
     */
    public void reactToLeftPanel(Integer index) {
        System.out.println(dungeon.getSpaceDescription(index));
    }

    public String getNewPopUpDescription(Integer spaceIndex, Integer doorIndex) {
        return dungeon.getDoorDescriptionFromSpace(spaceIndex, doorIndex);
    }

    /**
     * gets the new entries for the drop down menu of doors.
     * @param  index the space to get the doors from
     * @return       the list of doors
     */
    public ArrayList<String> getNewChoiceBoxEntries(Integer index) {
        return dungeon.getDoorsFromSpace(index);
    }

    public String getNewLeftPanelDescription(Integer index) {
        return dungeon.getSpaceDescription(index);
    }

    /**
     * gets the strings for the left sidebar.
     * @return the array of names
     */
    public ArrayList<String> getSpaceList() {
        return dungeon.getSpaceNames();
    }

    public String getNewDescription() {
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return getNameList();
    }

}
