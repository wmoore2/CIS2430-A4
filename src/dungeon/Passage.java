package dungeon;

import database.DBMonster;
import dnd.models.Monster;
import dnd.models.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Passage extends Space implements java.io.Serializable{
    /**
     * the roll for a chamber.
     */
    private static final int CHAMBER_ROLL = 5;
    /**
     * the max number of sections in a passage.
     */
    private static final int MAX_NUM_SECTIONS = 10;
    /**
     * roll for a dead end.
     */
    private static final int DEAD_END_ROLL = 19;
    /**
     * the total number of passages.
     */
    private static int numPassage = 0;
    /**
     * boolean to signify if the passage is ended.
     */
    private boolean endBool;
    /**
     * list of passage sections.
     */
    private ArrayList<PassageSection> thePassage;
    /**
     * hasmap of doors and sections.
     */
    private HashMap<Door, PassageSection> doorMap;
    private HashMap<Treasure, ArrayList<PassageSection>> treasureMap;
    private HashMap<DBMonster, ArrayList<PassageSection>> monsterMap;

    /**
     * constructor.
     * @param  isEnd boolean if its an end passage
     */
    public Passage(boolean isEnd) {
        initPassage();
        if (isEnd) {
            setDeadEnd(isEnd);
            addSection(DEAD_END_ROLL);
        }
    }

    /**
     * initializes the passage.
     */
    private void initPassage()  {
        thePassage = new ArrayList<PassageSection>();
        doorMap = new HashMap<Door, PassageSection>();
        treasureMap = new HashMap<Treasure, ArrayList<PassageSection>>();
        monsterMap = new HashMap<DBMonster, ArrayList<PassageSection>>();
        setChamberNum();
        setDeadEnd(false);
    }

    /**
     * gets the number of passageSections in the passage.
     * @return integer
     */
    public int getNumSections() {
        return thePassage.size();
    }

    /**
     * resets the number of passages.
     */
    @Override
    public void reset() {
        numPassage = 0;
    }

    /**
     * sets the number of chambers.
     */
    private void setChamberNum() {
        setSpaceNum(numPassage);
        numPassage++;
    }

    /**
     * Clears the passage arraylist and the doormap.
     */
    private void clearAll() {
        thePassage.clear();
        doorMap.clear();
    }

    /**
     * builds a random passage.
     * @return the passage it built
     */
    public Passage buildRandomPassage() {
        PassageSection temp;
        clearAll();
        /*generate entire random passage*/
        for (int i = 0; i < MAX_NUM_SECTIONS; i++) {
            if (i == (MAX_NUM_SECTIONS - 1)) {
                //force a chamber
                temp = addSection(CHAMBER_ROLL);
            } else  {
                //make a random passage section
                temp = addSection(true);
            }
            if (temp.isEnd()) {
                break;
            }
        }
        return this;
    }

    /**
     * gets the doors in the passage.
     * @return the list of doors
     */
    @Override
    public ArrayList<Door> getDoors() {
        //gets all of the doors in the entire passage
        Set<Door> doorSet = doorMap.keySet();
        ArrayList<Door> toReturn = new ArrayList<Door>(doorSet);
        return toReturn;
    }

    /**
     * returns if the passage is a dead end.
     * @return returns the boolean
     */
    public boolean isDeadEnd() {
        return endBool;
    }

    /**
     * sets if the passage is a dead end.
     * @param toSet the boolean to set
     */
    public void setDeadEnd(boolean toSet) {
        endBool = toSet;
    }

    /**
     * gets the door at index i.
     * @param  i the index of the door
     * @return   the door at index i
     */
    public Door getDoor(int i) {
        //returns the door in section 'i'. if  there is no door, returns null
        return thePassage.get(i).getDoor();
    }

    /**
     * adds a monster at index i to the passage.
     * @param theMonster the monster to add
     * @param i          the index
     */
    public void addMonster(DBMonster theMonster, int i) {
        // adds a monster to section 'i' of the passage
        thePassage.get(i).addMonster(theMonster);
    }

    /**
     * gets the monster at index i.
     * @param  i the index
     * @return   the monster at the index
     */
    public DBMonster getMonster(int i) {
        //returns DBMonster door in section 'i'. if  there is no DBMonster, returns null
        return thePassage.get(i).getMonster();
    }

    /**
     * adds a door to the given section.
     * @param temp the section to add a door to
     */
    private void addSectionDoor(PassageSection temp) {
        if (temp.hasDoor()) {
            temp.getDoor().setSpaces(this, null);
        }
    }

    /**
     * adds the given monster to the passage.
     * @param theMonster the monster
     */
    public void addMonster(DBMonster theMonster) {
        ArrayList<PassageSection> tempList;
        PassageSection temp = new PassageSection(20);
        if (!monsterMap.containsKey(theMonster)) {
            tempList = new ArrayList<PassageSection>();
        } else {
            tempList = monsterMap.get(theMonster);
        }
        temp.addMonster(theMonster);
        tempList.add(temp);
        monsterMap.put(theMonster, tempList);
        addPassageSection(temp);
    }

    /**
     * removes the given monster from the passage.
     * @param theMonster the monster
     */
    @Override
    public void removeMonster(DBMonster theMonster) {
        thePassage.remove(monsterMap.get(theMonster).get(0));
        monsterMap.get(theMonster).remove(0);
        if (monsterMap.get(theMonster).size() == 0) {
            monsterMap.remove(theMonster);
        }
    }

    /**
     * adds the given treasure to the passage.
     * @param theTreasure the treasure.
     */
    @Override
    public void addTreasure(Treasure theTreasure) {
        ArrayList<PassageSection> tempList;
        PassageSection temp = new PassageSection(21);
        if (!treasureMap.containsKey(theTreasure)) {
            tempList = new ArrayList<PassageSection>();
        } else {
            tempList = treasureMap.get(theTreasure);
        }
        temp.addTreasure(theTreasure);
        tempList.add(temp);
        treasureMap.put(theTreasure, tempList);
        addPassageSection(temp);
    }

    /**
     * removes the given treasure from the passage.
     * @param theTreasure the treasure
     */
    @Override
    public void removeTreasure(Treasure theTreasure) {
        thePassage.remove(treasureMap.get(theTreasure).get(0));
        treasureMap.get(theTreasure).remove(0);
        if (treasureMap.get(theTreasure).size() == 0) {
            treasureMap.remove(theTreasure);
        }
    }

    /**
     * gets the list of treasure in the passage.
     * @return the list of treasure
     */
    @Override
    public ArrayList<Treasure> getTreasureList() {
        int counter = 0;
        ArrayList<Treasure> toReturn = new ArrayList<Treasure>();
        ArrayList<PassageSection> temp;
        for (Treasure t : treasureMap.keySet()) {
            temp = new ArrayList<PassageSection>(treasureMap.get(t));
            for (int i = 0; i < temp.size(); i++) {
                toReturn.add(t);
            }
            counter++;
        }
        return toReturn;
    }

    /**
     * gets the list of monsters in the passage.
     * @return the list of monsters
     */
    @Override
    public ArrayList<DBMonster> getMonsters() {
        int counter = 0;
        ArrayList<DBMonster> toReturn = new ArrayList<DBMonster>();
        ArrayList<PassageSection> temp;
        for (DBMonster t : monsterMap.keySet()) {
            temp = new ArrayList<PassageSection>(monsterMap.get(t));
            for (int i = 0; i < temp.size(); i++) {
                toReturn.add(t);
            }
            counter++;
        }
        return toReturn;
    }

    /**
     * adds a section from the given roll.
     * @param  roll the roll of the section to add
     * @return      the section that was added
     */
    public PassageSection addSection(int...roll) {
        PassageSection temp = new PassageSection(roll);
        addPassageSection(temp);
        addSectionDoor(temp);
        return temp;
    }

    /**
     * adds a section with a boolean now.
     * @param  isRand the boolean
     * @return        the section added
     */
    public PassageSection addSection(boolean isRand) {
        PassageSection temp = new PassageSection(isRand);
        addPassageSection(temp);
        addSectionDoor(temp);
        return temp;
    }

    /**
     * adds the given passagesection to the list of sections.
     * @param toAdd the section to be added
     */
    private void addPassageSection(PassageSection toAdd) {
        //adds the passage section to the passageway
        if (toAdd != null) {
            thePassage.add(toAdd);
        }
    }

    /**
     * same thing as setdoor basically.
     * @param newDoor the door to be added to the passage
     */
    @Override
    public void addDoor(Door newDoor) {
        //should add a door connection to the current Passage Section
        if (thePassage.isEmpty()) {
            addPassageSection(new PassageSection());
        }
        addNewDoor(newDoor);
    }

    /**
     * same thing as setdoor basically.
     * @param newDoor the door to be added to the passage
     * @param roll the roll to add
     */
    public void addDoorSection(Door newDoor, int...roll) {
        //should add a door connection to the current Passage Section
        PassageSection temp = new PassageSection(roll[0]);
        addPassageSection(temp);
        addNewDoor(newDoor);
    }

    /**
     * adds a new door to the passage.
     * @param newDoor the door to be added to the passage
     */
    private void addNewDoor(Door newDoor) {
        PassageSection temp = thePassage.get(thePassage.size() - 1);
        temp.addDoor(newDoor);
        mapDoor(newDoor);
    }

    /**
     * maps the given door to the doormap.
     * @param newDoor the new door to map
     */
    private void mapDoor(Door newDoor) {
        PassageSection temp = thePassage.get(thePassage.size() - 1);
        doorMap.put(newDoor, temp);
    }

    /**
     * gets the description of all the sections in the passage.
     * @return the description
     */
    @Override
    public String getDescription() {
        PassageSection temp;
        String toReturn = new String();
        toReturn = toReturn.concat("Passage Number: " + (getSpaceNum() + 1) + "\n");
        for (int i = 0; i < thePassage.size(); i++)  {
            temp = thePassage.get(i);
            toReturn = toReturn.concat("\tSection " + (i + 1) + ": " + temp.getDescription() + "\n");
        }
        return toReturn;
    }
}
