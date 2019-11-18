package dungeon;

import dnd.models.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Passage extends Space {
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
        setChamberNum();
        setDeadEnd(false);
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
    public void addMonster(Monster theMonster, int i) {
        // adds a monster to section 'i' of the passage
        thePassage.get(i).addMonster(theMonster);
    }

    /**
     * gets the monster at index i.
     * @param  i the index
     * @return   the monster at the index
     */
    public Monster getMonster(int i) {
        //returns Monster door in section 'i'. if  there is no Monster, returns null
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
        toReturn = toReturn.concat("\nPassage Number: " + (getSpaceNum() + 1) + "\n");
        for (int i = 0; i < thePassage.size(); i++)  {
            temp = thePassage.get(i);
            toReturn = toReturn.concat("\tSection " + (i + 1) + ": " + temp.getDescription() + "\n");
        }
        return toReturn;
    }
}
