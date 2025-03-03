package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import database.DBMonster;
import database.DBConnection;
import dnd.models.Treasure;
import dnd.die.D20;
import dnd.die.Percentile;

public class DungeonGenerator implements java.io.Serializable {
    /**
     * Stores the chambers being used in the dungeon.
     */
    private static final boolean PASSAGE = false;
    /**
     * the list of chambers.
     */
    private ArrayList<Chamber> chamberList;
    /**
     * the association map.
     */
    private HashMap<Integer, ArrayList<Door>> associationMap;
    /**
     * the set of chambers.
     */
    private HashSet<Chamber> chamberSet;
    /**
     * the list of doors.
     */
    private ArrayList<Door> doorList;
    /**
     * the list of passages.
     */
    private ArrayList<Passage> thePassageList;
    /**
     * hashmap of doors to the chambers they connect to.
     */
    private HashMap<Door, ArrayList<Chamber>> doorMap;
    /**
     * idk.
     */
    private HashSet<Treasure> treasureList;

    /**
     * constructor for the class.
     */
    public DungeonGenerator() {
        initDungeon();
    }

    /**
     * gets all the spaces in the dungeon.
     * @return the list of all spaces(passages and chambers)
     */
    public ArrayList<Space> getSpaces() {
        ArrayList<Space> toReturn = new ArrayList<Space>();
        toReturn.addAll(chamberList);
        toReturn.addAll(thePassageList);
        return toReturn;
    }

    /**
     * resets the space numbering for when a new dungeon needs to be generated.
     */
    public void resetSpaces() {
        for (Space s : getSpaces()) {
            s.reset();
        }
        initDungeon();
    }

    /**
     * gets list.
     */
    private void generateTreasureList() {
        treasureList = new HashSet<Treasure>();
        Treasure temp;
        for (int i = 0; i < 100; i++) {
            temp = new Treasure();
            temp.chooseTreasure(Percentile.percentile());
            temp.setContainer(D20.d20());
            treasureList.add(temp);
        }
    }

    /**
     * does stuff.
     * @return idk
     */
    public ArrayList<String> getPossibleTreasure() {
        ArrayList<String> toReturn = new ArrayList<String>();
        ArrayList<Treasure> temp = new ArrayList<Treasure>(treasureList);
        for (Treasure t : temp) {
            toReturn.add(t.getDescription());
        }
        return toReturn;
    }

    /**
     * does things.
     * @param  index param
     * @return       return
     */
    public ArrayList<String> getTreasureListFromSpace(Integer index) {
        ArrayList<String> toReturn = new ArrayList<String>();
        if (index == -1) {
            return toReturn;
        }
        for (Treasure t : getSpaces().get(index).getTreasureList()) {
            toReturn.add(t.getDescription());
        }
        return toReturn;
    }

    /**
     * yeet.
     * @param  index this assignment
     * @return       idk
     */
    public ArrayList<String> getMonsterListFromSpace(Integer index) {
        ArrayList<String> toReturn = new ArrayList<String>();
        if (index == -1) {
            return toReturn;
        }
        for (DBMonster m : getSpaces().get(index).getMonsters()) {
            toReturn.add(m.getName() + ": " + m.getDescription());
        }
        return toReturn;
    }

    /**
     * gonna do bad.
     * @param  index cuz
     * @return       these comments are good
     */
    public String getMonsterFromDatabase(Integer index) {
        DBConnection db = new DBConnection();
        DBMonster temp = new DBMonster();
        return temp.stringToMonster(db.getAllMonsters().get(index)).getName();
    }

    /**
     * yup does stuff.
     * @return the return val
     */
    public ArrayList<String> getDatabaseMonsterList() {
        ArrayList<String> toReturn = new ArrayList<String>();
        ArrayList<DBMonster> temp = new ArrayList<DBMonster>();
        DBConnection db = new DBConnection();
        DBMonster tempMonster;

        for (String s : db.getAllMonsters()) {
            tempMonster = new DBMonster();
            temp.add(tempMonster.stringToMonster(s));
        }

        for (DBMonster m : temp) {
            toReturn.add(m.getName() + ": " + m.getDescription());
        }
        return toReturn;
    }

