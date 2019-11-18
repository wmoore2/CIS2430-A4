// package dungeon;

// import java.util.ArrayList;

// public class LevelBuilder {
//     /**
//     *   Constant boolean for which boolean value is the chamber.
//     **/
//     private static final boolean CHAMBER = true;
//     /**
//     *   Constant boolean for which boolean value is a passage.
//     **/
//     private static final boolean PASSAGE = false;
//     /**
//     *   Boolean storing if  number of chambers has been reached.
//     **/
//     private boolean isEnd = false;
//     /**
//     *   Arraylist storing all spaces in dungeon.
//     **/
//     private ArrayList<Space> spaceList;
//     /**
//     *   Stores the number of chambers generated.
//     **/
//     private int chamberCount;
//     /**
//     *   Stores the max number of chambers.
//     **/
//     private int numChamber;
//     /**
//     *   String storing the description of the chamber.
//     **/
//     private String dungeonDesc;

//     /**
//     *   Constructor for the level.
//     *   @param chamberNum number of chamebrs to set.
//     **/
//     public LevelBuilder(int...chamberNum) {
//         setNumChamber(chamberNum);
//         initLevel();
//     }

//     /**
//     *   Initializes the level.
//     **/
//     private void initLevel() {
//         spaceList = new ArrayList<Space>();
//         dungeonDesc = new String();
//         isEnd = false;
//         chamberCount = 0;
//     }

//     /**
//     *   Builds an entire level.
//     **/
//     public void buildLevel() {
//         Space firstSpace;
//         while (getChamberCount() < getNumChamber()) {
//             resetSpaces();
//             firstSpace = genFirstSpace();
//             recursiveBuild(firstSpace);
//         }
//     }

//     /**
//      * resets the count inside of chamber and passage class.
//      */
//     private void resetSpaces() {
//         for (Space s: spaceList) {
//             s.reset();
//         }
//         chamberCount = 0;
//         spaceList.clear();
//     }

//     /**
//     *   Builds spaces branching off of the given space until maximum number of chambers is hit.
//     *   @param theSpace the space to build
//     **/
//     private void recursiveBuild(Space theSpace) {
//         ArrayList<Door> doors = theSpace.getDoors();
//         Space space;
//         if (doors != null) {
//             if (getChamberCount() >= getNumChamber()) {
//                 isEnd = true;
//             }
//             for (Door d: doors)  {
//                 space = generateNewSpace(theSpace, d);
//                 recursiveBuild(space);
//             }
//         }
//     }

//     /**
//     *   Generates the first passage.
//     *   @return the first space
//     **/
//     private Space genFirstSpace() {
//         Passage firstPassage = new Passage(false);
//         firstPassage.buildRandomPassage();
//         while (firstPassage.getDoors().size() == 0) {
//             firstPassage.buildRandomPassage();
//         }
//         addSpace(firstPassage);
//         return firstPassage;
//     }

//     /**
//     *   Generates a new space.
//     *   @param toAdd the space to add a space off of
//     *   @param theDoor the door to connect the new space to
//     *   @return the space that was generated
//     **/
//     private Space generateNewSpace(Space toAdd, Door theDoor) {
//         Space newSpace;
//         if (toAdd instanceof Chamber) {
//             /*if  the space to add a new space off of is a chamber*/
//             if (isEnd) {
//                 newSpace = generateEndPassage(theDoor);
//             } else  {
//                 newSpace = generateRandomPassage(theDoor);
//             }
//         } else  {
//             /*if  the space to add a new space off of is a passage*/
//             if (theDoor.getConnectType() == CHAMBER) {
//                 //generates straggling chambers
//                 newSpace = generateRandomChamber(theDoor);
//             } else  {
//                 //generates dead ends if  max number of chamber reached
//                 if (isEnd) {
//                     newSpace = generateEndPassage(theDoor);
//                 } else  {
//                     newSpace = generateRandomPassage(theDoor);
//                 }
//             }
//         }
//         return newSpace;
//     }

//     /**
//     *   Generates a new space.
//     *   @param linker door to build a chamber off of
//     *   @return the generated space
//     **/
//     private Space generateRandomChamber(Door linker) {
//         Chamber newChamber = addRandomChamber();
//         setUpSpace(linker, newChamber);
//         return newChamber;
//     }

//     /**
//     *   adds a new random Chamber to the spaceList.
//     *   @return the new Chamber
//     **/
//     private Chamber addRandomChamber() {
//         Chamber temp = new Chamber();
//         temp.buildRandomChamber();
//         addSpace(temp);
//         chamberCount++;
//         return temp;
//     }

//     /**
//     *   Generates a passage that ends as soon as it begins.
//     *   @param linker the door to generate the passage off of
//     *   @return the space it generated
//     **/
//     private Space generateEndPassage(Door linker) {
//         Passage endPassage = new Passage(true);
//         addSpace(endPassage);
//         setUpSpace(linker, endPassage);
//         return endPassage;
//     }

//     /**
//     *   Generates a random passage.
//     *   @param linker the door to generate off of
//     *   @return the generated space
//     **/
//     private Space generateRandomPassage(Door linker) {
//         Passage newPassage = addRandomPassage();
//         setUpSpace(linker, newPassage);
//         return newPassage;
//     }

//     /**
//     *   Sets up the space.
//     *   @param linker the door to set the space up with
//     *   @param toSet the space to setup
//     **/
//     private void setUpSpace(Door linker, Space toSet) {
//         toSet.setEntryDoor(linker);
//         linker.setSpaceTwo(toSet);
//     }

//     /**
//     *   adds a random passage.
//     *   @return the generated passage
//     **/
//     private Passage addRandomPassage() {
//         Passage temp = new Passage(false);
//         temp.buildRandomPassage();
//         addSpace(temp);
//         return temp;
//     }

//     /**
//     *   adds a space to the spaceList.
//     *   @param toAdd the space to add
//     **/
//     private void addSpace(Space toAdd) {
//         spaceList.add(toAdd);
//     }

//     /**
//     *   sets the number of chambers.
//     *   @param toSet integer representing the new number of chambers
//     **/
//     private void setNumChamber(int...toSet) {
//         if (toSet.length != 0)  {
//             numChamber = toSet[0];
//         } else  {
//             numChamber = 5;
//         }
//     }

//     /**
//     *   Returns the number of chambers.
//     *   @return the number of chambers max
//     **/
//     public int getNumChamber() {
//         return numChamber;
//     }

//     /**
//     *   Returns the number of chambers currently in the level.
//     *   @return the number of chambers in the level
//     **/
//     public int getChamberCount() {
//         return chamberCount;
//     }

//     /**
//     *   Returns the description of the chaber after generating it.
//     *   @return the description
//     **/
//     private String genDescription() {
//         String toReturn = new String();
//         for (Space s: spaceList)  {
//             toReturn = toReturn.concat(s.getDescription());
//         }
//         return toReturn;
//     }

//     /**
//     *   Generates the description.
//     **/
//     private void setDescription() {
//         dungeonDesc = genDescription();
//     }

//     /**
//     *   returns the description.
//     *   @return the description
//     **/
//     public String getDescription() {
//         setDescription();
//         return dungeonDesc;
//     }
// }
