package dungeon;

import java.util.ArrayList;

public abstract class Space {
    /**
    *   the door the space is generated off of.
    **/
    private Door entryDoor;
    /**
    *   the number this space is.
    **/
    private int spaceNum;

    /**
     * sets the entry door with the given door.
     * @param toSet door to set
     */
    public void setEntryDoor(Door toSet) {
        entryDoor = toSet;
    }

    /**
     * resets the given spaces count.
     */
    public abstract void reset();

    /**
     * sets the number that the space is. This is for labelling spaces with numbers. Further implementation is included in child classes.
     * @param toSet the number to ser the spacenum to
     */
    public void setSpaceNum(int toSet) {
        spaceNum = toSet;
    }

    /**
     * gets the spaceNum.
     * @return spaceNum
     */
    public int getSpaceNum() {
        return spaceNum;
    }

    /**
     * abstract method for getting description.
     * @return implemented in children
     */
    public abstract String getDescription();

    /**
     * gets the doors in the space.
     * @return ArrayList
     */
    public abstract ArrayList getDoors();

    /**
     * adds a door to the chamber.
     * @param theDoor the door to add
     */
    public abstract void addDoor(Door theDoor);
}
