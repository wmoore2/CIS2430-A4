package dungeon;

import dnd.models.Trap;
import java.util.Collections;
import dnd.die.D20;
import dnd.die.D10;
import dnd.die.D6;
import java.util.ArrayList;

public class Door implements Cloneable, java.io.Serializable {
    /**
     * constant for archway.
     */
    private static final int ARCHWAY = 1;
    /**
     * constant for locked.
     */
    private static final int LOCKED = 2;
    /**
     * constant for trapped.
     */
    private static final int TRAPPED = 3;
    /**
     * boolean constant for chamber.
     */
    private static final boolean CHAMBER = true;
    /**
     * boolan constant for passage.
     */
    private static final boolean PASSAGE = false;
    /**
     * the trap in the door.
     */
    private Trap theTrap;
    /**
     * the list of spaces the door connects.
     */
    private ArrayList<Space> spaceList;
    /**
     * the boolean storing what type of space the door is connected to.
     */
    private boolean connectedTo;
    /**
     * boolean storing if there is a trip.
     */
    private boolean trapBool;
    /**
     * boolean storing the openness of the door.
     */
    private boolean openBool;
    /**
     * boolean for archway.
     */
    private boolean archBool;
    /**
     * boolean for being locked.
     */
    private boolean lockBool;
    /**
     * used with exit constructor.
     */
    private String doorDirection;
    /**
     * used with exit constructor.
     */
    private String doorLocation;

    /**
     * constructor.
     * @param  secondSpace the next type of space to add
     * @param  type  the type of door
     */
    public Door(boolean secondSpace, int...type) {
        //needs to set defaults
        initDoor();
        connectedTo = secondSpace;

        if (type.length != 0) {
            if (type[0] == ARCHWAY) {
                makeArchway();
            }
        }
    }

    /**
     * initializes the door.
     */
    private void initDoor() {
        spaceList = new ArrayList<Space>();
        connectedTo = false;
        setTrapped(false);
        setOpen(false);
        setArchway(false);
        setLocked(false);
        rollDoor();
    }

    /**
     * randomizes the door.
     */
    private void rollDoor() {
        //roll for door characteristics
        if  (D10.d10() == ARCHWAY)  {
            makeArchway();
        } else  {
            //roll for locking
            if  (D6.d6() == LOCKED)  {
                setLocked(true);
            }
            //roll for trapping
            if  (D20.d20() == TRAPPED)  {
                setTrapped(true);
            }
            //roll for being open
            if  (!isLocked())  {
                setOpen((D20.d20() % 2) == 0);
            }
        }
    }

    /**
     * gets the connector type.
     * @return the boolean
     */
    public boolean getConnectType() {
        return connectedTo;
    }

    /**
     * sets what the door connects to.
     * @param type boolean type
     */
    public void setConnectType(boolean type) {
        connectedTo = type;
    }

    /**
     * makes the door an archway.
     */
    public void makeArchway() {
        setArchway(true);
        setOpen(true);
    }

    /**
     * sets the door to be locked.
     * @param flag decides wether to lock or unlock the door
     */
    public void setLocked(boolean flag) {
        setOpen(!flag);
        if (!isOpen()) {
            lockBool = flag;
        }
    }

    /**
     * sets the trap.
     * @param flag       decides wether or not to be trapped
     * @param roll the trap to use
     */
    public void setTrapped(boolean flag, int...roll) {
        // true == trapped.  Trap must be rolled if  no integer is given
        if (roll.length == 1) {
            theTrap = new Trap();
            theTrap.setDescription(roll[0]);
            trapBool = flag;
        } else if  (roll.length == 0)  {
            theTrap = new Trap();
            theTrap.setDescription(D20.d20());
            trapBool = flag;
        }
    }

    /**
     * sets the door to flag.
     * @param flag open or close
     */
    public void setOpen(boolean flag) {
        //true == open
        openBool = (flag || isArchway());
    }

    /**
     * sets the door to be an archway.
     * @param flag true or false for yes or no to archway
     */
    public void setArchway(boolean flag) {
        //true == is archway
        archBool = flag;
        if (flag) {
            setOpen(flag);
            setLocked(!flag);
        }
    }

    /**
     * returns if the door is locked or not.
     * @return a boolean telling if the door is locked
     */
    public boolean isLocked() {
        return lockBool;
    }

