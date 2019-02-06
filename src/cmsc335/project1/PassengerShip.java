package cmsc335.project1;

/**
 * File: PassengerShip.java
 * Date: 11/1/2018
 * Author: Dillan Cobb
 * Purpose: To handle the Ships of the type PassengerShip from the data file.
 */

// Imports
import java.util.*; 

public class PassengerShip extends Ship {
    // Variables
    int numberOfOccupiedRooms;
    int numberOfPassengers;
    int numberOfRooms;
    
    // Constructor taking in the scanner
     public PassengerShip (Scanner sc) {
         // Calling the super constructor with the scanner
        super (sc);
        
        // If there is a int, set the passangers
        if (sc.hasNextInt()) setPassengers(sc.nextInt());
        
        // If there is a int, set the rooms
        if (sc.hasNextInt()) setRooms(sc.nextInt());
        
        // If therei s a int, set the occupied rooms
        if (sc.hasNextInt()) setOccupiedRooms(sc.nextInt());
    } // end end Scanner constructor
     
     
     // setPassengers method sets the numberofpassengers variable
    public void setPassengers(int passengers) {
        this.numberOfPassengers = passengers;
    }
    
    // setRooms method sets the numberofrooms variable
    public void setRooms(int rooms) {
        this.numberOfRooms = rooms;
    }
    
    // setOccupiedRooms method sets the numberofrooms variable
    public void setOccupiedRooms(int rooms) {
        this.numberOfRooms = rooms;
    }
    
    // getPassengers returns the numberofpassangers on the ship
    public int getPassengers() {
        return numberOfPassengers;
    }
    
    // getRooms retruns the number of rooms on the ship
    public int getRooms() {
        return numberOfRooms;
    }
    
    // getOccupiedRooms returns the number of occupied rooms on the ship
    public int getOccupiedRooms() {
        return numberOfOccupiedRooms;
    }
    
    // toString returns the information oin the passenger ship
    @Override
    public String toString () {
        String str;
        
        str = "Passenger Ship: " + super.toString() + "\n>Passengrs: "
                + getPassengers() + "\n>Rooms: " + getRooms() + "\n>Occupied Ro"
                + "oms: " + getOccupiedRooms();
        
        return str;
    } 
}
