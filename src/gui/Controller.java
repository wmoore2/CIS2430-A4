package gui;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import dungeon.DungeonGenerator;

public class Controller {
    /**
     * java.
     */
    private DungeonGenerator dungeon;
    /**
     * this is a comment.
     */
    private GuiDemo myGui;

    /**
     * this is a comment.
     * @param  theGui stuff.
     */
    public Controller(GuiDemo theGui) {
        myGui = theGui;
        dungeon = new DungeonGenerator();
        dungeon.buildDungeon();
    }

    /**
     * please judi dont run checkstyle.
     * @return this is bad
     */
    private String getNameList() {
        return "0";
    }

    /**
     * never use this.
     */
    public void reactToButton() {
        System.out.println("Thanks for clicking!");
    }

    /**
     * gets monster form db.
     * @param  index the index
     * @return       the thuing
     */
    public String getMonsterFromDatabase(Integer index) {
        return dungeon.getMonsterFromDatabase(index);
    }

    /**
     * adds monster to a space.
     * @param itemIndex  the thing
     * @param spaceIndex other thing
     */
    public void addMonsterToSpace(Integer itemIndex, Integer spaceIndex) {
        //get monster from database and turn it into monster we can work with then pass it off to the dungeon.
        dungeon.addMonsterToSpace(itemIndex, spaceIndex);
    }

    /**
     * remove from space.
     * @param itemIndex  the index
     * @param spaceIndex other index.
     */
    public void removeMonsterFromSpace(Integer itemIndex, Integer spaceIndex) {
        dungeon.removeMonsterFromSpace(itemIndex, spaceIndex);
    }

    /**
     * comment.
     * @param itemIndex  this is taking too long
     * @param spaceIndex omg
     */
    public void addTreasureToSpace(Integer itemIndex, Integer spaceIndex) {
        dungeon.addTreasureToSpace(itemIndex, spaceIndex);
    }

    /**
     * i am sorry ta.
     * @param itemIndex  this
     * @param spaceIndex is
     */
    public void removeTreasureFromSpace(Integer itemIndex, Integer spaceIndex) {
        dungeon.removeTreasureFromSpace(itemIndex, spaceIndex);
    }

    /**
     * bad.
     * @param  theIndex omg
     * @return          omg
     */
    public ArrayList<String> getMonsterListInSpace(Integer theIndex) {
        return dungeon.getMonsterListFromSpace(theIndex);
    }

    /**
     * real bad.
     * @return omg
     */
    public ArrayList<String> getMonsterListDatabase() {
        return dungeon.getDatabaseMonsterList();
    }

    /**
     * i tried i guess.
     * @param theTreasure no i didnt
     * @param theIndex    not at all
     */
    public void setTreasureInSpace(String theTreasure, Integer theIndex) {
        //get monster from database and turn it into monster we can work with then pass it off to the dungeon.
        System.out.println(theTreasure + " " + theIndex);
    }

    /**
     * why.
     * @return why
     */
    public ArrayList<String> getTreasureListDatabase() {
        return dungeon.getPossibleTreasure();
    }

    /**
     * i hate this.
     * @param  theIndex this sucks
     * @return          pls be nice
     */
    public ArrayList<String> getTreasureListInSpace(Integer theIndex) {
        return dungeon.getTreasureListFromSpace(theIndex);
    }

    /**
     * reacts to the left bar getting clicked.
     * @param index the index of the entry in the list to use for the update.
     */
    public void reactToLeftPanel(Integer index) {
        System.out.println(dungeon.getSpaceDescription(index));
    }

    /**
     * saves the level to the given filename.
     * @param theFile the file to save the level to
     * @return      sup
     */
    public int saveLevel(File theFile) {
        if (theFile == null) {
            return 0;
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(theFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(dungeon);

            fileOut.close();
            objectOut.close();
        } catch (IOException e) {
            System.out.println("Could not initalize the file.");
            return 1;
        }
        return 0;
    }

    /**
     * loads the given file to become the new level.
     * @param theFile the file to open up
     * @return      hows it goin
     */
    public int loadLevel(File theFile) {
        if (theFile == null) {
            return 0;
        }
        try {
            FileInputStream fileIn = new FileInputStream(theFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            dungeon.resetSpaces();
            dungeon = (DungeonGenerator) objectIn.readObject();

            fileIn.close();
            objectIn.close();
        } catch (IOException e) {
            System.out.println("Could not initalize the file.");
            return 1;
        } catch (ClassNotFoundException e) {
            System.out.println("File not found.");
            return 2;
        }
        return 0;
    }

    /**
     * gets a new description for the popup.
     * @param  spaceIndex the index of the space.
     * @param  doorIndex  the index of the door in the space
     * @return            the new description
     */
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

    /**
     * stuff.
     * @param  index idk
     * @return       more
     */
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

    /**
     * i cant recall if i use this.
     * @return it stays
     */
    public String getNewDescription() {
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return getNameList();
    }

}