    /**
     * returns if trapped.
     * @return wether or not the door is trapped
     */
    public boolean isTrapped() {
        return trapBool;
    }

    /**
     * returns wether or not the door is open.
     * @return returns openbool attribute
     */
    public boolean isOpen() {
        return openBool;
    }

    /**
     * returns if the door is an archway or not.
     * @return archBool class attribute
     */
    public boolean isArchway() {
        return archBool;
    }

    /**
     * gets the description of the trap in the door.
     * @return the string trapDescription
     */
    private String getTrapDescription() {
        if (isTrapped()) {
            return ", and contains a trap: " + theTrap.getDescription() + ".\n";
        } else  {
            return ".\n";
        }
    }

    /**
     * gets the spaces associated by the door.
     * @return arraylist of space
     */
    public ArrayList<Space> getSpaces() {
        //returns the two spaces that are connected by the door
        return spaceList;
    }

    /**
     * clones.
     * @return idk
     * @throws CloneNotSupportedException idk
     */
    public Object clone() throws CloneNotSupportedException {
        Door toReturn = (Door) super.clone();
        toReturn.spaceList = new ArrayList<Space>();
        toReturn.spaceList.addAll(getSpaces());
        toReturn.connectedTo = connectedTo;
        return toReturn;
    }

    /**
     * reverses the door. swaps the incoming space and the outgoing space as well as the connection type.
     */
    public void reverseDoor() {
        Collections.swap(spaceList, 0, 1);
        setConnectType(!getConnectType());
    }

    /**
     * gets the space number of the given index in spaceList.
     * @param  index the index of the space in spaceList
     * @return       the number of that space
     */
    public int getSpaceNumIndex(int index) {
        try {
            if (spaceList.size() >= 2) {
                return (spaceList.get(index).getSpaceNum() + 1);
            }
        } catch (NullPointerException e) {
            return -1;
        }
        return 0;
    }

    /**
     * sets the first space to the given space.
     * @param toSet space to set
     */
    public void setSpaceOne(Space toSet) {
        spaceList.add(0, toSet);
    }

    /**
     * sets the second space to the given space.
     * @param toSet space to set
     */
    public void setSpaceTwo(Space toSet) {
        spaceList.add(1, toSet);
    }

    /**
     * adds the given space to the spaceList.
     * @param toSet the space to add
     */
    public void addSpace(Space toSet) {
        if (spaceList.size() == 2) {
            if (spaceList.get(1) == null) {
                setSpaceTwo(toSet);
                return;
            }
        }
        spaceList.add(toSet);
    }

    /**
     * sets both spaces of the door and adds the door to both of those spaces retroactively.
     * @param spaceOne the first space
     * @param spaceTwo the second space
     */
    public void setSpaces(Space spaceOne, Space spaceTwo) {
        //identif ies the two spaces with the door
        //this method should also call the addDoor method from Space
        addSpace(spaceOne);
        addSpace(spaceTwo);
        if (spaceOne != null) {
            spaceOne.addDoor(this);
        }
        if (spaceTwo != null) {
            spaceTwo.addDoor(this);
        }
    }

    /**
     * gets the description of the door if its open.
     * @return the string containing the description if the door is poen or closed
     */
    private String getOpenDescription() {
        if (isOpen()) {
            return " open";
        } else  {
            return " closed";
        }
    }

    /**
     * gets the description if the door is locked or unlocked.
     * @return the string containing the description
     */
    private String getLockDescritpion() {
        if (isLocked()) {
            return "The door is locked,";
        } else  {
            return "The door is unlocked,";
        }
    }

    /**
     * gets the description if the door is an archway.
     * @return the string containing the description
     */
    private String getArchDescription() {
        return "It is an Archway.\n";
    }

    /**
     * generates the description of the door.
     * @return the description of the door
     */
    private String genDescription() {
        String toReturn = new String();
        if (isArchway()) {
            return getArchDescription();
        }

        toReturn = toReturn.concat(getLockDescritpion());
        toReturn = toReturn.concat(getOpenDescription());
        toReturn = toReturn.concat(getTrapDescription());
        return toReturn;
    }

    /**
     * generates and returns the description.
     * @return the description
     */
    public String getDescription() {
        return genDescription();
    }
}
