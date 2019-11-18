package dungeon;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.die.D20;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class PassageSectionTest {
    PassageSection theSection;
    boolean expResult;
    Monster theMonster;
    Door theDoor;
    /* set up similar to the sample in PassageTest.java */
    public PassageSectionTest()
    {
    }

    @Before
    public void setup(){
        //set up any instance variables here so that they have fresh values for every test
        theMonster = new Monster();
        theDoor = new Door(true);
    }  


    @Test
    public void hasDoorTest()
    {
        System.out.println("hasDoor");
        theSection = new PassageSection(6);
        expResult = true;
        assertEquals(expResult, theSection.hasDoor());
    }


    @Test
    public void isEndTest()
    {

        System.out.println("isEnd");
        theSection = new PassageSection(6);
        expResult = false;
        assertEquals(expResult, theSection.isEnd());
    }


    @Test
    public void addMonsterTest()
    {
        System.out.println("addMonster");
        theMonster.setType(D20.d20());
        theSection = new PassageSection(0);
        theSection.addMonster(theMonster);
        assertEquals(theSection.getMonster(), theMonster);
    }

    @Test
    public void addDoorTest()
    {
        System.out.println("addDoor");
        theSection = new PassageSection(0);
        theSection.addDoor(theDoor);
        assertEquals(theDoor, theSection.getDoor());
    }


    @Test
    public void getRollTest()
    {
        System.out.println("getRoll");
        theSection = new PassageSection(9);
        assertEquals(9, theSection.getRoll());
    }


    @Test
    public void getDoorTest()
    {
        System.out.println("getDoor");
        theSection = new PassageSection(8);
        theSection.addDoor(theDoor);
        assertEquals(theDoor, theSection.getDoor());
    }


    @Test
    public void getMonsterTest()
    {
        System.out.println("getMonster");
        theSection = new PassageSection(20);
        assertTrue(theSection.getMonster() != null);
    }


    @Test
    public void getDescriptionTest()
    {
        System.out.println("getDescription");
        theSection = new PassageSection(9);
        assertTrue(theSection.getDescription().toLowerCase().contains("passage"));
    }
    
}
