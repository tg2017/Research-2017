package com.Research;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    //***************
    //*** Methods ***
    //***************

    //Creates GUI Menu
    public static void createGUI(){
        //Start GUI
        menu.setVisible(true);
        GUICreated = true;
    }

    //VERY IMPORTANT METHOD: OPERATES AS THE ENGINE OF THE PROGRAM - BASICALLY A MAIN METHOD
    //Note: This code is NOT in the Main method because it needs to be called DIRECTLY from the GUI, to avoid Main method running without GUI input
    public static void runTheProgram(){

        //Get rid of GUI menu
        menu.dispose();

        //Determine what data should be used, based on checkboxes in settings window, and apply it to ProfileComparison
        shouldUseFreq();
        shouldUseJitter();
        shouldUseShimmer();

        ProfileComparison.setUseFreq(useFreq);
        ProfileComparison.setUseJitter(useJitter);
        ProfileComparison.setUseShimmer(useShimmer);

        //Print filenames
        System.out.println("File for profiles: " + profileFilename);
        System.out.println("File for indices: " + indexFilename);
        System.out.println("File for samples: " + samplesFilename);
        System.out.println("File for report: " + reportFilename);

        //Initialize CSV Readers with filenames
        cr = new CSVReader(profileFilename);
        indexReader = new CSVReader(indexFilename);



        //"Engine" loop that continues program by returning to GUI Menu after completion:
        if (timesRun < 1) {
            //Read in data from csv file and store them in arrays
            readAndStore();

            //Read and store index data, and create profiles based on data
            createProfiles();

            //Print everything out (before sample entry)
            printOutput();
        }

        //Get sample data and compare it to profiles
        //See if "Automatically Get Sample Data" is selected in Settings window GUI
        useAuto = SettingsGUI.checkUseAuto();
        if (useAuto) { //If user chooses to enter data automatically, do so...
            autoSampleIn();
            for (int autoCounter = 0; autoCounter < samples.size(); autoCounter++) {

                //Reset List values
                comparisons = new ArrayList<>();
                sumsOfDiffs = new ArrayList<>();

                //Change sample to be compared
                sampleProfile = samples.get(autoCounter);

                //Compare sample to all profiles and get results as a List of ProfileComparison objects
                comparisons = sampleProfile.compareToProfiles(profiles);

                //Put sum of diffs values into sumsOfDiffs
                for (ProfileComparison tempComparison : comparisons) {
                    sumsOfDiffs.add(tempComparison.getSumOfDiffs());
                }

                //Determine which profile most closely matches the sample by determining which Sum of Diffs is lowest
                indexOfLowest = sumsOfDiffs.indexOf(Collections.min(sumsOfDiffs));
                closestMatch = comparisons.get(indexOfLowest);

                //Print results to console
                System.out.println("\n\n**********************************************************************\n\nSample Data:\n" + sampleProfile.toString() + "\n\nClosest Match:\n" + profiles.get(indexOfLowest) + "\n\nClosest Match Summary: \n" + closestMatch.toString());

                //Print data to report
                printToReport("\n\n**********************************************************************\n\nSample Data:\n" + sampleProfile.toString() + "\n\nClosest Match:\n" + profiles.get(indexOfLowest) + "\n\nClosest Match Summary: \n" + closestMatch.toString());

                //Determine if program was correct. If so, increment "correct" counter by one. If not, increment "incorrect" counter by one.
                if (!closestMatch.getNameDiff()) { //If the names are NOT DIFFERENT, program was correct
                    timesCorrect++;
                } else if (closestMatch.getNameDiff()) { //If names are DIFFERENT, program was incorrect
                    timesIncorrect++;
                }
            }
            //Determine percentage of times the program was correct
            if ((timesCorrect + timesIncorrect) != samples.size()) {
                System.out.println("ERROR. Something went wrong while determining percentage correct.");
                JOptionPane.showMessageDialog(null,"ERROR. Something went wrong while determining percentage correct.");
            } else {
                percentCorrect = (timesCorrect / samples.size()) * 100;
                System.out.println("\n**********************************************************************\n\nThe program correctly identified " + (int) timesCorrect + " out of " + samples.size() + " samples.");
                System.out.println("The program was correct " + percentCorrect + "% of the time.");
                printToReport("\n\n**********************************************************************\n\nThe program correctly identified " + (int) timesCorrect + " out of " + samples.size() + " samples.\nThe program was correct " + percentCorrect + "% of the time.\n\n**********************************************************************");
            }


        } else { //...Otherwise, get and handle data manually
            //Get data for sample from user and store it in a profile
            sampleProfile = manualSampleIn();

            //Compare sample to all profiles and get results as a List of ProfileComparison objects
            comparisons = sampleProfile.compareToProfiles(profiles);

            //Tell ProfileComparison objects "comparisons" what data to use
            for(int i=0; i<comparisons.size(); i++){
                comparisons.get(i).setUseFreq(useFreq);
                comparisons.get(i).setUseJitter(useJitter);
                comparisons.get(i).setUseShimmer(useShimmer);
            }

            //Put sum of diffs values into sumsOfDiffs
            for (ProfileComparison tempComparison : comparisons) {
                sumsOfDiffs.add(tempComparison.getSumOfDiffs());
            }

            //Determine which profile most closely matches the sample by determining which Sum of Diffs is lowest
            indexOfLowest = sumsOfDiffs.indexOf(Collections.min(sumsOfDiffs));
            closestMatch = comparisons.get(indexOfLowest);

            //Print the sample data to console
            System.out.println("\nSample:");
            sampleProfile.printSummary();

            //Print the data for the closest match to console
            System.out.println("\n\nClosest match: \n" + profiles.get(indexOfLowest));
            System.out.println("\nClosest match summary: " + closestMatch.toString());

            //Print data to report
            printToReport("\n\n**********************************************************************\n\nSample Data:\n" + sampleProfile.toString() + "\n\nClosest Match:\n" + profiles.get(indexOfLowest) + "\n\nClosest Match Summary: \n" + closestMatch.toString());
        }

        //Increment counter to avoid duplication of data in next iteration of "engine" loop
        timesRun++;

        //Create new GUI to start again
        createGUI();

        //Restart "Successfully written" message counter
        timesWrittenMessage = 0;
    }

    //Main method
    public static void main(String[] args) {

        //Initialize thisDirectory and filenamesFilename
        thisDirectory = System.getProperty("user.dir");
        filenamesFilename = thisDirectory + "\\filenames.txt";

        //Get the filenames and tell SettingsGUI
        fetchFilenamesFromFile();

        //Initially create SettingsGUI, to make Main class aware of data present
        SettingsGUI settings = new SettingsGUI();
        settings.setVisible(false);

        //Tell SettingsGUI about filename data
        SettingsGUI.setFilenames(getProfileFilename(), getIndexFilename(), getSampleFilename(), getReportFilename());

        //Reset checkboxes to default values - essentially, make settings forget this ever happened
        SettingsGUI.setAlreadyUsed(false);

        //Get rid of settings GUI window
        settings.dispose();

        //Create the GUI
        createGUI();

        System.out.println("Main Method - GUI created");

    }

    //Reads in data from csv file and stores them in Lists
    //Takes no user input from keyboard or mouse
    private static void readAndStore(){
        //Read in values from csv, separated by each new line
        cr.setSplitString("new line");

        //Get values from CSVReader
        initialArray = cr.getValues();

        //Split values by comma and add to tempValues
        for (String initialArrayElement : initialArray) {
            List parsedArray = Arrays.asList(initialArrayElement.split("\\s*,\\s*"));
            tempValues.add(parsedArray);
        }

        //Remove blank ("") values from arrays and store new arrays in finalValues
        for (List tempElement : tempValues) {
            finalValues.add(DataProcessor.removeBlanks(tempElement));
        }

    }

    //Reads in and stores data from indices csv file, and uses those indices and data from other csv file to create profiles
    //Takes no direct user input
    private static void createProfiles(){
        //Get indices values from indices.csv file, split by comma
        indexReader.setSplitString(",");
        initialIndices = indexReader.getValues();

        //Convert indices to Integers and store them in finalIndices
        for (String index : initialIndices){
            if(DataProcessor.isInteger(index)){
                finalIndices.add(DataProcessor.convertToInteger(index));
            }
        }

        //Create and store the Profiles
        for (List profileArray : finalValues){
            profiles.add(new Profile(profileArray, finalIndices));
        }
    }

    //Prints out data from profiles and comparison
    private static void printOutput(){
        //Print summaries of Profiles
        for (Profile printTest : profiles){
            printTest.printSummary();
            System.out.println();
            System.out.println();
        }

        //Print test values
        //Print temp values
        //System.out.println("Temp Values ArrayList:\n" + tempValues);
        //Example use:
        //To print the 2nd array
        //System.out.println("\nThe 2nd temp array:\n" + tempValues.get(1));

        //To print the fourth value of the second array
        //System.out.println("\nThe 4th value of the 2nd temp array:\n" + tempValues.get(1).get(3));

        System.out.println();

        //Print final values
        System.out.println("Final Values ArrayList:\n" + finalValues);

        //Example use:
        //To print the 2nd array
        //System.out.println("\nThe 2nd array:\n" + finalValues.get(1));

        //To print the fourth value of the second array
        //System.out.println("\nThe 4th value of the 2nd array:\n" + finalValues.get(1).get(3));

        //Comparison test
        //ProfileComparison comparisonTest = profiles.get(0).compareToProfile(profiles.get(5));
        //System.out.println("\n");
        //comparisonTest.printSummary();
    }

    //Gets sample data from user and creates sample profile
    private static Profile manualSampleIn(){
        Profile theSamples = null; //Initialize with null to satisfy "might not be initialized"

        try { //Try/catch deals with pressing of "cancel" button
            //Get data from user (manually)
            sampleName = GetData.getString("What is the name?", "Enter Name");
            sampleMaxFreqStr = GetData.getString("What is the maximum frequency of the sample?", "Enter Max Frequency");
            sampleMinFreqStr = GetData.getString("What is the minimum frequency of the sample?", "Enter Min Frequency");
            sampleAvgFreqStr = GetData.getString("What is the average frequency of the sample?", "Enter Average Frequency");
            sampleJitterStr = GetData.getString("What is the jitter ratio of the sample?", "Enter Jitter Ratio");
            sampleShimmerStr = GetData.getString("What is the shimmer value of the sample?", "Enter Shimmer Value");

            //Add values to list
            sampleList.add(sampleName);
            sampleList.add(sampleMaxFreqStr);
            sampleList.add(sampleMinFreqStr);
            sampleList.add(sampleAvgFreqStr);
            sampleList.add(sampleJitterStr);
            sampleList.add(sampleShimmerStr);
            theSamples = new Profile(sampleList, finalIndices);
        } catch (Exception ex){
            ex.printStackTrace();
            //Properly exit program
            JOptionPane.showMessageDialog(null, "Cancelled. Leaving program");
            System.exit(3);
        }
        //Create and return sample profile
        return theSamples;
    }

    //Gets sample data automatically from csv file and stores it in Profile "samples" object
    private static void autoSampleIn(){

        //CSVReader for sample auto-entry
        CSVReader sampleReader = new CSVReader(samplesFilename);

        //Temp arrays to store values
        String[] initialSampleArray;
        List<List> tempSampleValues = new ArrayList<>();

        //Array to store sample "Profile" objects in
        List<List> finalSamples = new ArrayList<>();

        //Get data from csv file with sample data
        sampleReader.setSplitString("new line");
        initialSampleArray = sampleReader.getValues();

        //Split values by comma and add to tempSampleValues
        for(String initialSampleArrayElement : initialSampleArray){
            List parsedSampleArray = Arrays.asList(initialSampleArrayElement.split("\\s*,\\s*"));
            tempSampleValues.add(parsedSampleArray);
        }

        //Remove blank ("") values from arrays and store new arrays in finalValues
        for (List tempSampleElement : tempSampleValues) {
            finalSamples.add(DataProcessor.removeBlanks(tempSampleElement));
        }

        //Create and store the samnples as Profile objects
        for (List sampleArray : finalSamples){
            samples.add(new Profile(sampleArray, finalIndices));
        }
    }

    //Print data to report file
    private static void printToReport(String output){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFilename, true))) {
            //Take in String from user (String "output" parameter) and store in repOutputTemp
            String repOutputTemp = output;

            //Replace '\n' escape sequence with '\r\n', which writes to a new line IN FILE
            reportOutput = repOutputTemp.replaceAll("\n", "\r\n");

            //Write data to file
            bw.write(reportOutput);

            System.out.println("\n\nSuccessfully wrote data to file: " + reportFilename);

            //Display "Successfully Written" message if auto-input is chosen and finished
            if(useAuto) {
                if (timesWrittenMessage == (samples.size()-1)) {
                    JOptionPane.showMessageDialog(null, "Successfully wrote data to file: " + reportFilename);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Successfully wrote data to file: " + reportFilename);
            }
            timesWrittenMessage++;
            System.out.println("Number of samples written to file in this runthrough: " + timesWrittenMessage);

        } catch (IOException e) {
            //Print error message if exception is caught
            e.printStackTrace();
            System.out.println("Error writing to file: " + reportFilename);
            JOptionPane.showMessageDialog(null, "Error writing to file: " + reportFilename);
        }
    }

    //Getters: Return filenames (used by GUI)
    public static String getProfileFilename(){ return profileFilename; }
    public static String getIndexFilename(){ return indexFilename; }
    public static String getReportFilename(){ return reportFilename; }
    public static String getSampleFilename(){ return samplesFilename; }

    //Setters: Set filenames (used by GUI)
    public static void setProfileFilename(String newFilename) { profileFilename = newFilename; }
    public static void setIndexFilename(String newIndexFilename) { indexFilename = newIndexFilename; }
    public static void setReportFilename(String newReportFilename) { reportFilename = newReportFilename; }
    public static void setSampleFilename(String newSampleFilename) { samplesFilename = newSampleFilename; }

    //Set automatic input (used by GUI)
    public static void setAutoInput(boolean autoIn){
        useAuto = autoIn;
    }

    //Set data to use (used by GUI)
    public static void setUseFrequency(boolean useIt){ useFreq = useIt; }
    public static void setUseJitter(boolean useIt){ useJitter = useIt; }
    public static void setUseShimmer(boolean useIt){ useShimmer = useIt; }

    //Check data to be used
    private static void shouldUseFreq(){ useFreq = SettingsGUI.checkUseFreq();}
    private static void shouldUseJitter(){ useJitter = SettingsGUI.checkUseJitter(); }
    private static void shouldUseShimmer(){ useShimmer = SettingsGUI.checkUseShimmer(); }

    //Set start parameter (used by GUI)
    public static void setStart(boolean start){ startProgram = start; }

    //Set if program is finished (used by GUI) (NOTE: This is just a precaution, the GUI calls the System.exit method itself
    public static void setFinished(boolean done){ finished = done; }

    //Sets value that determines whether the GUI has already been created or not
    public static void setGUICreated(boolean created){
        GUICreated = created;
    }

    //Reset all variables
    public static void reset(){
        timesCorrect = 0.0;
        timesIncorrect = 0.0;
        reportOutput = "";
        finished = false;
        GUICreated = false;
        startProgram = false;
        timesWrittenMessage = 0;
        comparisons = new ArrayList<>(); //List of ProfileComparison objects that store the data from each of the comparisons between the sample and the known profiles
        sumsOfDiffs = new ArrayList<>(); //List that contains all of the sums of differences of the ProfileComparisons in the comparisons list
        samples = new ArrayList<>(); //Array of samples stored in the program - data is accessed from the csv file WHEN DATA IS ENTERED AUTOMATICALLY
        sampleList = new ArrayList<>(); //Stores sample values as Strings
        String sampleName = null;
        String sampleMaxFreqStr = null;
        String sampleMinFreqStr = null;
        String sampleAvgFreqStr = null;
        String sampleJitterStr = null;
        String sampleShimmerStr = null;
        Profile sampleProfile = null;
    }

    //Methods that manage filenames
    //Sets filename variables
    private static void setFilenames(String filenameProfiles, String filenameIndices, String filenameSamples, String filenameReport){
        profileFilename = filenameProfiles;
        indexFilename = filenameIndices;
        samplesFilename = filenameSamples;
        reportFilename = filenameReport;
        //filenamesFilename = filenameFilenames;
    }
    //Reads in filenames from "filenamesFilename" file
    private static void fetchFilenamesFromFile(){

        //Initialize and use filenameReader
        filenameReader = new CSVReader(filenamesFilename);
        filenameReader.setSplitString("new line");
        String[] tempFilenames = filenameReader.getValues();


        String filenameProfiles = tempFilenames[PROFILEFILENAMEINDEX];
        String filenameIndices = tempFilenames[INDEXFILENAMEINDEX];
        String filenameSamples = tempFilenames[SAMPLEFILENAMEINDEX];
        String filenameReport = tempFilenames[REPORTFILENAMEINDEX];

        setFilenames(filenameProfiles, filenameIndices, filenameSamples, filenameReport);

    }

    //Writes to "filenamesFilename" file
    public static void changeFilenames(String[] newFileNames){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filenamesFilename, false))) {

            String newFilenames= "";//Will store new filenames as single String, with "\r\n"'s

            //Copy values of newFileNames into one string, newFilenames, with "\r\n"'s
            for(int i = 0; i < newFileNames.length; i++) {
                if (i == 0) {
                    newFilenames += newFileNames[i];
                } else {
                    newFilenames += "\r\n" + newFileNames[i];
                }
            }

            //Write data to file
            bw.write(newFilenames);

            //Display confirmation message
            System.out.println("\n\nSuccessfully changed filename");
            JOptionPane.showMessageDialog(null, "Successfully changed filename");


        } catch (IOException e) {
            //Print error message if exception is caught
            e.printStackTrace();
            System.out.println("Error changing filename in file: " + filenamesFilename);
            JOptionPane.showMessageDialog(null, "Error changing filename in file: " + filenamesFilename);
        }

    }



