package dungeon;

import java.util.ArrayList;
import dnd.models.Treasure;
import dnd.models.Monster;

public abstract class Space implements java.io.Serializable{
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
     * gets the treasure in the space;
     * @return the list of treasure
     */
    public abstract ArrayList<Treasure> getTreasureList();

    /**
     * gets the monsters in the space.
     * @return           [the monsters in the space]
     */
    public abstract ArrayList<Monster> getMonsters();

    /**
     * adds given treasure to the space.
     * @param theTreasure the treasure to add
     */
    public abstract void addTreasure(Treasure theTreasure);

    /**
     * removes the given treasure from the space.
     * @param theTreasure the treasure to remove.
     */
    public abstract void removeTreasure(Treasure theTreasure);

    /**
     * adds the given monster to the space.
     * @param theMonster the monster to add
     */
    public abstract void addMonster(Monster theMonster);

    /**
     * removes the given monster from the space.
     * @param theMonster the monster to remove.
     */
    public abstract void removeMonster(Monster theMonster);

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
