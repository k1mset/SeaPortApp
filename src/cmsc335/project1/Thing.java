package cmsc335.project1;

/**
 * File: Thing.java
 * Date: 11/1/2018
 * Author: Dillan Cobb
 * Purpose: Handle all of the names, index numbers and parent id numbers of 
 * all of the objects of data within the application. 
 */

// Imports
import java.util.*;

public class Thing implements Comparable<Thing> {
    
    // Variables
    int index;
    String name;
    int parent;
    
    // Thing constructor takes in the scanner
    public Thing(Scanner sc) {
        
        // Takes the first bit of information and makes that the name of the 
        // object
        if (sc.hasNext()) {
            this.setName(sc.next());
        }else{
            name = "Error";
        }
        
        // Takes the next int and sets the index, or just sets the index to 0
        if (sc.hasNextInt()) {
            setIndex(sc.nextInt());
        }else{
            index = 0;
        }
        
        // Takes the next int and sets that as the parent, or sets it to 0
        if (sc.hasNextInt()) {
            setParent(sc.nextInt());
        }else{
            parent = 0;
        }
    }
    
    // setName method sets the name of the object
    public void setName(String name) {
        this.name = name;
    }
    
    // setIndex method sets the index of the object
    public void setIndex(int index) {
        this.index = index;
    }
    
    
    // setParent method sets the parent of the object
    public void setParent(int parent) {
        this.parent = parent;
    }
    
    // returns the name of the object
    public String getName() {
        return this.name;
    }    
    
    // returns the index of the object
    public int getIndex() {
        return this.index;
    }
    
    // returns the parent of the object
    public int getParent() {
        return this.parent;
    }
    
    // toString method returns the name and index of the object
    @Override
    public String toString() {
        String str;
        
        str = getName() + " " + getIndex();
        
        return str;
    }
    
    // compareTo compares two indexs
    @Override
    public int compareTo(Thing other) {
        return Integer.compare(index, other.getIndex());
    }
    
    // Compares two parents
    public int compareParent(Thing other) {
        return Integer.compare(parent, other.getParent());
    }
    
}