    /**
     * read the title yo.
     * @param itemIndex  the index
     * @param spaceIndex other index
     */
    public void addMonsterToSpace(Integer itemIndex, Integer spaceIndex) {
        DBConnection db = new DBConnection();
        DBMonster tempMonster;
        ArrayList<DBMonster> tempList = new ArrayList<DBMonster>();

        for (String s : db.getAllMonsters()) {
            tempMonster = new DBMonster();
            tempList.add(tempMonster.stringToMonster(s));
        }


        DBMonster temp = tempList.get(itemIndex);
        getSpaces().get(spaceIndex).addMonster(temp);
    }

    /**
     * see somewhere else for details.
     * @param itemIndex  y
     * @param spaceIndex n
     */
    public void removeMonsterFromSpace(Integer itemIndex, Integer spaceIndex) {
        DBMonster temp = getSpaces().get(spaceIndex).getMonsters().get(itemIndex);
        getSpaces().get(spaceIndex).removeMonster(temp);
    }

    /**
     * at least checkstyle is fine with this.
     * @param itemIndex  idk
     * @param spaceIndex yes
     */
    public void addTreasureToSpace(Integer itemIndex, Integer spaceIndex) {
        ArrayList<Treasure> tempList = new ArrayList<Treasure>(treasureList);
        Treasure temp = tempList.get(itemIndex);
        getSpaces().get(spaceIndex).addTreasure(temp);
    }

    /**
     * yupyupyup.
     * @param itemIndex  yes
     * @param spaceIndex yes
     */
    public void removeTreasureFromSpace(Integer itemIndex, Integer spaceIndex) {
        Treasure temp = getSpaces().get(spaceIndex).getTreasureList().get(itemIndex);
        getSpaces().get(spaceIndex).removeTreasure(temp);
    }

    /**
     * gets the description of a given door from a given space.
     * @param  spaceIndex the index of the spaces
     * @param  doorIndex  the index of the door
     * @return the string containing the description.
     */
    public String getDoorDescriptionFromSpace(Integer spaceIndex, Integer doorIndex) {
        Door temp = (Door) getSpaces().get(spaceIndex).getDoors().get(doorIndex);
        String toReturn = "Door " + (doorIndex + 1) + "\n";
        toReturn = toReturn.concat(temp.getDescription());
        if (temp.getSpaces().get(1) instanceof Chamber) {
            toReturn = toReturn.concat("Connected to Chamber " + temp.getSpaceNumIndex(1));
        } else if (temp.getSpaces().get(1) instanceof Passage) {
            toReturn = toReturn.concat("Connected to Passage " + temp.getSpaceNumIndex(1));
        }
        return toReturn;
    }

    /**
     * gets the list of doors from the given space in string format.
     * @param  index the index of the space to get
     * @return       the arraylist of doors in string form
     */
    public ArrayList<String> getDoorsFromSpace(Integer index) {
        ArrayList<Space> temp = getSpaces();
        ArrayList<String> toReturn = new ArrayList<String>();
        if (index > temp.size()) {
            toReturn.add("out of bounds");
            return toReturn;
        } else {
            for (int i = 0; i < temp.get(index).getDoors().size(); i++) {
                toReturn.add("Door " + (i + 1));
            }
        }
        return toReturn;
    }

    /**
     * gets the description of the given space.
     * @param  index the index to get the description of
     * @return       the string
     */
    public String getSpaceDescription(int index) {
        ArrayList<Space> temp = getSpaces();
        if (index > temp.size()) {
            return "out of bounds";
        } else {
            return temp.get(index).getDescription();
        }
    }

    /**
     * gets the list of all the names of all spaces.
     * @return the arraylist of names of spaces.
     */
    public ArrayList<String> getSpaceNames() {
        ArrayList<String> toReturn = new ArrayList<String>();
        for (Space s : getSpaces()) {
            if (s instanceof Chamber) {
                toReturn.add("Chamber " + (s.getSpaceNum() + 1));
            } else if (s instanceof Passage) {
                toReturn.add("Passage " + (s.getSpaceNum() + 1));
            }
        }
        return toReturn;
    }

    /**
     * creates the map between the doors and the list of chambers associated with them.
     */
    public void createDoorMap() {
        ArrayList<Chamber> tempList = new ArrayList<Chamber>();
        for (int i = 0; i < associationMap.size(); i++) {
            for (Door d : associationMap.get(i)) {
                for (Door dr : associationMap.get(i)) {
                    if (d != dr) {
                        tempList.add(chamberList.get(dr.getSpaceNumIndex(0) - 1));
                    }
                }
                doorMap.put(d, tempList);
                tempList = new ArrayList<Chamber>();
            }
        }
    }

