package dungeon;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Exit;
import dnd.die.D20;
import dnd.exceptions.UnusualShapeException;
import dnd.exceptions.NotProtectedException;
import java.util.ArrayList;

public class Chamber extends Space implements java.io.Serializable{
    /**
     * roll for nothing.
     */
    private static final int NOTHING_ROLL = 1;
    /***
    * Roll for monster in the chamber.
    ***/
    private static final int MONSTER_ROLL = 13;
    /***
    * Roll for monster and treasure in the chamber.
    ***/
    private static final int MONSTER_TREASURE_ROLL = 15;
    /***
    * Roll for special in the chamber.
    ***/
    private static final int SPECIAL_ROLL = 18;
    /***
    * Roll for trap in the chamber.
    ***/
    private static final int TRAP_ROLL = 19;
    /***
    * Roll for treasure in the chamber.
    ***/
    private static final int TREASURE_ROLL = 20;
    /**
    *   boolean for storing the constant for chamber.
    **/
    private static final boolean CHAMBER = true;
    /**
    *   boolean for storing the constant for passage.
    **/
    private static final boolean PASSAGE = false;
    /**
    *   stores the total number of chambers.
    **/
    private static int numChamber = 0;
    /**
    *   stores the contents of the chamber.
    **/
    private ChamberContents myContents;
    /**
    *   stores the chambershape.
    **/
    private ChamberShape mySize;
    /**
    *   stores the chamber roll.
    **/
    private int chamberRoll;
    /**
    *   list of all doors in chamber.
    **/
    private ArrayList<Door> doorList;
    /**
    *   list of monsters in chamber.
    **/
    private ArrayList<Monster> monsterList;
    /**
    *   list of stairs in chamber.
    **/
    private ArrayList<Stairs> stairList;
    /**
    *   list of traps in chamber.
    **/
    private ArrayList<Trap> trapList;
    /**
    *   list of treasure in chamber.
    **/
    private ArrayList<Treasure> treasureList;

    /**
    *   constructor with no arguments.
    **/
    public Chamber() {
        ChamberShape theShape = ChamberShape.selectChamberShape(D20.d20());
        ChamberContents theContents = new ChamberContents();
        theShape.setNumExits();
        theContents.setDescription(D20.d20());
        initChamber();
        setShape(theShape);
        setContents(theContents);
        setChamberNum();
    }

    /**
    *   builds a random chamber.
    *   @return the random chamber
    **/
    public Chamber buildRandomChamber() {
        initChamber();
        findRoll();
        createContents();
        createDoors();
        return this;
    }

    /**
    *   sets this specific chambers number in the level.
    **/
    private void setChamberNum() {
        setSpaceNum(numChamber);
        numChamber++;
    }

    /**
    *   Clears the doorList.
    **/
    private void refreshDoors() {
        doorList.clear();
    }

    /**
     * resets the number of chambers.
     */
    @Override
    public void reset() {
        numChamber = 0;
    }

    /**
    *   creates doors from exits given in chamberShape.
    **/
    private void createDoors() {
        setProperNumExits();
        ArrayList<Exit> theList = mySize.getExits();
        for (Exit e: theList)  {
            Door d = new Door(PASSAGE);
            d.setSpaces(this, null);
        }
    }

    /**
    *   creates the contents from the given roll.
    **/
    private void createContents() {
        switch (getRoll()) {
            case NOTHING_ROLL:
                //nothing
                break;
            case MONSTER_ROLL:
                addRandomMonster();
                break;
            case MONSTER_TREASURE_ROLL:
                addRandomMonster();
                addRandomTreasure();
                break;
            case SPECIAL_ROLL:
                addRandomStair();
                break;
            case TRAP_ROLL:
                addRandomTrap();
                break;
            case TREASURE_ROLL:
                addRandomTreasure();
                break;
            default:
                break;
        }
    }

    /**
    *   sets the roll based on the given string in the constructor.
    **/
    private void findRoll() {
        if (equateRoll(NOTHING_ROLL, myContents))  {
            /*No contents*/
            setRoll(NOTHING_ROLL);
        } else if  (equateRoll(MONSTER_ROLL, myContents))  {
            /*Monster only*/
            setRoll(MONSTER_ROLL);
        } else if  (equateRoll(MONSTER_TREASURE_ROLL, myContents))  {
            /*Monster and Treasure*/
            setRoll(MONSTER_TREASURE_ROLL);
        } else if  (equateRoll(SPECIAL_ROLL, myContents))  {
            /*Special/Stairs*/
            //for stairs in the future
            setRoll(SPECIAL_ROLL);
        } else if  (equateRoll(TRAP_ROLL, myContents))  {
            /*Trap*/
            //for a trap in the future
            setRoll(TRAP_ROLL);
        } else if  (equateRoll(TREASURE_ROLL, myContents))  {
            /*Treasure*/
            setRoll(TREASURE_ROLL);
        }
    }

