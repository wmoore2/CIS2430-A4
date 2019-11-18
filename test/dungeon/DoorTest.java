package dungeon;

import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.D20;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class DoorTest {
    private Door theDoor;
    private Chamber theChamber;
    private Passage thePassage;
    private Trap theTrap;
    private boolean expResult;
    private ArrayList<Space> spaceList;
    private static final boolean CHAMBER = true;
    private static final boolean PASSAGE = false;
    private static final int ARCHWAY = 1;
    private static final int LOCKED = 2;
    private static final int TRAPPED = 3;

    public DoorTest() {

    }
    
   
    
    /* set up similar to the sample in PassageTest.java */
    @Before
    public void setup(){
        //set up any instance variables here so that they have fresh values for every test
        theDoor = new Door(CHAMBER);
        spaceList = new ArrayList<Space>();
        theTrap = new Trap();
        theChamber = new Chamber();
        theChamber.buildRandomChamber();
        thePassage = new Passage(false);
        thePassage.buildRandomPassage();
        spaceList.add(thePassage);
        spaceList.add(theChamber);
    }  


    @Test
    public void getConnectTypeTest()
    {
        System.out.println("getConnectType");
        expResult = CHAMBER;
        assertEquals(expResult, theDoor.getConnectType());
    }

    @Test
    public void setConnectTypeTest()
    {
        System.out.println("setConnectType");
        expResult = PASSAGE;
        theDoor.setConnectType(PASSAGE);
        assertEquals(expResult, theDoor.getConnectType());
    }

    @Test
    public void setLockedTest()
    {
        System.out.println("setLocked");
        theDoor = new Door(CHAMBER, ARCHWAY);
        theDoor.setLocked(true);
        expResult = false;
        assertEquals(expResult, theDoor.isLocked());
    }

    @Test
    public void setTrappedTest()
    {
        System.out.println("setTrapped");
        int trapRoll = D20.d20();
        theTrap.setDescription(trapRoll);
        theDoor.setTrapped(true, trapRoll);
        assertTrue(theDoor.getDescription().contains(theTrap.getDescription()));
    }

    @Test
    public void setOpenTest()
    {
        System.out.println("setOpen"); 
        theDoor.setOpen(true);
        expResult = true;
        assertEquals(expResult, theDoor.isOpen());
    }

    @Test
    public void setArchwayTest()
    {
        System.out.println("setArchway");
        theDoor.setArchway(true);
        expResult = true;
        assertEquals(expResult, theDoor.isArchway());
    }

    @Test
    public void isLockedTest()
    {
        System.out.println("isLocked");
        theDoor = new Door(CHAMBER, ARCHWAY);
        expResult = false;
        assertEquals(expResult, theDoor.isLocked());
    }

    @Test
    public void isOpenTest()
    {
        System.out.println("isOpen");
        theDoor = new Door(CHAMBER, ARCHWAY);
        expResult = true;
        assertEquals(expResult, theDoor.isOpen());
    }

    @Test
    public void isTrappedTest()
    {
        System.out.println("isTrapped");
        theDoor = new Door(CHAMBER);
        theDoor.setTrapped(true);
        expResult = true;
        assertEquals(expResult, theDoor.isTrapped());
    }

    @Test
    public void isArchwayTest()
    {
        System.out.println("isArchway");
        theDoor = new Door(CHAMBER, ARCHWAY);
        expResult = true;
        assertEquals(expResult, theDoor.isArchway());
    }

    @Test
    public void getSpacesTest()
    {
        System.out.println("getSpaces");
        theDoor.setSpaces(thePassage, theChamber);
        assertTrue(spaceList.equals(theDoor.getSpaces()));
    }

    @Test
    public void getSpaceNumIndexTest()
    {
        System.out.println("getSpaceNumIndex");
        theDoor.setSpaces(thePassage, theChamber);
        int result = theDoor.getSpaceNumIndex(1);
        assertEquals(result, theChamber.getSpaceNum() + 1);
    }

    @Test
    public void setSpaceOneTest()
    {
        System.out.println("setSpaceOne");
        theDoor.setSpaces(theChamber, theChamber);
        theDoor.setSpaceOne(thePassage);
        assertEquals(thePassage, theDoor.getSpaces().get(0));
    }

    @Test
    public void setSpaceTwoTest()
    {
        System.out.println("setSpaceTwo");
        theDoor.setSpaces(theChamber, theChamber);
        theDoor.setSpaceTwo(thePassage);
        assertEquals(thePassage, theDoor.getSpaces().get(1));
    }

    @Test
    public void addSpaceTest()
    {
        System.out.println("addSpace");
        int result = 3;
        theDoor.setSpaces(theChamber, thePassage);
        theDoor.addSpace(theChamber);
        assertEquals(result, theDoor.getSpaces().size());
    }

    @Test
    public void setSpacesTest()
    {
        System.out.println("setSpaces");
        int result = 2;
        theDoor.setSpaces(theChamber, thePassage);
        assertEquals(result, theDoor.getSpaces().size());
    }

    @Test
    public void getDescriptionTest()
    {
        System.out.println("getDescription");
        theDoor = new Door(CHAMBER, ARCHWAY);
        theDoor.setSpaces(theChamber, thePassage);
        assertTrue(theDoor.getDescription().contains("Archway"));
    }
}
