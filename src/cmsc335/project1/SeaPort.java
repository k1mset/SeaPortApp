package cmsc335.project1;

/**
 * File: SeaPort.java
 * Date: 11/1/2018
 * Author: Dillan Cobb
 * Purpose: Handle all of the seaport objects of data from the file.
 */

// Imports
import java.util.*; 
import javax.swing.tree.DefaultMutableTreeNode;  

public class SeaPort extends Thing {
    
    // Variables 
    ArrayList <Dock> docks;
    ArrayList <Ship> que;
    ArrayList <Ship> ships;
    ArrayList <Person> persons;
        
    // SeaPort contructor takes in the canner
    public SeaPort(Scanner scanner) {
        // Sends the super constructor the scanner
        super(scanner);
        
        // Set the array lists up
        setDocks(new ArrayList<Dock>());
        setQue(new ArrayList<Ship>());
        setShips(new ArrayList<Ship>());
        setPersons(new ArrayList<Person>());
    }
    
    public void removeFromQue(Ship shipInQue) {
        this.que.remove(shipInQue);
    }
    
    public void addToShips(Ship shipAdd) {
        this.ships.add(shipAdd);
    }
    
    public Ship getTopOfQue() {
        if (!this.que.isEmpty()) {
            return this.que.get(0);
        }
        
        return null;
    }
    
    // sets the docks to a default arraylist
    public void setDocks(ArrayList<Dock> dock) {
        this.docks = dock;
    }
    
    // sets the ques to a default arraylist
    public void setQue(ArrayList<Ship> que) {
        this.que = que;
    }
    
    // sets the ships to a default arraylist
    public void setShips(ArrayList<Ship> ship) {
        this.ships = ship;
    }
    
    // sets the persons to a default array list
    public void setPersons(ArrayList<Person> person) {
        this.persons = person;
    }
    
    // returns the arraylist of docks
    public ArrayList<Dock> getDocks() {
        return docks;
    }
    
    // returns the arraylsit of ques
    public ArrayList<Ship> getQue() {
        return que;
    }
    
    // returns the arraylist of ships
    public ArrayList<Ship> getShips() {
        return ships;
    }
    
    // returns the arraylist of persons
    public ArrayList<Person> getPersons() {
        return persons;
    }
    
    // toString method returns all of the information attached to that SeaPort
    @Override
    public String toString() {
        String st = "\nSeaPort: " + super.toString();
        
        for (Dock md: docks) {
            st += "\n" + md.toString();
        }
        
        for (Ship ms: que ) {
            st += "\n > " + ms.toString();
        }
        
        st += "\n\n >>> List of all ships:";
        
        for (Ship ms: ships) {
            st += "\n > " + ms.toString();
        }
        
        st += "\n\n >>> List of all persons:";
        
        for (Person mp: persons) {
            st += "\n > " + mp.toString();
        }
    
        return st;
    }
    
    public DefaultMutableTreeNode toTree() {
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode(super.name);
        DefaultMutableTreeNode shipQue = new DefaultMutableTreeNode("Current Ship Que");
        DefaultMutableTreeNode allShips = new DefaultMutableTreeNode("All Ships");
        DefaultMutableTreeNode allPeople = new DefaultMutableTreeNode("All People");
        
        
        for (Dock md: docks) {
            tree.add(md.toTree());
        }
        
        for (Ship ms: que ) {
            shipQue.add(ms.toTree());
        }
        
        for (Ship ms: ships) {
            allShips.add(ms.toTree());
        }
        
        for (Person mp: persons) {
            allPeople.add(mp.toTree());
        }
        
        tree.add(shipQue);
        tree.add(allShips);
        tree.add(allPeople);
        
        return tree;
    }
    
}