    /**
    *   Checks equality of roll with another chambercontents object.
    * @param roll integer value of roll to check
    * @param toCheck object to check
    * @return boolean statting if  they are equal or not
    **/
    private boolean equateRoll(int roll, ChamberContents toCheck)  {
        ChamberContents checkAgainst = new ChamberContents();
        checkAgainst.setDescription(roll);
        return (checkAgainst.getDescription().equals(toCheck.getDescription()));
    }

    /**
    *   sets the chamber shape.
    *   @param theShape the chamber shape to set as the chambers shape
    **/
    private void setShape(ChamberShape theShape) {
        mySize = theShape;
        refreshDoors();
        createDoors();
    }

    /**
    *   sets the contents of the chamber.
    *   @param theContents the contents to set as the chambers contents
    **/
    private void setContents(ChamberContents theContents) {
        myContents = theContents;
    }

    /**
    *   initializes the chamber.
    **/
    private void initChamber() {
        doorList = new ArrayList<Door>();
        monsterList = new ArrayList<Monster>();
        treasureList = new ArrayList<Treasure>();
        stairList = new ArrayList<Stairs>();
        trapList = new ArrayList<Trap>();
    }

    /**
    *   sets the correct number of exits. Each chamber needs at least 1 exit.
    **/
    private void setProperNumExits() {
        if (mySize.getExits().size() == 0) {
            mySize.setNumExits(1);
        }
    }

    /**
    *   sets the roll of the chamber based on the table.
    *   @param toSet the roll to set
    **/
    private void setRoll(int toSet) {
        chamberRoll = toSet;
    }

    /**
    *   gets the roll of the chamber.
    *   @return int the chamberRoll
    **/
    private int getRoll() {
        return chamberRoll;
    }

    /**
    *   gets a list of the doors from the chamber.
    *   @return ArrayList<Door> is returned
    **/
    @Override
    public ArrayList<Door> getDoors() {
        return doorList;
    }

    /**
    *   adds a monster to the chamber.
    *   @param theMonster the monster to add to the chamber
    **/
    public void addMonster(Monster theMonster) {
        monsterList.add(theMonster);
    }

    /**
     * Adds a random monster to the chamber.
     */
    private void addRandomMonster() {
        Monster theMonster = new Monster();
        theMonster.setType(D20.d20());
        addMonster(theMonster);
    }

    /**
    *   gets the list of monsters in the chamber.
    *   @return ArrayList<Monster> the monsters contained in the chamber
    **/
    public ArrayList<Monster> getMonsters() {
        return monsterList;
    }

    /**
    *   adds a trap to the chamber.
    *   @param theTrap the trap to add to the chamber.
    **/
    public void addTrap(Trap theTrap) {
        trapList.add(theTrap);
    }

    /**
     * adds a random trap to the chamber.
     */
    private void addRandomTrap() {
        Trap theTrap = new Trap();
        theTrap.setDescription(D20.d20());
        addTrap(theTrap);
    }

    /**
    *   gets a list of traps in the chamber.
    *   @return the arrayList of traps
    **/
    public ArrayList<Trap> getTraps() {
        return trapList;
    }

    /**
    *   adds the given stairs to the chamber.
    *   @param theStair the stair to add to the chamber
    **/
    public void addStair(Stairs theStair) {
        stairList.add(theStair);
    }

    /**
     * adds a random stair to the chamber.
     */
    private void addRandomStair() {
        Stairs theStair = new Stairs();
        theStair.setType(D20.d20());
        addStair(theStair);
    }

    /**
    *   gets all the stairs in the chamber.
    *   @return ArrayList<Stairs> the list of stairs in the chamber
    **/
    public ArrayList<Stairs> getStairs() {
        return stairList;
    }

    /**
    *   adds the given treasure to the chamber.
    *   @param theTreasure the treasure to add to the chamber.
    **/
    public void addTreasure(Treasure theTreasure) {
        treasureList.add(theTreasure);
    }

    /**
     * Adds a random treasure to the chamber.
     */
    private void addRandomTreasure() {
        Treasure theTreasure = new Treasure();
        theTreasure.chooseTreasure(D20.d20());
        theTreasure.setContainer(D20.d20());
        addTreasure(theTreasure);
    }

    /**
    *   gets the list of treasure in the chamber.
    *   @return ArrayList<Treasure> the list of treasure in the chamber
    **/
    public ArrayList<Treasure> getTreasureList() {
        return treasureList;
    }

    /**
     * gets the basic description of the chamber.
     * @return returns a string containing the description
     */
    private String getBasicDescription() {
        String toReturn = new String("");

        toReturn = toReturn.concat("----------Chamber Info----------\n");
        toReturn = toReturn.concat("Chamber Number: " + (getSpaceNum() + 1) + "\n");
        toReturn = toReturn.concat("Shape: " + mySize.getShape() + "\n");

        return toReturn;
    }

