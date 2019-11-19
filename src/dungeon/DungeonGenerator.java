package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import dnd.die.D20;

public class DungeonGenerator {
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

    public String getSpaceDescription(int index){
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
            if(s instanceof Chamber) {
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

    // /**
    //  * creates the needed passage and links doors and spaces for the given map index.
    //  * @param mapIndex the index in the map to create
    //  */
    // private void createTCase(int mapIndex) {
    //     ArrayList<Door> tempDoorList = associationMap.get(mapIndex);
    //     Door temp = tempDoorList.get(0), newDoor;
    //     ArrayList<Passage> passageList = new ArrayList<Passage>();

    //     for (int i = 0; i < tempDoorList.size(); i++) {
    //         passageList.add(new Passage(false));
    //     }

    //     for (int i = 1; i < tempDoorList.size(); i++) {
    //         passageList.get(i - 1).addDoorSection(temp, 1);
    //         newDoor = new Door(PASSAGE, 1);
    //         passageList.get(i - 1).addDoorSection(newDoor, D20.d20() % 2 + 7);
    //     }

    // }

    // /**
    //  * creates the needed passage and links doors and spaces for given map index.
    //  * @param mapIndex the index in the associationMap
    //  */
    // private void createSimpleCase(int mapIndex) {
    //     ArrayList<Door> tempList = associationMap.get(mapIndex);
    //     Passage passage1 = new Passage(false), passage2 = new Passage(false);
    //     passage1.addSection(0);
    //     passage1.addSection(3);
    // }

    /**
     * creates the dungeon given the values in the variables after other method calls.
     * need to do a lot more here its not that good
     */
    private void createFinalDungeon() {
        ArrayList<Door> theList;
        Passage thePassage;
        for (int i = 0; i < associationMap.size(); i++) {
            theList = associationMap.get(i);
            thePassage = new Passage(false);
            for (Door d : theList) {
                d.addSpace(thePassage);
                thePassage.addDoorSection(d, 7);
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
