package dungeon;

import dnd.models.Monster;
import database.DBMonster;
import dnd.models.Treasure;
import dnd.exceptions.NotProtectedException;
import java.util.HashMap;
import dnd.die.D20;

public class PassageSection implements java.io.Serializable{
    /**
     * Hashmap containing teh table for passage section rolls.
     */
    private static HashMap<Integer, String> rollTable;
    /**
    *   the archway constant.
    **/
    private static final int ARCHWAY = 1;
    /**
    *   boolean chamber.
    **/
    private static final boolean CHAMBER = true;
    /**
    *   boolean passage.
    **/
    private static final boolean PASSAGE = false;
    /**
    *   the monster in the passage.
    **/
    private DBMonster passageMonster;
    private Treasure passageTreasure;
    /**
    *   the door in the passage.
    **/
    private Door passageDoor;
    /**
    *   tells wether or not there is a monster.
    **/
    private boolean monsterBool;
    private boolean treasureBool;
    /**
    *   tells wether there is a door or not.
    **/
    private boolean doorBool;
    /**
    *   tells wether its a dead end or not.
    **/
    private boolean endBool;
    /**
    *   the roll on the table.
    **/
    private int sectionRoll;
    /**
    *   the description of the passage.
    **/
    private String passageDescription;
    /**
    *   the direction of the passage.
    **/
    private String passageDirection;

    /**
     * constructor.
     * @param  roll the argument
     */
    public PassageSection(int...roll) {
        //sets up the 10 foot section with default settings or specified roll
        setMonster(false);
        setDoor(false);
        if  (roll.length == 0)  {
            setRoll(0);
        } else  {
            setRoll(roll[0]);
        }
        genSection();
    }

    /**
     * constructor.
     * @param  rand the boolean argument
     */
    public PassageSection(boolean rand) {
        //sets up the 10 foot section with random or default roll
        setMonster(false);
        setDoor(false);
        if (rand) {
            setRoll(D20.d20());
        } else  {
            setRoll(0);
        }
        genSection();
    }

    /**
     * generates a section of passage.
     */
    private void genSection() {
        //i already refactored this down and removed another method.
        //this could be broken down more but this is more readable to me.
        DBMonster temp;
        initializeTable();
        setDescription(rollTable.get(getRoll()));
        switch (getRoll()) {
            case 3:
            case 4:
            case 5:
                setEnd(true);
                addDoor(new Door(CHAMBER));
                break;
            case 6:
            case 7:
                addDoor(new Door(PASSAGE, ARCHWAY));
                break;
            case 8:
            case 9:
                addDoor(new Door(PASSAGE, ARCHWAY));
                break;
            case 14:
            case 15:
            case 16:
                setEnd(true);
                addDoor(new Door(CHAMBER, ARCHWAY));
                break;
            case 18:
            case 19:
                setEnd(true);
                break;
            case 20:
                // temp = new DBMonster();
                // temp.setType(D20.d20());
                // addMonster(temp);
                break;
            default:
                break;
        }
    }

    /**
     * sets the roll to the given int.
     * @param roll the given roll
     */
    private void setRoll(int roll) {
        sectionRoll = roll;
    }

    /**
     * sets the description based off of the sgiven string.
     * @param description the given description
     */
    private void setDescription(String description)  {
        passageDescription = description;
    }

    /**
     * returns if the section has a door.
     * @return boolean doorBool
     */
    public boolean hasDoor() {
        return doorBool;
    }

    /**
     * returtns if the section has a monster.
     * @return monsterbool
     */
    private boolean hasMonster() {
        return monsterBool;
    }

    /**
     * returtns if the section has a monster.
     * @return treasureBool
     */
    private boolean hasTreasure() {
        return treasureBool;
    }

    /**
     * sets if the end of the section is here.
     * @param flag boolean to set
     */
    private void setEnd(boolean flag) {
        endBool = flag;
    }

    /**
     * returns endbool.
     * @return endbool
     */
    public boolean isEnd() {
        return endBool;
    }

    /**
     * sets if the section has a door.
     * @param flag wether or not it has a door.
     */
    private void setDoor(boolean flag)  {
        doorBool = flag;
    }

    /**
     * sets the monster boolean.
     * @param flag the given flag t or f
     */
    private void setMonster(boolean flag)  {
        monsterBool = flag;
    }

    /**
     * adds a monster and flips the flag to true.
     * @param theMonster the monster to add
     */
    public void addMonster(DBMonster theMonster) {
        passageMonster = theMonster;
        setMonster(true);
    }

    /**
     * sets the treasure boolean.
     * @param flag the given flag t or f
     */
    private void setTreasure(boolean flag)  {
        treasureBool = flag;
    }

