package cmsc335.project1;

/**
 * File: SeaPortProgram.java
 * Date: 11/1/2018
 * Author: Dillan Cobb
 * Purpose: Display the GUI to the user and then handle the opening of a select
 * file for the use of the program. Direct the contents to the World.java
 * to be processed.
 */

// Imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeaPortProgram extends JPanel {

    // Creating the GUI items
    private JLabel currentFile = new JLabel("File:");
    private JTextField currentFileTxt = new JTextField();
    private JButton fileChooseBtn = new JButton("Choose File");
    private JTextArea displayInfo = new JTextArea(20, 20);
    private JScrollPane displayScroll = new JScrollPane(displayInfo);
    private JFileChooser fileChoose = new JFileChooser(".");
    private JRadioButton queWeight = new JRadioButton("Sort que by weight");
    private JRadioButton queWidth = new JRadioButton("Sort que by width");
    private JRadioButton queLength = new JRadioButton("Sort que by length");
    private JRadioButton queDraft = new JRadioButton("Sort que by draft");
    private JRadioButton nameSort = new JRadioButton("Sort all by names");
    private JTree tree;
    private JPanel jobsPanel = new JPanel();
    
    // Creating goods for handling the file
    private Scanner scannerIn = null;
    private FileInputStream in = null;
    private BufferedReader inputStream = null;
    
    private World world;
    
    // main creates the frame and panels for the users gui
    public static void main(String[] args) {
        JFrame app = new JFrame();
        SeaPortProgram appPanel = new SeaPortProgram();
        app.setTitle("CMSC 335 - Project 1");
        app.setSize(1100, 1000);
        app.setLocationRelativeTo(null);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(appPanel);
        app.setVisible(true);
    }
    
    // Creates the gui and handles operation of the program
    public SeaPortProgram() {
        
        
        // Panels for the gui to sit in
        JPanel topPanel = new JPanel();
        JPanel sortPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        
        // Setup for top panel
        topPanel.setLayout(new GridLayout(1, 3, 10, 10));
        topPanel.add(currentFile);
        topPanel.add(currentFileTxt);
        currentFileTxt.setEnabled(false);
        topPanel.add(fileChooseBtn);
        
        // Setup for the sort panel
        sortPanel.setLayout(new GridLayout(2, 3, 10, 10));
        sortPanel.add(queWeight);
        sortPanel.add(queWidth);
        sortPanel.add(queDraft);
        sortPanel.add(queLength);
        sortPanel.add(nameSort);
        // Groups the radio buttons
        ButtonGroup queBtnGroup = new ButtonGroup();
        queBtnGroup.add(queWeight);
        queBtnGroup.add(queWidth);
        queBtnGroup.add(queDraft);
        queBtnGroup.add(queLength);
        queBtnGroup.add(nameSort);
        
        // Setup for the bottomPanel
        bottomPanel.setLayout(new GridLayout(1, 1, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        //bottomPanel.add(displayInfo, BorderLayout.NORTH);
        //bottomPanel.add(displayScroll, BorderLayout.CENTER);
        displayScroll.setBounds(5, 5, 10, 10);  
        displayScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        displayScroll.getViewport().add(displayInfo);
        
        // Setup main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.add(topPanel);
        mainPanel.add(sortPanel);
        mainPanel.add(bottomPanel);
        this.add(mainPanel);
        
        // Handle the file chooser and operation once the files selected
        fileChooseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChoose.showOpenDialog(null);
                
                // File selectedFile = null;
                File selectedFile;
                FileReader fileReader;
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        selectedFile = fileChoose.getSelectedFile();
                        fileReader = new FileReader(selectedFile);
                        currentFileTxt.setText(selectedFile.getAbsolutePath());
                        scannerIn = new Scanner(fileReader);
                    } catch (FileNotFoundException ex) {
                        
                    }
                    
                }
                
                world = new World(scannerIn);
                
                if (queWeight.isSelected()) {
                    world.sortByWeight();
                }
                if (queWidth.isSelected()) {
                    world.sortByWidth();
                }
                if (queLength.isSelected()) {
                    world.sortByLength();
                }
                if (queDraft.isSelected()) {
                    world.sortByDraft();
                }
                if (nameSort.isSelected()) {
                    world.sortByName();
                }
                
                // displayInfo.setText(world.toString());
                tree = new JTree(world.toTree());
                
                
                JScrollPane displayScroll2 = new JScrollPane(tree);
                
                //bottomPanel.add(tree, BorderLayout.NORTH);
                //bottomPanel.add(displayInfo, BorderLayout.NORTH);
                bottomPanel.add(displayScroll2, BorderLayout.CENTER);
                
                
                // START HERE
                // CREATE THE JOBSPANEL
                JLabel jobName = new JLabel("Job Name");
                JLabel dockName = new JLabel("Dock");
                JLabel status = new JLabel("Status");
                JLabel options = new JLabel("Options");
                JLabel blank = new JLabel(" ");
                
                jobsPanel.setLayout(new GridLayout(0,5, 5, 5));
                jobsPanel.add(jobName);
                jobsPanel.add(dockName);
                jobsPanel.add(status);
                jobsPanel.add(options);
                jobsPanel.add(blank);
                
                for (Job jobs: world.getJobs()) {
                    JLabel newName = new JLabel(jobs.getName());
                    String location = "None";
                    
                    if (jobs.jobShip.checkDocked()) {
                        location = jobs.parentPort.getName() + " / " + jobs.parentDock.getName();
                        jobs.noKillFlag = true;
                    } else {
                        location = jobs.parentPort.getName();
                    }
                    
                    JLabel newDockName = new JLabel (location);
                    
                    JProgressBar newStatus = new JProgressBar(0,100);
                    JButton newCancel = new JButton("Cancel");
                    JButton pauseResume = new JButton("Pause / Resume");
                    
                    jobsPanel.add(newName);
                    jobsPanel.add(newDockName);
                    jobsPanel.add(newStatus);
                    jobsPanel.add(newCancel);
                    jobsPanel.add(pauseResume);
                    
                    jobs.setProgressBar(newStatus);
                    
                    jobs.start();
                    
                    newCancel.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            jobs.goFlag = false;
                            jobs.noKillFlag = false;
                        }
                    });
                    
                    pauseResume.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (jobs.goFlag) {
                                jobs.goFlag = false;
                            } else {
                                jobs.goFlag = true;
                            }
                        }
                    }); 
                }
                
                JScrollPane displayScroll3 = new JScrollPane(jobsPanel);
                displayScroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                displayScroll3.setBounds(50, 30, 300, 50);
                displayScroll3.setPreferredSize(new Dimension(800,450));
                mainPanel.add(displayScroll3, BorderLayout.SOUTH);
                ArrayList<Job> jobs = world.getJobs();
                
                revalidate();
                repaint();
            }
        });
    }
    
}
