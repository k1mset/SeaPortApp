package cmsc335.project1;

/**
 * File: CargoShip.java
 * Date: 11/1/2018
 * Author: Dillan cobb
 * Purpose: To handle all of the ships that are of a cargo ship type.
 */

// Imports
import java.util.*; 

public class CargoShip extends Ship {
    // Variables
    double cargoWeight;
    double cargoVolume;
    double cargoValue;
    
    // Constructor taking in the scanner object
    public CargoShip(Scanner scanner) {
        // Calls the super constructor with the scanner.
        super(scanner);
        
        // If their is a double remaining, set it to the weight of the ship.
        if (scanner.hasNextDouble()) {
            setWeight(scanner.nextDouble());
        }
        
        // If their is a double remaining, set it to the volume of the ship.
        if (scanner.hasNextDouble()) {
            setVolume(scanner.nextDouble());
        }
        
        // If their is a double remaining, set it to the value of the ship.
        if (scanner.hasNextDouble()) {
            setValue(scanner.nextDouble());
        }
    }
    
    // setValue method takes a double and sets it the cargoValue
    public void setValue(double value) {
        this.cargoValue = value;
    }
    
    // setVolume method takes a double and sets it the cargoVolume
    public void setVolume(double volume) {
        this.cargoVolume = volume;
    }
    
    // setWeight method takes a double and sets it the cargoWeight
    @Override
    public void setWeight(double weight) {
        this.cargoWeight = weight;
    }
    
    // getValue returns the cargoValue 
    public double getValue() {
        return cargoValue;
    }
    
    // getVolume returns the cargoVolume
    public double getVolume() {
        return cargoVolume;
    }
    
    // getWeight returns the cargoWeight 
    @Override
    public double getWeight() {
        return cargoWeight;
    }
     
    // toString method returns the variables of the cargo ship in a string
    @Override
    public String toString() {
        String str;
        
        str = "Cargo Ship: " + super.toString() + "\n>Weight: " + getWeight() +
                "\nVolume: " + getVolume() + "\n>Value: " + getVolume();
        
        return str;
    }
 }
