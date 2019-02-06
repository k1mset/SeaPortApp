package cmsc335.project1;

/**
 * File: Dock.java
 * Date: 11/1/2018
 * Author: Dillan cobb
 * Purpose: Handle all of the docks inside the world of which the data file
 * originates.
 */

// Imports
import java.util.*; 
import javax.swing.tree.DefaultMutableTreeNode;  

public class Dock extends Thing {
    // Variables
    Ship ship;
    
    // Dock constructor with the scanner argument
    public Dock(Scanner scanner) {
        // Calls the super constructor with the scanner
        super(scanner);
    }
    
    // sets the ship to the dock
    public void setShip(Ship ship) {
        this.ship = ship;
    }
    
    // gets the ship from the dock
    public Ship getShip() {
        return ship;
    }
    
    // toString method returns if there is a ship currently in the dock or not
    @Override
    public String toString() {
        String str;
        
        if (ship == null) {
            str = "No Ship in Dock";
        } else {
            str = ">> Current Dock: " + this.getName() + "\n" + getShip().toString();
        }
        
        return str;
    }
    
    public DefaultMutableTreeNode toTree() {
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode(super.name);
        DefaultMutableTreeNode dockShip;
        
        
        if (ship == null) {
            dockShip = new DefaultMutableTreeNode("No ship in dock");
        } else {
            dockShip = new DefaultMutableTreeNode(getShip().toTree());
        }
        
        tree.add(dockShip);
        
        return tree;
    }
}