    /**
     * creates the dungeon given the values in the variables after other method calls.
     * need to do a lot more here its not that good
     */
    private void createFinalDungeon() {
        ArrayList<Door> theList;
        Door temp = new Door(false, 0);
        Passage thePassage;
        for (int i = 0; i < associationMap.size(); i++) {
            theList = associationMap.get(i);
            thePassage = new Passage(false);
            for (Door d : theList) {
                temp = new Door(false, 0);
                try {
                    temp = (Door) d.clone();
                } catch (CloneNotSupportedException e) {
                    System.out.println("Clone not supported.");
                }
                temp.reverseDoor();
                d.addSpace(thePassage);
                temp.addSpace(thePassage);
                if (temp.getDescription().toLowerCase().contains("archway")) {
                    if (thePassage.getNumSections() == 0) {
                        thePassage.addDoorSection(temp, 15);
                    } else {
                        thePassage.addDoorSection(temp, 7);
                    }
                } else {
                    if (thePassage.getNumSections() == 0) {
                        thePassage.addDoorSection(temp, 3);
                    } else {
                        temp.makeArchway();
                        thePassage.addDoorSection(temp, 7);
                    }
                }
            }
            thePassageList.add(thePassage);
            // if (theList.size() > 2) {
            //     //case where we have t intersections
            //     createTCase(i);
            // } else if (theList.size() == 2) {
            //     //simple case of 2
            //     createSimpleCase(i);
            // } else {
            //     //ya idk how this would happen. be afraid
            //     System.out.println("Something went wrong");
            // }
        }
    }

    /**
     * maps remaining doors to one another in each chamber.
     * at this point we have each chamber connected in some way
     * its time to tie up the remaining loose ends.
     */
    private void mapRemainingDoors() {
        Door targetDoor;
        Door currDoor;
        while (!doorList.isEmpty()) {
            currDoor = getRandomDoor();
            do {
                targetDoor = getRandomDoor();
            } while (targetDoor.getSpaceNumIndex(0) == currDoor.getSpaceNumIndex(0) && isPossibleMatch());
            //here we have only doors left in one chamber and need to put them into pre-existing associations
            if (!isPossibleMatch()) {
                while (!doorList.isEmpty()) {
                    addDoorToAssociation(getRandomDoor(), getRandomAssociationIndex());
                }
                break;
            }
            associationMap.put(associationMap.size(), createNewAssociation(currDoor, targetDoor));
        }
    }

    /**
     * checks to see if there is a possible match between doors in the doorList.
     * @return the boolean representation of if a match can be made between two new doors.
     */
    private boolean isPossibleMatch() {
        boolean[] chamberHasDoors = new boolean[5];
        boolean flag = false;
        for (Door d : doorList) {
            chamberHasDoors[d.getSpaceNumIndex(0) - 1] = true;
        }
        for (boolean b : chamberHasDoors) {
            if (flag && b) {
                return true;
            } else if (b) {
                flag = b;
            }
        }
        return false;
    }

    /**
     * Gets the index of a random association to tack a straggling door onto.
     * @return the index of the association
     */
    private int getRandomAssociationIndex() {
        return D20.d20() % associationMap.size();
    }

    /**
     * gets a random door from the doorList.
     * @return the random door
     */
    private Door getRandomDoor() {
        return doorList.get(D20.d20() % doorList.size());
    }

    /**
     * associates the chambers through doors such that each chamber is accessible.
     */
    private void associateDoors() {
        Chamber currChamber = null;
        Door currDoor;
        Door targetDoor;
        int counter = 0;
        //gets list of all doors in chamber
        getDoors();
        getChamberSet();
        currChamber = getRandomChamber();
        while (!chamberSet.isEmpty()) {
            //choose door from chamber
            currDoor = chooseDoor(currChamber.getDoors());

            //get a new chamber
            currChamber = getRandomChamber();

            //choose door from chamber
            targetDoor = chooseDoor(currChamber.getDoors());

            if (isDoorAssociated(currDoor)) {
                addDoorToAssociation(targetDoor, getAssociationIndex(currDoor));
            } else {
                //associate the two
                associationMap.put(counter, createNewAssociation(currDoor, targetDoor));
                counter++;
            }

            System.out.println(associationMap.size() + " " + counter);
        }
    }