    /**
     * gets the description of the exits.
     * @return description of exits
     */
    private String getExitDescription() {
        ArrayList<Exit> chamberExits = mySize.getExits();
        String toReturn = new String("");
        for (int i = 0; i < chamberExits.size(); i++) {
            Exit e = chamberExits.get(i);
            toReturn = toReturn.concat("   Exit " + (i + 1) + ": " + e.getLocation() + " - " + e.getDirection() + ". " + doorList.get(i).getDescription());
            if (doorList.get(i).getSpaces().get(1) instanceof Passage) {
                toReturn = toReturn.concat("\t\tDoor is connected to: Passage " + doorList.get(i).getSpaceNumIndex(1) + "\n");
            } else {
                toReturn = toReturn.concat("\t\tDoor is connected to: Chamber " + doorList.get(i).getSpaceNumIndex(1) + "\n");
            }
        }
        return toReturn;
    }

    /***
    * gets the relevant chamber shape and size info.
    *   @return the description of the shape
    ***/
    private String getShapeDescription()  {
        String toReturn = new String("");

        try  {
            toReturn = toReturn.concat("Dimensions (L x W): " + mySize.getLength() + "' x " + mySize.getWidth() + "'\n");
        } catch (UnusualShapeException e)  {
            toReturn = toReturn.concat("Special size. Dimensions unavailable.\n");
        }
        toReturn = toReturn.concat("Area: " + mySize.getArea() + " sqft\n");
        return toReturn;
    }

    /**
    *   gets the description of the monster.
    *   @param index the monster to get the description of
    *   @return the description of the monster
    **/
    private String getMonsterDesc(int index) {
        String toReturn = new String("");
        Monster monster = monsterList.get(index);
        toReturn = toReturn.concat("\n   Monster: Between " + monster.getMinNum() + " and " +  monster.getMaxNum() + " " + monster.getDescription());
        return toReturn;
    }

    /**
    *   gets the description of the treasure.
    *   @param index the treasure to get the description of
    *   @return the description of the treasure
    **/
    private String getTreasureDesc(int index) {
        String toReturn = new String("");
        Treasure treasure = treasureList.get(index);
        toReturn = toReturn.concat("\n   Treasure: " + treasure.getDescription() + " contained in " + treasure.getContainer());
        try  {
            toReturn = toReturn.concat(" protected by " + treasure.getProtection());
        } catch (NotProtectedException e)  {
            toReturn = toReturn.concat("unprotected");
        }
        return toReturn;
    }

    /**
    *   gets the description of the trap.
    *   @param index the trap to get the description of
    *   @return the description of the trap
    **/
    private String getTrapDesc(int index) {
        String toReturn = new String("");
        Trap trap = trapList.get(index);
        toReturn = toReturn.concat("\n   Trap: " + trap.getDescription());
        return toReturn;
    }

    /**
    *   gets the description of the stairs.
    *   @param index the stairs to get the description of
    *   @return the description of the stairs
    **/
    private String getStairsDesc(int index) {
        String toReturn = new String("");
        Stairs stair = stairList.get(index);
        toReturn = toReturn.concat("\n   Stairs: " + stair.getDescription());
        return toReturn;
    }

    /**
    *   gets the description of the chamber in general.
    *   @return the description of the rest of the chamber
    **/
    private String getAdditionalDescription() {
        String toReturn = new String("");
        switch (getRoll()) {
            case NOTHING_ROLL:
                if (!getMonsters().isEmpty()) {
                    toReturn = toReturn.concat("Contents:" + getMonsterDesc(0) + "\n");
                }
                break;
            case MONSTER_ROLL:
                toReturn = toReturn.concat("Contents:" + getMonsterDesc(0) + "\n");
                break;
            case MONSTER_TREASURE_ROLL:
                toReturn = toReturn.concat("Contents:" + getMonsterDesc(0) + getTreasureDesc(0) + "\n");
                break;
            case SPECIAL_ROLL:
                toReturn = toReturn.concat("Contents:" + getStairsDesc(0) + "\n");
                if (!getMonsters().isEmpty()) {
                    toReturn = toReturn.concat(getMonsterDesc(0) + "\n");
                }
                break;
            case TRAP_ROLL:
                toReturn = toReturn.concat("Contents:" + getTrapDesc(0) + "\n");
                if (!getMonsters().isEmpty()) {
                    toReturn = toReturn.concat(getMonsterDesc(0) + "\n");
                }
                break;
            case TREASURE_ROLL:
                toReturn = toReturn.concat("Contents:" + getTreasureDesc(0) + "\n");
                if (!getMonsters().isEmpty()) {
                    toReturn = toReturn.concat(getMonsterDesc(0) + "\n");
                }
                break;
            default:
                break;
        }
        return toReturn;
    }

    /**
    *   gets the description of the chamber.
    *   @return the description
    **/
    @Override
    public String getDescription() {
        String toReturn = new String("");
        toReturn = toReturn.concat(getBasicDescription());
        toReturn = toReturn.concat(getShapeDescription());
        toReturn = toReturn.concat(getExitDescription());
        toReturn = toReturn.concat(getAdditionalDescription());
        return toReturn;
    }

    /**
    *   adds the given door to the chamber.
    *   @param newDoor the door to add
    **/
    @Override
    public void addDoor(Door newDoor) {
        doorList.add(0, newDoor);
    }
}
