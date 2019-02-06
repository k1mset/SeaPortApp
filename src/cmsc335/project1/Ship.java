package cmsc335.project1;

/**
 * File: Ship.java
 * Date: 11/1/2018
 * Author: Dillan Cobb
 * Purpose: Handle all of the worlds ships and their locations, as well as
 * their sizes.
 */

// Imports
import java.util.*; 
import javax.swing.tree.DefaultMutableTreeNode;  

public class Ship extends Thing {
    // Variables of the ship
    PortTime arrivalTime;
    PortTime dockTime;
    double weight;
    double length;
    double width;
    double draft;
    ArrayList <Job> jobs;
    boolean isDocked;
    
    // Ship constructor takes in the scanner
    public Ship(Scanner scanner) {
        // Calls the super constructor with the scanner
        super(scanner);
        
        // If there is a double then set that to the weight
        if (scanner.hasNextDouble()) {
            setWeight(scanner.nextDouble());
        }
        
        // If therier is a double then set that to the length
        if (scanner.hasNextDouble()) {
            setLength(scanner.nextDouble());
        }
        
        // If there is a double then set that to the width
        if (scanner.hasNextDouble()) {
            setWidth(scanner.nextDouble());
        }
        
        // If there is a double then set that to the draft
        if (scanner.hasNextDouble()) {
            setDraft(scanner.nextDouble());
        }
        
        jobs = new ArrayList<Job>();
    }
    
    public void setDocked(boolean docked) {
        this.isDocked = docked;
    }
    
    public boolean checkDocked() {
        return isDocked;
    }
    
    public void addJob(Job job) {
        this.jobs.add(job);
    }
    
    public ArrayList<Job> getJobs() {
        return jobs;
    }
    
    public boolean checkJobStatus() {
        boolean fin = true;
        for (Job job : jobs) {
            if (job.isComplete == false) {
                fin = false;
            }
        }
        
        return fin;
    }
    
    // setWeight sets the ships weight
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    // setLength sets the ships length
    public void setLength(double length) {
        this.length = length;
    }
    
    // setWidth sets the ships width
    public void setWidth(double width) {
        this.width = width;
    }
    
    // setDraft sets the ships draft
    public void setDraft(double draft) {
        this.draft = draft;
    }
    
    // getWeight retruns the weight of the ship
    public double getWeight() {
        return weight;
    }
    
    // getLength returns the length of the ship
    public double getLength() {
        return length;
    }
    
    // getWidth returns the width of the ship
    public double getWidth() {
        return width;
    }
    
    // getDraft returns the draft of the ship
    public double getDraft() {
        return draft;
    }    
    
    // toString method returns the ships total information
    @Override
    public String toString() {
        String str;
        
        str = "Ship: " + super.toString() + "\n>Weight: " + getWeight() + "\n>L"
                + "ength: " + getLength() + "\n>Width: " + getWidth() + "\n>Dra"
                + "ft: " + getDraft();
        
        return str;
     }
    
    public DefaultMutableTreeNode toTree() {
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode(super.name);
        DefaultMutableTreeNode shipWeight = new DefaultMutableTreeNode("Weight: " + getWeight());
        DefaultMutableTreeNode shipLength = new DefaultMutableTreeNode("Length: " + getLength());
        DefaultMutableTreeNode shipWidth = new DefaultMutableTreeNode("Width: "  + getWidth());
        DefaultMutableTreeNode shipDraft = new DefaultMutableTreeNode("Draft: " + getDraft());
        
        tree.add(shipWeight);
        tree.add(shipLength);
        tree.add(shipWidth);
        tree.add(shipDraft);
        
        return tree;
    }
    
    // compareWeight compares two ships weights
    public int compareWeight(Ship other) {
        return Double.compare(weight, other.getWeight());
    }
    
    // cmopareLength compares two ships lengths
    public int compareLength(Ship other) {
        return Double.compare(length, other.getLength());
    }
    
    // compareWeight compares two ships widths
    public int compareWidth(Ship other) {
        return Double.compare(width, other.getWidth());
    }
    
    // compareDraftcompares two ships drafts
    public int compareDraft(Ship other) {
        return Double.compare(draft, other.getDraft());
    }

}