    /**
     * gets the index of the association that the door is associated in.
     * @param  theDoor the door to look for
     * @return         the index of the association the door is contained in
     */
    private int getAssociationIndex(Door theDoor) {
        for (int i = 0; i < associationMap.size(); i++) {
            for (Door d : associationMap.get(i)) {
                if (theDoor == d) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * adds the given door to the association at the given index.
     * @param theDoor    the door to add
     * @param assocIndex the index to add the door to
     */
    private void addDoorToAssociation(Door theDoor, int assocIndex) {
        ArrayList<Door> temp = associationMap.get(assocIndex);
        temp.add(theDoor);
        doorList.remove(theDoor);
        associationMap.put(assocIndex, temp);
    }

    /**
     * checks to see if the given door is already included in an association.
     * @param  theDoor the door to check
     * @return         the boolean value of wether or not the door is associated.
     */
    private boolean isDoorAssociated(Door theDoor) {
        for (int i = 0; i < associationMap.size(); i++) {
            if (associationMap.get(i).contains(theDoor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets a random chamber form the list of chambers.
     * @return the chamber
     */
    private Chamber getRandomChamber() {
        Chamber currChamber = null;
        while (!chamberSet.contains(currChamber)) {
            currChamber = chamberList.get(D20.d20() % chamberList.size());
        }
        chamberSet.remove(currChamber);
        return currChamber;
    }

    /**
     * creates a new association between any number of doors.
     * @param  theDoors the doors to create an association between
     * @return          the list of all the doors given
     */
    private ArrayList<Door> createNewAssociation(Door...theDoors) {
        ArrayList<Door> toReturn = new ArrayList<Door>();
        for (Door d : theDoors) {
            toReturn.add(d);
            doorList.remove(d);
        }
        return toReturn;
    }

    /**
     * gets a rnaomd door from given list.
     * @param  theList the list to choose from
     * @return         the door chosen
     */
    private Door chooseDoor(ArrayList<Door> theList) {
        return theList.get(D20.d20() % theList.size());
    }

    /**
     * gets the list of all the doors in the dungeon.
     */
    private void getDoors() {
        for (Chamber c: chamberList) {
            for (Door d : c.getDoors()) {
                doorList.add(d);
            }
        }
    }


    /**
     * gets the set of chambers in the dungeon.
     */
    private void getChamberSet() {
        chamberSet.addAll(chamberList);
    }


    /**
     * initializes all instance variables.
     */
    private void initDungeon() {
        chamberList = new ArrayList<Chamber>();
        chamberSet = new HashSet<Chamber>();
        doorMap = new HashMap<Door, ArrayList<Chamber>>();
        associationMap = new HashMap<Integer, ArrayList<Door>>();
        doorList = new ArrayList<Door>();
        thePassageList = new ArrayList<Passage>();
        generateTreasureList();
    }

    /**
     * creates the chambers in the dungeon and stores them in chamberlist.
     */
    private void createChambers() {
        Chamber temp;
        for (int i = 0; i < 5; i++) {
            temp = new Chamber();
            temp.buildRandomChamber();
            chamberList.add(temp);
        }
    }

    /**
     * gets the number of doors in the dungeon based off of the number of doors in each chamber in chamberList.
     * @return the number of doors in the dungeon.
     */
    private int getNumDoors() {
        int returnVal = 0;
        for (Chamber c : chamberList) {
            returnVal += c.getDoors().size();
        }
        return returnVal;
    }

    /**
     * builds a completely random dungeon.
     */
    public void buildDungeon() {
        createChambers();
        associateDoors();
        mapRemainingDoors();
        createDoorMap();
        createFinalDungeon();
    }

    /**
     * gets the description of the dungeon.
     * @return string containing the description of the dungeon
     */
    public String getDescription() {
        String toReturn = new String("");
        for (Chamber c : chamberList) {
            toReturn = toReturn.concat(c.getDescription());
        }
        toReturn = toReturn.concat("\n");
        for (Passage p : thePassageList) {
            toReturn = toReturn.concat("Passage " + (p.getSpaceNum() + 1) + " connects chambers: ");
            for (Door d : p.getDoors()) {
                toReturn = toReturn.concat(d.getSpaceNumIndex(0) + "  ");
            }
            toReturn = toReturn.concat("\n");
        }
        return toReturn;
    }
}
