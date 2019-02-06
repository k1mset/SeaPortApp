package cmsc335.project1;

/**
 * File: Person.java
 * Date: 11/1/2018
 * Author: Dillan cobb
 * Purpose: To handle all of the people that are assigned jobs at the docks.
 */

// Imports
import java.util.*; 
import javax.swing.tree.DefaultMutableTreeNode;  

public class Person extends Thing {
    // Variables
    String skill;
    
    boolean isWorking = false;
    int jobID = 0;
    
    // Constructor taking in a scanner class
    public Person(Scanner scanner) {
        // Calls the super constructor and sends the argument of scanner
        super(scanner);
        
        // Handles the current scanner, if their is a next, than take it and 
        // set it to the skill.
        if (scanner.hasNext()) {
            setSkill(scanner.next());
        }
    }
    
    public boolean checkWorking() {
        return isWorking;
    }
    
    public void setJobID(int jobid) {
        jobID = jobid;
    }
    
    public int getJobID() {
        return jobID;
    }
    
    public void setWorking(boolean work) {
        isWorking = work;
    }
    
    public boolean getWorking() {
        return isWorking;
    }
    
    // setSkill method takes a String in and assigns it to the skill string.
    public void setSkill(String skill) {
        this.skill = skill;
    }
    
    // getSkill returns the string skill.
    public String getSkill() {
        return skill;
    }
    
    // toString method retruns the current persons information + their skill.
    @Override
    public String toString() {
        String str;
        
        str = "Person: " + super.toString() + " Skill: " + getSkill();
        
        return str;
    }
    
    public DefaultMutableTreeNode toTree() {
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode(super.name);
        DefaultMutableTreeNode personSkill = new DefaultMutableTreeNode("Skill: " + getSkill());
        
        tree.add(personSkill);
        
        return tree;
    }
}
