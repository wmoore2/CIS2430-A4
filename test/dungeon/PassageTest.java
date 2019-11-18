package dungeon;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;


public class PassageTest {
    //you don't have to use instance variables but it is easier
    // in many cases to have them and use them in each test
    private Passage passageOne;
    private Passage passageTwo;
    private PassageSection sectionOne;
    private PassageSection sectionTwo;
    private int expResult;

    public PassageTest() {
    }

    @Before
    public void setup(){
        //set up any instance variables here so that they have fresh values for every test
        passageOne = new Passage(false);
        passageTwo = new Passage(false);
        sectionOne = new PassageSection(true);
        sectionTwo = new PassageSection(true);
    }  

    @Test
    public void resetTest()
    {
        System.out.println("reset");

        expResult = 0;

        passageOne = new Passage(false);
        passageOne.buildRandomPassage();
        
        passageOne = new Passage(false);
        passageOne.buildRandomPassage();

        passageOne = new Passage(false);
        passageOne.buildRandomPassage();

        passageOne = new Passage(false);
        passageOne.buildRandomPassage();

        passageOne = new Passage(false);
        passageOne.buildRandomPassage();

        passageOne.reset();

        passageOne = new Passage(false);
        passageOne.buildRandomPassage();

        assertEquals(expResult, passageOne.getSpaceNum());   
    }

    @Test
    public void buildRandomPassageTest()
    {
        System.out.println("buildRandomPassage");
        passageOne = new Passage(false);
        passageOne.buildRandomPassage();
        assertTrue(passageOne.getDescription().contains("Passage"));
    }

    @Test
    public void getDoorsTest()
    {

        System.out.println("getDoors");
        expResult = 0;
        passageOne = new Passage(true);
        ArrayList<Door> doorList = passageOne.getDoors();
        assertEquals(expResult, doorList.size());
    }

    @Test
    public void isDeadEndTest()
    {
        System.out.println("isDeadEnd");
        passageOne = new Passage(true);
        assertTrue(passageOne.isDeadEnd());
    }

    @Test
    public void setDeadEndTest()
    {
        System.out.println("setDeadEnd");
        passageOne = new Passage(false);
        passageOne.setDeadEnd(true);
        assertTrue(passageOne.isDeadEnd());
    }

    @Test
    public void getDoorTest()
    {
        System.out.println("getDoor");
        passageOne = new Passage(true);
        assertEquals(null, passageOne.getDoor(0));
    }

    @Test
    public void addMonsterTest()
    {
        System.out.println("addMonster");
        Monster theMonster = new Monster();
        passageOne = new Passage(false);
        passageOne.buildRandomPassage();
        passageOne.addMonster(theMonster, 0);
        assertEquals(theMonster, passageOne.getMonster(0));
    }

    @Test
    public void getMonsterTest()
    {
        System.out.println("getMonster");
        Monster theMonster = new Monster();
        passageOne = new Passage(false);
        passageOne.buildRandomPassage();
        passageOne.addMonster(theMonster, 0);
        assertEquals(theMonster, passageOne.getMonster(0));
    }

    @Test
    public void addDoorTest()
    {
        System.out.println("addDoor");
        Door theDoor = new Door(true);
        passageOne.addDoor(theDoor);
        assertEquals(1, passageOne.getDoors().size());
    }

    @Test
    public void getDescriptionTest()
    {
        System.out.println("getDescription");
        passageOne.buildRandomPassage();
        assertTrue(passageOne.getDescription().contains("Passage"));
    }

    @Test
    public void addSectionTest()
    {
        System.out.println("addSection");
        passageOne = new Passage(false);
        passageOne.addSection(4);
        assertTrue(passageOne.getDoors().size() > 0);
    }
}
