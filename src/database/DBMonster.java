package database;

import java.util.ArrayList;
import java.util.Random;

public class DBMonster implements java.io.Serializable{

	private String monsterName;
	private String upperBound;
	private String lowerBound;
	private String description;
    
	public DBMonster(){

	}

	public DBMonster(String name, String upper, String lower, String desc){
		setName(name);
		setUpperBound(upper);
		setLowerBound(lower);
		setDescription(desc);

	}
	
    //might be easier to make some constructors here

	public void setRandomMonster() {
		DBConnection temp = new DBConnection();
		ArrayList<String> list = temp.getAllMonsters();
        Random rand = new Random();
        this.stringToMonster(list.get(rand.nextInt(list.size())));
	}

	public DBMonster stringToMonster(String theString) {
		/*THIS LINE OF CODE IS TAKEN FROM STACKOVERFLOW
		 https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
		 THIS IS THE URL IT IS RETRIEVED FROM I DID NOT WRITE THIS CODE, I AM USING IT TO EASILY SPLIT THE STRING
		 ONCE AGAIN THIS CODE IS NOT MY OWN ALL CREDIT GOES TO THE POSTER ON STACKOVERFLOW*/
		String[] tokens = theString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		setName(tokens[0]);
        setUpperBound(tokens[1]);
        setLowerBound(tokens[2]);
        setDescription(tokens[3]);
        return this;
	}

    
    public void setName(String name){
		monsterName = name;
	}
	public void setUpperBound(String upper){
		upperBound = upper;
	}
	public void setLowerBound(String lower){
		lowerBound = lower;
    }
	public void setDescription(String desc){
		description = desc;
	}
    
    public String getName(){
		return monsterName;
	}
	public String getUpper(){
		return upperBound;
	}
	public String getLower(){
		return lowerBound;
	}
	public String getDescription(){
		return description;
	}
	@Override
	public String toString(){
        return    "Between " + getLower() + " and " + getUpper() + " " + getName() + ": " + getDescription();

	}
}
