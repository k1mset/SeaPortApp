
package cmsc335.project1;

/**
 * File: World.java
 * Date: 11/1/2018
 * Author: Dillan Cobb
 * Purpose: Take in the information from the file and then construct the entire
 * programs handling of the data contents, sort and return back the sorted
 * contents to the user.
 */

// Imports
import java.util.*; 
import javax.swing.tree.DefaultMutableTreeNode;  


public class World extends Thing {
    
    // Variables
    ArrayList <Thing> allThings;
    ArrayList <SeaPort> ports;
    ArrayList <Dock> docks;
    ArrayList <Ship> ships;
    ArrayList <PassengerShip> pships;
    ArrayList <CargoShip> cships;
    ArrayList <Person> persons;
    ArrayList <Job> jobs;
    PortTime time;
    
    HashMap<Integer, SeaPort> seaPortMap = new HashMap<>();
    HashMap<Integer, Dock> dockMap = new HashMap<Integer, Dock> ();
    HashMap<Integer, Ship> shipMap = new HashMap<Integer, Ship> ();
    HashMap<Integer, Person> personMap = new HashMap<Integer, Person> ();
    HashMap<Integer, Job> jobMap = new HashMap<Integer, Job>();
    
    // World constructor takesin the scanner from the SeaPortProgram
    public World(Scanner scanner) { 
        // Sends the scanner to the super constructor
        super(scanner);
        
        // Sets the arraylists up to be used
        setPorts(new ArrayList <SeaPort>());
        setAllThings(new ArrayList <Thing>());
        setDocks(new ArrayList <Dock>());
        setShips(new ArrayList <Ship>());
        setPassengerShips(new ArrayList <PassengerShip>());
        setCargoShips(new ArrayList <CargoShip>());
        setPerson(new ArrayList <Person>());
        setJob(new ArrayList <Job>());
        
        // Processes the remaining scanner information
        process(scanner);
    }
    // process method handles all the remaining scanner information and determines
    // what to do with it and where to send it
    public void process(Scanner sc) {
        
        // Variables used with the scanner
        String lineStr;
        Scanner line;
        String[] lineArray;
        
        // While loop that handles the remaining contents of the scanner
        while(sc.hasNext()) {
            lineStr = sc.nextLine().trim();
            lineArray = lineStr.trim().split(" ");
            
            // If theres a blank than continue
            if (lineStr.length() == 0) {
                continue;
            }
            
            line = new Scanner(lineStr);
            
            if (lineArray[0] != "//") {
                if (line.hasNext()) {
                
                    // Switch that determines what to do with particualr lines inside
                    // of the scanner line
                    switch(line.next().trim()) {
                        case "port":
                            SeaPort newSeaPort = new SeaPort(line);
                            ports.add(newSeaPort);                            
                            addSeaPortMap(newSeaPort);                            
                            break;

                        // Dock gets sent to SeaPort
                        case "dock":
                            Dock newDock = new Dock(line);
                            docks.add(newDock);
                            assignDock(newDock);                            
                            addDockMap(newDock);                            
                            break;

                        // Passenger ships get sent to Ship
                        case "pship":
                            PassengerShip newPassengerShip = new PassengerShip(line);
                            pships.add(newPassengerShip);
                            
                            Dock dockCheck = getDockByIndex(newPassengerShip.getParent(), dockMap);
                            
                            if (dockCheck == null) {
                                newPassengerShip.setDocked(false);
                            } else {
                                newPassengerShip.setDocked(true);
                            }
                            
                            assignShip(newPassengerShip);                            
                            addShipMap(newPassengerShip);                            
                            break;

                        // Passenger ships get sent to Ship
                        case "cship":
                            CargoShip newCargoShip = new CargoShip(line);
                            cships.add(newCargoShip);
                            
                            dockCheck = getDockByIndex(newCargoShip.getParent(), dockMap);
                            
                            if (dockCheck == null) {
                                newCargoShip.setDocked(false);
                            } else {
                                newCargoShip.setDocked(true);
                            }
                            
                            assignShip(newCargoShip);                            
                            addShipMap(newCargoShip);                            
                            break;

                        // Creates and assigns the person
                        case "person":
                            Person newPerson = new Person(line);
                            persons.add(newPerson);
                            assignPerson(newPerson);                            
                            addPersonMap(newPerson);                            
                            break;
                            
                        case "job":
                            Job newJob = new Job(line);
                            jobs.add(newJob);
                            
                            Ship jobShip = getShipByIndex(newJob.getParent(), shipMap);
                            
                            newJob.setJobShip(jobShip);
                            
                            SeaPort jobShipsPort;
                            
                            if (jobShip.checkDocked()) {
                                Dock jobShipsDock = getDockByIndex(jobShip.getParent(), dockMap);
                                jobShipsPort = getSeaPortByIndex(jobShipsDock.getParent(), seaPortMap);
                                newJob.setParentDock(jobShipsDock);
                            } else {
                                jobShipsPort = getSeaPortByIndex(jobShip.getParent(), seaPortMap);
                            }
                            
                            newJob.setParentPort(jobShipsPort);
                            
                            addJobMap(newJob);
                            break;
                    }
                }
            }
        }
    }
    
