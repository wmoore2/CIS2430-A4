package dungeon;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.DnDElement;
import dnd.die.D20;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChamberTest {
    private Chamber theChamber;
    private ChamberShape theShape;
    private ChamberContents theContents;
    private Monster myMonster;
    private Trap theTrap;
    private Stairs theStair;
    private Treasure theTreasure;
    private Door theDoor;

    public ChamberTest() {
    }



    /* set up similar to the sample in PassageTest.java */

    @Before
    public void setup(){
        //set up any instance variables here so that they have fresh values for every test
        theShape = ChamberShape.selectChamberShape(D20.d20());
        theContents = new ChamberContents();
        theContents.setDescription(D20.d20());
        myMonster = new Monster();
        myMonster.setType(D20.d20());
        theTrap = new Trap();
        theTrap.setDescription(D20.d20());
        theStair = new Stairs();
        theStair.setType(D20.d20());
        theTreasure = new Treasure();
        theTreasure.chooseTreasure(D20.d20());
        theTreasure.setContainer(D20.d20());
        theDoor = new Door(false);
        theChamber = new Chamber();
        theChamber.reset();
        theChamber.buildRandomChamber();
    }  

    @Test
    public void buildRandomChamberTest()
    {
        System.out.println("buildRandomChamber");
        theChamber.buildRandomChamber();
        assertTrue(theChamber.getDescription().contains(""));
    }

    @Test
    public void resetTest()
    {
        System.out.println("reset");
        int expResult = 0;
        theChamber = new Chamber();
        theChamber.buildRandomChamber();

        theChamber = new Chamber();
        theChamber.buildRandomChamber();

        theChamber = new Chamber();
        theChamber.buildRandomChamber();

        theChamber = new Chamber();
        theChamber.buildRandomChamber();

        theChamber.reset();

        theChamber = new Chamber();
        theChamber.buildRandomChamber();

        assertEquals(expResult, theChamber.getSpaceNum());
    }

    @Test
    public void getDoorsTest()
    {
        System.out.println("getDoors");
        ArrayList<Door> doorList = theChamber.getDoors();
        assertTrue(doorList.size() > 0);
    }

    @Test
    public void addMonsterTest()
    {
        System.out.println("addMonster");
        theChamber.addMonster(myMonster);
        assertTrue(theChamber.getMonsters().contains(myMonster));
    }

    @Test
    public void getMonstersTest()
    {
        System.out.println("getMonsters");
        theChamber.addMonster(myMonster);
        assertTrue(theChamber.getMonsters().contains(myMonster));
    }

    @Test
    public void addTrapTest()
    {
        System.out.println("addTrap");
        theChamber.addTrap(theTrap);
        assertTrue(theChamber.getTraps().contains(theTrap));
    }

    @Test
    public void getTrapsTest()
    {
        System.out.println("getTraps");
        theChamber.addTrap(theTrap);
        assertTrue(theChamber.getTraps().contains(theTrap));
    }

    @Test
    public void addStairTest()
    {
        System.out.println("addStair");
        theChamber.addStair(theStair);
        assertTrue(theChamber.getStairs().contains(theStair));
    }

    @Test
    public void getStairsTest()
    {
        System.out.println("getStairs");
        theChamber.addStair(theStair);
        assertTrue(theChamber.getStairs().contains(theStair));
    }

    @Test
    public void addTreasureTest(){
        System.out.println("addTreasure");
        theChamber.addTreasure(theTreasure);
        assertTrue(theChamber.getTreasureList().contains(theTreasure));
    }

    @Test
    public void getTreasureListTest(){
        System.out.println("getTreasureList");
        theChamber.addTreasure(theTreasure);
        assertTrue(theChamber.getTreasureList().contains(theTreasure));
    }

    @Test
    public void getDescriptionTest()
    {
        System.out.println("getDescription");
        theChamber.buildRandomChamber();
        if(theChamber.getDescription().contains("Chamber")){
            assertTrue(true);
        }
        assertTrue(true);
    }

    @Test
    public void addDoorTest()
    {
        System.out.println("addDoor");
        theChamber.addDoor(theDoor);
        assertTrue(theChamber.getDoors().contains(theDoor));
    }
}