    /**
     * adds a treasure and flips the flag to true.
     * @param theTreasure the treasure to add
     */
    public void addTreasure(Treasure theTreasure) {
        passageTreasure = theTreasure;
        setTreasure(true);
    }

    /**
     * adds a door.
     * @param theDoor the door to add
     */
    public void addDoor(Door theDoor) {
        passageDoor = theDoor;
        setDoor(true);
    }

    /**
     * returns the section roll.
     * @return the section roll
     */
    public int getRoll() {
        return sectionRoll;
    }

    /**
     * gets the door.
     * @return the door in the section
     */
    public Door getDoor() {
        //returns the door that is in the passage section, if  there is one
        if  (hasDoor())  {
            return passageDoor;
        } else  {
            return null;
        }
    }

    /**
     * gets the monster in the section.
     * @return the monster in the section.
     */
    public DBMonster getMonster() {
        //returns the monster that is in the passage section, if  there is one
        if  (hasMonster())  {
            return passageMonster;
        } else  {
            return null;
        }
    }

    /**
     * gets the treasure in the section.
     * @return the treasure in the section.
     */
    public Treasure getTreasure() {
        //returns the treasure that is in the passage section, if  there is one
        if  (hasTreasure())  {
            return passageTreasure;
        } else  {
            return null;
        }
    }

    /**
     * generates the description.
     * @return string of description
     */
    private String genDescription() {
        String toReturn = new String();
        if (hasMonster()) {
            DBMonster monster = getMonster();
            toReturn = toReturn.concat("Monster: " + monster.toString());
        } else if (hasTreasure()) {
            Treasure treasure = getTreasure();
                toReturn = toReturn.concat("\n   Treasure: " + treasure.getDescription() + " contained in " + treasure.getContainer());
            try  {
                toReturn = toReturn.concat(" protected by " + treasure.getProtection());
            } catch (NotProtectedException e)  {
                toReturn = toReturn.concat("unprotected");
            }
        } 
        return toReturn;
    }

    /**
     * generates the door description.
     * @return the string description
     */
    private String genDoorDescription() {
        String toReturn = new String("");
        if (hasDoor()) {
                toReturn = toReturn.concat(getDoor().getDescription());
            
            toReturn = toReturn.concat("\t\tDoor is connected to: ");
            if (getDoor().getConnectType() == CHAMBER) {
                toReturn = toReturn.concat("Chamber " + getDoor().getSpaceNumIndex(1));
            } else  {
                toReturn = toReturn.concat("Passage " + getDoor().getSpaceNumIndex(1));
            }
        }
        return toReturn;
    }

    /**
     * gets the descriptions.
     * @return string containing the descriptions
     */
    public String getDescription() {
        String toReturn = new String();
        toReturn = passageDescription;
        toReturn = toReturn.concat(".\n\t\t");
        toReturn = toReturn.concat(genDoorDescription());
        toReturn = toReturn.concat(genDescription());
        return toReturn;
    }

    /**
     * Initializes the table of possible rolls for the passage section.
     */
    private static void initializeTable() {
        rollTable = new HashMap<Integer, String>();
        rollTable.put(1, "Passage goes straight for 10 ft");
        rollTable.put(2, "Passage goes straight for 10 ft");
        rollTable.put(3, "Passage ends in Door to a Chamber");
        rollTable.put(4, "Passage ends in Door to a Chamber");
        rollTable.put(5, "Passage ends in Door to a Chamber");
        rollTable.put(6, "Archway (door) to right (main passage continues straight for 10 ft)");
        rollTable.put(7, "Archway (door) to right (main passage continues straight for 10 ft)");
        rollTable.put(8, "Archway (door) to left (main passage continues straight for 10 ft)");
        rollTable.put(9, "Archway (door) to left (main passage continues straight for 10 ft)");
        rollTable.put(10, "Passage turns to left and continues for 10 ft");
        rollTable.put(11, "Passage turns to left and continues for 10 ft");
        rollTable.put(12, "Passage turns to right and continues for 10 ft");
        rollTable.put(13, "Passage turns to right and continues for 10 ft");
        rollTable.put(14, "Passage ends in archway (door) to chamber");
        rollTable.put(15, "Passage ends in archway (door) to chamber");
        rollTable.put(16, "Passage ends in archway (door) to chamber");
        rollTable.put(17, "Stairs, (passage continues straight for 10 ft)");
        rollTable.put(18, "Dead End");
        rollTable.put(19, "Dead End");
        rollTable.put(20, "Wandering Monster (passage continues straight for 10 ft)");
        rollTable.put(21, "Treasure (passage continues straight for 10ft)");
    }
}