    public int getNumOfJobs() {
        int jobNum = jobs.size();
        
        return jobNum;
    }
    
    public void setJob(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }
    
    // sets the ports to a fresh arraylist
    public void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }
    
    // sets the allthings to a fresh arraylist
    public void setAllThings(ArrayList<Thing> things) {
        this.allThings = things;
    }
    
    // sets the docks to a fresh arraylist
    public void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }
    
    // sets the ships to a fresh arraylist
    public void setShips(ArrayList <Ship> ships) {
        this.ships = ships;
    }
    
    // sets the passengerships to a fresh arraylist
    public void setPassengerShips(ArrayList <PassengerShip> pships) {
        this.pships = pships;
    }
    
    // sets the cargo ships to a fresh arraylist
    public void setCargoShips(ArrayList <CargoShip> cships) {
        this.cships = cships;
    }
    
    // sets the people to a fresh arraylist
    public void setPerson(ArrayList <Person> person) {
        this.persons = person;
    }
    
    // returns the ports arraylist
    public ArrayList<SeaPort> getPorts() {
        return this.ports;
    }
    
    // returns the allthings arraylist
    public ArrayList<Thing> getAllThings() {
        return this.allThings;
    }
    
    // returns the dock arraylist
    public ArrayList<Dock> getDocks() {
        return this.docks;
    }
    
    // reutnrs the ships arraylist
    public ArrayList<Ship> getShips() {
        return this.ships;
    }
    
    // returns the passenger ship arraylsit
    public ArrayList<PassengerShip> getPassengerShips() {
        return this.pships;
    }
    
    // rturns the cargoship arraylist
    public ArrayList<CargoShip> getCargoShips() {
        return this.cships;
    }
    
    // returns the persons arraylist
    public ArrayList<Person> getPersons() {
        return this.persons;
    }
    
    public ArrayList<Job> getJobs() {
        return this.jobs;
    }
    
    public void addJobMap(Job job) {
        int index = job.getIndex();
        
        jobMap.put(index, job);
    }
    
    // addSeaPortMap method takes in a SeaPort and adds it along with its
    // index number to the seaPortMap
    public void addSeaPortMap(SeaPort port) {
        int index = port.getIndex();
        
        seaPortMap.put(index, port);
    }
    
    // addDockMap method takes in a dock and adds it along with its
    // index number to the dockMap
    public void addDockMap(Dock dock) {
        int index = dock.getIndex();
        
        dockMap.put(index, dock);
    }    
    
    // addShipMap method takes in a ship and adds it along with its
    // index number to the shipMap
    public void addShipMap(Ship ship) {
        int index = ship.getIndex();
        
        shipMap.put(index, ship);
    }  
    
    // addPersonMap method takes in a person and adds it along with its
    // index number to the personMap
    public void addPersonMap(Person person) {
        int index = person.getIndex();
        
        personMap.put(index, person);
    }  
    
    // getSeaPortByIndex returns the SeaPort from the map at the key of x
    public SeaPort getSeaPortByIndex(int x, java.util.HashMap<Integer, SeaPort> hms) {
        SeaPort returnPort = hms.get(x);
        if (returnPort == null) {
            System.out.println("SeaPort return failed.");
            return null;
        }
        return returnPort;
    }
    
    // getDockByIndex returns the Dock from the map at the key of x
    public Dock getDockByIndex(int x, java.util.HashMap<Integer, Dock> hms) {
        Dock returnDock = hms.get(x);
        if (returnDock == null) {
            System.out.println("Dock return failed for Dock.");
            return null;
        }
        return returnDock;
    }
    
    // getPersonByIndex returns the Person from the map at the key of x
    public Person getPersonByIndex(int x, java.util.HashMap <Integer, Person> hms) {
        Person returnPerson = hms.get(x);
        if (returnPerson == null) {
            System.out.println("Dock return failed for Person.");
            return null;
        }
        return returnPerson;
    }
    
    // getShipByIndex returns the Ship from the map at the key of x
    public Ship getShipByIndex (int x, java.util.HashMap <Integer, Ship> hms) {
        Ship returnShip = hms.get(x);
        if (returnShip == null) {
            System.out.println("Ship return failed.");
            return null;
        }
        return returnShip;
    } // end getDockByIndex
    
    // assigns the dock to a seaport
    public void assignDock (Dock ms) {
        SeaPort md = getSeaPortByIndex (ms.parent, seaPortMap);
        if (md == null) {
            return;
        }
        md.docks.add(ms);
    }
    
    // assigns the person to the seaport
    public void assignPerson (Person ms) {
        SeaPort md = getSeaPortByIndex(ms.parent, seaPortMap);
        if (md == null) {
            return;
        }
        md.persons.add(ms);
    }
    
    // assigns a ship to the dock
    public void assignShip (Ship ms) {
        System.out.println("assignign ship: " + ms.getName());
        Dock md = getDockByIndex (ms.getParent(), dockMap);
        if (md == null) {
            getSeaPortByIndex (ms.parent, seaPortMap).ships.add (ms);
            getSeaPortByIndex (ms.parent, seaPortMap).que.add(ms);
            //shipSort(ms);
            return;
        }
        md.ship = ms;
        getSeaPortByIndex (md.parent, seaPortMap).ships.add (ms);
    } // end method assignShip
    
    // Used for sorting all the que by weight
    public void sortByWeight() {
        for(SeaPort seaPorts: getPorts()) {
            Collections.sort(seaPorts.getQue(), new SortByWeight());
        }
    }
    
    // Used for sorting all the que by width
    public void sortByWidth() {
        for(SeaPort seaPorts: getPorts()) {
            Collections.sort(seaPorts.getQue(), new SortByWidth());
        }
    }
    
    // Used for sorting all the que by length
    public void sortByLength() {
        for(SeaPort seaPorts: getPorts()) {
            Collections.sort(seaPorts.getQue(), new SortByLength());
        }
    }
    
    // Used for sorting all the que by draft
    public void sortByDraft() {
        for(SeaPort seaPorts: getPorts()) {
            Collections.sort(seaPorts.getQue(), new SortByDraft());
        }
    }
    
    // sortByName method sorts all of the information in all arraylists
    // in alphabetical order
    public void sortByName() {
        // Sorts the world arraylists
        Collections.sort(getPorts(), new SortByName());
        Collections.sort(getDocks(), new SortByName());
        Collections.sort(getShips(), new SortByName());
        Collections.sort(getPersons(), new SortByName());
        
        // Sorts the seaport arrayy lists
        for(SeaPort seaPorts: getPorts()) {
            Collections.sort(seaPorts.getPersons(), new SortByName());
        }
        for(SeaPort seaPorts: getPorts()) {
            Collections.sort(seaPorts.getDocks(), new SortByName());
        }
        for(SeaPort seaPorts: getPorts()) {
            Collections.sort(seaPorts.getShips(), new SortByName());
        }
        for(SeaPort seaPorts: getPorts()) {
            Collections.sort(seaPorts.getQue(), new SortByName());
        }
    }
    
    // toString method returns all information of the data from the file that
    // has been processed.
    @Override
    public String toString() {        
        String str;
        
        str = ">>>>> The World\n";
        for (SeaPort seaPorts: getPorts()) {
            str += seaPorts.toString() + "\n";
        }
        
        return str;
    }
    
    public DefaultMutableTreeNode toTree() {
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode("World");
        
        for (SeaPort seaPorts: getPorts()) {
            tree.add(seaPorts.toTree());
        }
        
        return tree;
    }
}

// class handles the compare for two ships weights
class SortByWeight implements Comparator<Ship> {

    @Override

    public int compare(Ship a, Ship b) {

        return (int) (a.getWeight() - b.getWeight());

    }

}

// class handles the compare for two ships lengths
class SortByLength implements Comparator<Ship> {

    @Override

    public int compare(Ship a, Ship b) {

        return (int) (a.getLength() - b.getLength());

    }

}

// class handles the compare for two ships widths
class SortByWidth implements Comparator<Ship> {

    @Override

    public int compare(Ship a, Ship b) {

        return (int) (a.getWidth() - b.getWidth());

    }

}

// class handles the compare for two ships drafts
class SortByDraft implements Comparator<Ship> {

    @Override

    public int compare(Ship a, Ship b) {

        return (int) (a.getDraft() - b.getDraft());

    }

}

// class handles the compare for two objects names
class SortByName implements Comparator<Thing> {

    @Override

    public int compare(Thing a, Thing b) {

        return a.getName().compareTo(b.getName());

    }

}
