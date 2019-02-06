/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc335.project1;

/**
 * File: Job.java
 * Date: 11/30/2018
 * Author: Dillan Cobb
 * Purpose: Contains all the information needed to display the jobs and thread
 * the application
 */

// Imports
import java.util.*; 
import javax.swing.*;

public class Job extends Thing implements Runnable {
    // Defined variables
    // skills are: electrician, inspector, cleaner, captain, clerk
    String[] skill = new String[10];
    double duration;
    Thread t;
    boolean goFlag = false;
    boolean noKillFlag = false;
    boolean isComplete = false;
    JProgressBar progressBar;
    boolean isDocked;
    Ship jobShip;
    SeaPort parentPort;
    Dock parentDock;
    boolean hasWorker = false;
    ArrayList<Person> workers = new ArrayList<Person>();
    int reqWorkers;
    boolean workersReady = false;
    
    // Enumerator for the threads status
    enum Status {
        WAITING, RUNNING, SUSPENDED, CANCELLED, DONE
    }
    
    // Constructor
    public Job (Scanner sc) {
        super(sc);
        
        // If the next double, use that for the duration
        if (sc.hasNextDouble()) setDuration(sc.nextDouble());
        
        // Takes all next strings and sets them to the skills
        int skillCounter = 0;
        while(sc.hasNext()) {
            skill[skillCounter] = sc.next();
            skillCounter++;
        }
        reqWorkers = skillCounter;
    }
    
    // setWorkers set the arraylist of workers to the parent seaports workers
    public void setWorkers(SeaPort port) {
        workers = port.getPersons();
        hasWorker = true;
    }
    
    // SetDocked method sets the boolea isDocked
    public void setDocked(boolean status) {
        isDocked = status;
    }
    
    // sets tha parentPort to the port inputted
    public void setParentPort(SeaPort port) {
        parentPort = port;
    }
    
    // setParentDock sets the parentDock
    public void setParentDock(Dock dock) {
        parentDock = dock;
    }
    
    // setJobShip sets the jobShip
    public void setJobShip(Ship ship) {
        jobShip = ship;
    }
    
    // setProgressBar sets the progressBar
    public void setProgressBar(JProgressBar progBar) {
        progressBar = progBar;
    }
    
    // start is ran at the beginning of the thread
    public void start () {
        System.out.println("Starting " +  this.getName() );
        if (t == null) {
            t = new Thread (this, this.getName());
            t.start ();
        }
    }
    
    // setDuration sets the duration of the job
    public void setDuration(double value) {
        duration = value;
    }
    
    // checkWorkers method will assign the workers to the job if they are not
    // currently doing a job
    public void checkWorkers() {
        int cnt = 0;
        for (int i = 0; i < skill.length; i++) {
            
            for (Person p : workers) {
                if (p.checkWorking() == false) {
                    p.setWorking(true);
                    cnt++;
                }
            }
        }
        
        if (cnt >= reqWorkers) {
            workersReady = true;
        }
    }
    
    // resetWorkers method will reset all the workers in the arraylist of the 
    // seaport and ready them for the next job
    public void resetWorkers() {
        for (Person p : workers) {
            p.setWorking(false);
        }
    }
    
    // run is the execution of the thread
    public void run() {
        System.out.println("Running " + this.getName());   
        
        long time = System.currentTimeMillis();
        long startTime = time;
        long stopTime = time + 1000 * (long) duration;
        double timeNeeded = stopTime - time;
        
        isDocked = jobShip.checkDocked();
        setWorkers(parentPort);
        
        // if the ship is not docked
        if (!isDocked){
            synchronized (parentPort) {
                //System.out.print("TESTING");
                while (!goFlag) {
                    //showStatus(Status.WAITING);
                    try {
                        parentPort.wait();
                    } catch (InterruptedException e) {
                    }
                    
                }
            }
        }
        
        // loop for docked ship to set the workers
        if (isDocked) {
            // loop for if the workers are ready to work or not
            while (!workersReady) {
                // when the resume button is pressed begin checking if the 
                // workers are good to work
                if (goFlag) {
                    // synch the workers arraylsit
                    synchronized (workers) {
                        // checks the works, then sleeops for 100 ms
                        // if they are ready, than exit the loop
                        try {
                            checkWorkers();
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                    }
                } else {
                    // when the goFlag is not true, just sleep to prevent
                    // any issues with CPU usage (i locked my PC up twice
                    // wondering what the issue was)
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }
        
        // handles the progress bars operation and when the bar is full set
        // to complete
        while (time < stopTime && noKillFlag && workersReady) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            if (goFlag) {
                synchronized(parentDock) {
                    showStatus(Status.RUNNING);
                    time += 100;
                    progressBar.setValue((int) (((time - startTime) / timeNeeded)
                            * 100));
                    
                    if (progressBar.getValue() == 100) {
                        isComplete = true;
                    }
                }
            } else {
                showStatus(Status.SUSPENDED);
            }
        }
        
        // if ship is docked, undock. also release workers for next job
        if (isDocked && isComplete) {
            System.out.println("Finished job");
            resetWorkers();
            synchronized (parentDock) {
                try {
                for (Dock dock : parentPort.getDocks()) {
                    if (dock.getShip() != null) {
                        if (dock.getShip().getJobs().isEmpty() || 
                                isAllJobsFinished()) {
                            dockNextShip();
                            parentDock.notifyAll();
                        }
                    }
                }
                } catch (Exception e) {

                }
            }
        }
    }
    
    // showStatus will show the enumerator status
    public void showStatus(Status status) {
        System.out.println(status);
    }
    
    // dockNextShip will do all the processing for the next ship for the dock
    public void dockNextShip() {
        parentDock.setShip(null);
        parentPort.addToShips(jobShip);
        Ship nextShip = parentPort.getTopOfQue();
        parentPort.removeFromQue(nextShip);
        parentDock.setShip(nextShip);
        
    }
    
    // isAllJobsFinished checks if the jobs are all finished for the dock
    public boolean isAllJobsFinished() {
        boolean fin = jobShip.checkJobStatus();
        
        return fin;
    }
    
}