//**********************************************************************************************************************

    //****************************
    //*** Variable Declaration ***
    //****************************

    //Objects pertaining to the accessing of data from csv files
    //Final variables that determine the location of each filename within the "tempFilenames" array in the "fetchFilenamesFromFile()" method
    public static final int PROFILEFILENAMEINDEX = 0;
    public static final int INDEXFILENAMEINDEX = 1;
    public static final int SAMPLEFILENAMEINDEX = 2;
    public static final int REPORTFILENAMEINDEX = 3;

    static String profileFilename; //File location of csv file for profiles
    static String indexFilename; //File location of indices csv file
    static String thisDirectory ;//Stores directory of this class as a String. Used only by filenamesFilename (below)

    static String filenamesFilename;/*File location of txt file that contains the filenames of other files
        ***NOTE: The file to which this variable relates MUST be stored in the same folder as the rest of the project,
        otherwise the program will have issues. See initialization in Main method (uses thisDirectory (above))*/

    static CSVReader filenameReader; //Reads in the filenames for other Readers
    static CSVReader cr; //Reads in data from csv file for profiles
    static CSVReader indexReader; //Reads in indices from indices csv file

    //Objects pertaining to the storing of data from the csv files
    static String[] initialArray; //Stores data for profiles from csv as Strings
    static String[] initialIndices; //Stores indices from indices csv file as Strings

    //Lists for storing data
    static List<List> tempValues = new ArrayList<>(); //Temporarily stores data for profiles in a List of Lists
    static List<List> finalValues = new ArrayList<>(); //Stores final, processed (formatted) data for profiles in a List of Lists
    static List<String> sampleList = new ArrayList<>(); //Stores sample values as Strings
    static List<Integer> finalIndices = new ArrayList<>(); // Stores indices values from indices.csv, as Integer objects
    static List<Profile> profiles = new ArrayList<>(); //Array of profiles stored in the program - data is accessed from the csv file
    static List<ProfileComparison> comparisons = new ArrayList<>(); //List of ProfileComparison objects that store the data from each of the comparisons between the sample and the known profiles
    static List<Double> sumsOfDiffs = new ArrayList<>(); //List that contains all of the sums of differences of the ProfileComparisons in the comparisons list
    static List<Profile> samples = new ArrayList<>(); //Array of samples stored in the program - data is accessed from the csv file WHEN DATA IS ENTERED AUTOMATICALLY

    //Data for Sample
    private static String sampleName;
    private static String sampleMaxFreqStr;
    private static String sampleMinFreqStr;
    private static String sampleAvgFreqStr;
    private static String sampleJitterStr;
    private static String sampleShimmerStr;
    private static Profile sampleProfile;

    //Objects regarding the closest match
    static int indexOfLowest; //Stores the index of the lowest sumOfDiffs in the sumsOfDiffs list
    static ProfileComparison closestMatch; //Stores the comparison object of the closest match to the sample

    //Objects for writing data to report file
    static String reportFilename;
    static String reportOutput;

    //Objects pertaining to the use of automatic data entry
    private static String samplesFilename;
    private static boolean useAuto = true;
    private static double timesCorrect = 0.0;
    private static double timesIncorrect = 0.0;
    private static double percentCorrect = 0.0;

    //Variables pertaining to the use of different points of data
    private static boolean useFreq = true;
    private static boolean useJitter = true;
    private static boolean useShimmer = true;

    //Variables pertaining to the creation and use of GUI
    static  MenuGUI menu = new MenuGUI();
    static boolean finished = false;
    static boolean GUICreated = false;
    static boolean startProgram = false;

    //Variables pertaining to the maintenance of the "engine" loop
    static int timesRun = 0;
    static int timesWrittenMessage = 0;



}