package com.Research;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class Main {

    //*******************
    //***** Methods *****
    //*******************

    //Creates GUI Menu
    public static void createGUI(){
        //Start GUI
        menu.setVisible(true);
        GUICreated = true;
    }

    //Main method
    public static void main(String[] args) {

        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Windows is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettingsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //Initialize thisDirectory, filenamesFilename and dataFilename
        thisDirectory = System.getProperty("user.dir");
        filenamesFilename = thisDirectory + "\\filenames.txt";
        dataFilename = thisDirectory + "\\data.txt";

        //Get the filenames and tell SettingsGUI
        fetchFilenamesFromFile();

        //Get data and tell SettingsGUI
        fetchDataFromFile();

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

    //VERY IMPORTANT METHOD: OPERATES AS THE ENGINE OF THE PROGRAM - BASICALLY THE "MAIN" METHOD
    //Note: This code is NOT in the Main method because it needs to be called DIRECTLY from the GUI, to avoid Main method running without GUI input
    public static void runTheProgram(){

        //Get rid of GUI menu
        menu.dispose();

        //Determine what data should be used, based on checkboxes in settings window, and apply it to ProfileComparison
        shouldUseFreq();
        shouldUseJitter();
        shouldUseShimmer();

        //Determine what form of report should be used
        shouldUseDescriptive();

        //System.out.println(useDescriptive);

        ProfileComparison.setUseFreq(useFreq);
        ProfileComparison.setUseJitter(useJitter);
        ProfileComparison.setUseShimmer(useShimmer);

        Profile.setUseFreq(useFreq);
        Profile.setUseJitter(useJitter);
        Profile.setUseShimmer(useShimmer);

        //Print filenames
        System.out.println("File for profiles: " + profileFilename);
        System.out.println("File for samples: " + samplesFilename);
        System.out.println("File for report: " + reportFilename);
        System.out.println("File for indices: " + indexFilename);

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
            willWrite = GetData.getBoolean("Write report to report file?", "Report");
            for (Profile currentSample : samples) {

                //Reset List values
                comparisons = new ArrayList<>();
                sumsOfDiffs = new ArrayList<>();

                //Change sample to be compared
                sampleProfile = currentSample;

                //Compare sample to all profiles and get results as a List of ProfileComparison objects
                comparisons = sampleProfile.compareToProfiles(profiles);

                //Put sum of diffs values into sumsOfDiffs
                for (ProfileComparison tempComparison : comparisons) {
                    sumsOfDiffs.add(tempComparison.getSumOfDiffs());
                }

                //Determine which profile most closely matches the sample by determining which Sum of Diffs is lowest
                indexOfLowest = sumsOfDiffs.indexOf(Collections.min(sumsOfDiffs));
                closestMatch = comparisons.get(indexOfLowest);

                //Determine if program was correct. If so, increment "correct" counter by one. If not, increment "incorrect" counter by one. Either way, update percentMatch data.
                if (!closestMatch.getNameDiff() && closestMatch.getPercentMatch() >= minPercentForMatch) { //If the names are NOT DIFFERENT, AND percent match is within values, then program was correct
                    timesCorrect++;

                    //Update values for calculation of average and standard deviation
                    sumOfPercents += closestMatch.getPercentMatch();
                    totalPercents++;
                    percentMatches.add(closestMatch.getPercentMatch());
                    closestMatch.setValid(true);

                } else if (closestMatch.getNameDiff() || closestMatch.getPercentMatch() < minPercentForMatch) { //If names are DIFFERENT, program was incorrect
                    timesIncorrect++;

                    //Update values for calculation of average and standard deviation
                    //Only zeroes because the match was invalid
                    sumOfPercents += 0;
                    totalPercents += 0;
                    closestMatch.setValid(false);

                }

                //Print results to console
                System.out.println("\n\n**********************************************************************\n\nSample Data:\n" + sampleProfile.toString() + "\n\nClosest Match:\n" + profiles.get(indexOfLowest) + "\n\nClosest Match Summary: \n" + closestMatch.toString());

                //Print data to report
                if(willWrite)
                    writeReport();
            }
            //Determine percentage of times the program was correct and print data to report file
            if ((timesCorrect + timesIncorrect) != samples.size()) {
                System.out.println("ERROR. Something went wrong while determining percentage correct.");
                JOptionPane.showMessageDialog(null, "ERROR: Something went wrong while determining percentage correct.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                percentCorrect = (timesCorrect / samples.size()) * 100;

                //Calculate One Proportional Z Interval
                opzi = new OnePropZInt(samples.size(), timesCorrect);
                try {
                    opzi.checkParameters();
                } catch(ParameterNotMetException ex){
                    ex.printStackTrace();
                    //JOptionPane.showMessageDialog(null, "Something went wrong while calculating the One Proportional Z Interval.\nCheck parameters and try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Warning: \"One Proportion Z Interval\" parameter(s) not met.\nResults may be illogical.", "One Proportion Z Interval Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    opziLowerBound = opzi.lowerBound * 100;
                    opziUpperBound = opzi.upperBound * 100;
                }

                average = sumOfPercents / totalPercents;

                //Sum up the value for the numerator in the standard deviation equation
                double sumOfXPlusMean2 = 0; //This is the value that goes in the numerator in the Standard Deviation equation, sigma(x - mean)^2
                for(int sumCount = 0; sumCount < totalPercents; sumCount++){
                    sumOfXPlusMean2 += Math.pow((Math.abs(percentMatches.get(sumCount) - average)), 2);
                }
                standardDeviation = Math.sqrt(sumOfXPlusMean2 / (totalPercents - 1));

                //Print out (some of) the calculations
                System.out.println("\n**********************************************************************\n\nThe program correctly identified " + (int) timesCorrect + " out of " + samples.size() + " samples.");
                System.out.println("The program was correct " + percentCorrect + "% of the time.");

                //Write footer data to report file
                String footerOutput = "";
                NumberFormat percent = NumberFormat.getNumberInstance();
                percent.setMaximumFractionDigits(2);

                footerOutput += "\n\n**********************************************************************\n\nThe program correctly identified " + (int) timesCorrect + " out of " + samples.size() + " samples, \nusing ";
                if(useFreq) {
                    footerOutput += "frequency ";
                }
                if(useFreq && useJitter || useFreq && useShimmer){
                    footerOutput += "& ";
                }
                if(useJitter){
                    footerOutput += "jitter ";
                }
                if(useJitter && useShimmer){
                    footerOutput += "& ";
                }
                if(useShimmer){
                    footerOutput += "shimmer ";
                }
                footerOutput += "data.";
                footerOutput += "\n\nThe program was correct " + percent.format(percentCorrect) + "% of the time.";
                footerOutput += "\n\nWithin a 95% confidence interval, the program will correctly match\n" +
                        " between" + percent.format(opziLowerBound) + "% and " + percent.format(opziUpperBound) + "% of samples.\n\n**********************************************************************";

                if(willWrite) {
                    printToReport(footerOutput);
                }

                //Make Bell Curve (if the user wants to)
                boolean makeCurve = GetData.getBoolean("Would you like to make a bell curve reflecting your data?", "Bell Curve");
                if(makeCurve) {
                    //Create Bell Curve GUI
                    bellCurveGUI.setVisible(true);

                    //NOTE: If user chooses to make the bell curve, the "finish()" method is called by the "makeBellCurve()" method
                } else {
                    finish();
                }
            }
        } else { //...Otherwise, get and handle data manually

            //Get data for sample from user and store it in a profile
            sampleProfile = manualSampleIn();

            //Compare sample to all profiles and get results as a List of ProfileComparison objects
            comparisons = sampleProfile.compareToProfiles(profiles);

            /*Tell ProfileComparison objects "comparisons" what data to use

            ****NOTE: Not used****

            for (ProfileComparison comparison : comparisons) {
                comparison.setUseFreq(useFreq);
                comparison.setUseJitter(useJitter);
                comparison.setUseShimmer(useShimmer);
            }*/

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
            if(GetData.getBoolean("Write report to report file?", "Report"))
                writeReport();

            finish();
        }
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

        //NOTE: the following commented-out lines of code were used for testing, no longer needed
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

        //NOTE: the following commented-out lines of code were used for testing, no longer needed
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
            String sampleName = GetData.getString("What is the name?", "Enter Name");
            String sampleMaxFreqStr = GetData.getString("What is the maximum frequency of the sample?", "Enter Max Frequency");
            String sampleMinFreqStr = GetData.getString("What is the minimum frequency of the sample?", "Enter Min Frequency");
            String sampleAvgFreqStr = GetData.getString("What is the average frequency of the sample?", "Enter Average Frequency");
            String sampleJitterStr = GetData.getString("What is the jitter ratio of the sample?", "Enter Jitter Ratio");
            String sampleShimmerStr = GetData.getString("What is the shimmer value of the sample?", "Enter Shimmer Value");

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

    //Print **any** data to report file
    private static void printToReport(String output){
        if(willWrite) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFilename, true))) {
                //Replace '\n' escape sequence with '\r\n', which writes to a new line IN FILE
                reportOutput = output.replaceAll("\n", "\r\n");

                //Write data to file
                bw.write(reportOutput);

                System.out.println("\n\nSuccessfully wrote data to file: " + reportFilename);

                //Display "Successfully Written" message if auto-input is chosen and finished
                if (useAuto) {
                    if (timesWrittenMessage == (samples.size())) {
                        JOptionPane.showMessageDialog(null, "Successfully wrote data to file: " + reportFilename);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Successfully wrote data to file: " + reportFilename);
                }
                timesWrittenMessage++;
                System.out.println("Number of samples written to file in this run-through: " + timesWrittenMessage);
            } catch (IOException e) {
                //Print error message if exception is caught
                e.printStackTrace();
                System.out.println("Error writing to file: " + reportFilename);
                JOptionPane.showMessageDialog(null, "Error writing to file: " + reportFilename);
            }
        }
    }

    //Write the run summary to the report file
    private static void writeReport(){
        NumberFormat percent = NumberFormat.getNumberInstance();
        percent.setMaximumFractionDigits(2);
        if(useDescriptive){
            printToReport("\n\n**********************************************************************\n\nSample Data:\n" + sampleProfile.toString() + "\n\nClosest Match:\n" + profiles.get(indexOfLowest) + "\n\nClosest Match Summary: \n" + closestMatch.toString());
        } else {
            printToReport("\n\n**********************************************************************\n\nSample:\n" + sampleProfile.getName() + "\n\nClosest Match:\n" + profiles.get(indexOfLowest).getName() + "\n\nThe sample matches the profile by " + percent.format(closestMatch.getPercentMatch()) + "%" + "\nMatch Validity: " + closestMatch.isValid());
        }
    }

    //Open files as prompted by Settings
    public static void openProfileFile(){
        File file = new File(profileFilename);
        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            JOptionPane.showMessageDialog(null, "ERROR: Desktop is not supported", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) try {
                desktop.open(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening file: " + file.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    public static void openIndicesFile(){
        File file = new File(indexFilename);
        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            JOptionPane.showMessageDialog(null, "ERROR: Desktop is not supported", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) try {
                desktop.open(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening file: " + file.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }}
    public static void openSamplesFile(){
        File file = new File(samplesFilename);
        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            JOptionPane.showMessageDialog(null, "ERROR: Desktop is not supported", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) try {
                desktop.open(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening file: " + file.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }}
    public static void openReportFile(){
        File file = new File(reportFilename);
        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            JOptionPane.showMessageDialog(null, "ERROR: Desktop is not supported", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) try {
                desktop.open(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening file: " + file.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    //Getters: Return filenames (used by GUI)
    public static String getProfileFilename(){ return profileFilename; }
    public static String getIndexFilename(){ return indexFilename; }
    public static String getReportFilename(){ return reportFilename; }
    public static String getSampleFilename(){ return samplesFilename; }

    //Return minPercentForMatch
    public static Double getMinPercentForMatch(){ return minPercentForMatch;}

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
    private static void shouldUseDescriptive(){ useDescriptive = SettingsGUI.checkUseDescriptive(); }

    //Set start parameter (used by GUI)
    public static void setStart(boolean start){ startProgram = start; }

    //Set if program is finished (used by GUI) (NOTE: This is just a precaution, the Menu GUI calls the System.exit method itself)
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
        /*String sampleName = null;
        String sampleMaxFreqStr = null;
        String sampleMinFreqStr = null;
        String sampleAvgFreqStr = null;
        String sampleJitterStr = null;
        String sampleShimmerStr = null;
        Profile sampleProfile = null;*/
    }

    //Finish each iteration of "engine loop"
    private static void finish(){
        //Increment counter to avoid duplication of data in next iteration of "engine" loop
        timesRun++;

        //Create new GUI to start again
        createGUI();

        //Restart "Successfully written" message counter
        timesWrittenMessage = 0;
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

    //Methods that manage data in "data.txt" file
    //Sets data variables
    public static void setMinPercentForMatch(Double minimumPercentForMatch){
        minPercentForMatch = minimumPercentForMatch;
    }
    //Reads in data from "data.txt" file
    private static void fetchDataFromFile(){

        //Initialize and use dataReader
        dataReader = new CSVReader(dataFilename);
        dataReader.setSplitString("new line");
        String[] tempData = dataReader.getValues();

        //ZERO IS THE INDEX OF THE minPercentForDouble IN THE DATA.TXT FILE
        if(DataProcessor.isDouble(tempData[0])) {
            //Set minPercentForMatch
            minPercentForMatch = DataProcessor.convertToDouble(tempData[0]);
        } else {
            JOptionPane.showMessageDialog(null, "Error reading the Minimum Percent for Match from data.txt file");
        }
    }
    //Writes to "data" file
    public static void changeData(String[] NewData){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFilename, false))) {

            String newData = "";//Will store new data as single String, with "\r\n"'s

            //Copy values of NewData into one string, newData, with "\r\n"'s
            for(int i = 0; i < NewData.length; i++) {
                if (i == 0) {
                    newData += NewData[i];
                } else {
                    newData += "\r\n" + NewData[i];
                }
            }

            //Write data to file
            bw.write(newData);

            //Display confirmation message
            System.out.println("\n\nSuccessfully changed data");
            JOptionPane.showMessageDialog(null, "Successfully changed data");


        } catch (IOException e) {
            //Print error message if exception is caught
            e.printStackTrace();
            System.out.println("Error changing filename in file: " + filenamesFilename);
            JOptionPane.showMessageDialog(null, "Error changing filename in file: " + filenamesFilename);
        }

    }

    //Makes a Bell Curve based on data
    public static void makeBellCurve(){
        if(!bellCurveGUI.isCancelled()) {
            StdDraw.setCanvasSize(600, 400);
            StdDraw.setXscale(bellCurveGUI.getMinX(), bellCurveGUI.getMaxX());
            StdDraw.setYscale(bellCurveGUI.getMinY(), bellCurveGUI.getMaxY());

            BellCurve.plot(bellCurveGUI.getMinX(), bellCurveGUI.getMaxX(), bellCurveGUI.getMinY(), bellCurveGUI.getMaxY(), bellCurveGUI.isDrawSDLines(), average, standardDeviation);
            System.out.println(average + " , " + standardDeviation);
        }
        finish();
    }




//**********************************************************************************************************************

    //********************************
    //***** Variable Declaration *****
    //********************************

    //Objects pertaining to the accessing of data from csv files
    //Final variables that determine the location of each filename within the "tempFilenames" array in the "fetchFilenamesFromFile()" method
    public static final int PROFILEFILENAMEINDEX = 0;
    public static final int INDEXFILENAMEINDEX = 1;
    public static final int SAMPLEFILENAMEINDEX = 2;
    public static final int REPORTFILENAMEINDEX = 3;

    private static Double minPercentForMatch; //Determines minimum percent to be considered a match

    private static String profileFilename; //File location of csv file for profiles
    private static String indexFilename; //File location of indices csv file
    private static String thisDirectory ;//Stores directory of this class as a String. Used only by filenamesFilename (below)

    private static String filenamesFilename;//File location of txt file that contains the filenames of other files
    private static String dataFilename;//File location of txt file that contains various data, such as minPercentForMatch
    /***NOTE: The files to which these variable relate MUST be stored in the same folder as the rest of the project,
     otherwise the program will have issues. See initialization in Main method (uses thisDirectory (above))*/

    private static CSVReader filenameReader; //Reads in the filenames for other Readers
    private static CSVReader dataReader; //Reads in the data from data.txt
    private static CSVReader cr; //Reads in data from csv file for profiles
    private static CSVReader indexReader; //Reads in indices from indices csv file

    //Objects pertaining to the storing of data from the csv files
    private static String[] initialArray; //Stores data for profiles from csv as Strings
    private static String[] initialIndices; //Stores indices from indices csv file as Strings

    //Lists for storing data
    private static List<List> tempValues = new ArrayList<>(); //Temporarily stores data for profiles in a List of Lists
    private static List<List> finalValues = new ArrayList<>(); //Stores final, processed (formatted) data for profiles in a List of Lists
    private static List<String> sampleList = new ArrayList<>(); //Stores sample values as Strings
    private static List<Integer> finalIndices = new ArrayList<>(); // Stores indices values from indices.csv, as Integer objects
    private static List<Profile> profiles = new ArrayList<>(); //Array of profiles stored in the program - data is accessed from the csv file
    private static List<ProfileComparison> comparisons = new ArrayList<>(); //List of ProfileComparison objects that store the data from each of the comparisons between the sample and the known profiles
    private static List<Double> sumsOfDiffs = new ArrayList<>(); //List that contains all of the sums of differences of the ProfileComparisons in the comparisons list
    private static List<Profile> samples = new ArrayList<>(); //Array of samples stored in the program - data is accessed from the csv file WHEN DATA IS ENTERED AUTOMATICALLY

    //Where Sample is stored for
    private static Profile sampleProfile;

    //Objects regarding the closest match
    private static int indexOfLowest; //Stores the index of the lowest sumOfDiffs in the sumsOfDiffs list
    private static ProfileComparison closestMatch; //Stores the comparison object of the closest match to the sample

    //Objects for writing data to report file
    private static String reportFilename;
    private static String reportOutput;
    private static boolean useDescriptive;
    private static boolean willWrite;

    //Objects pertaining to the use of automatic data entry
    private static String samplesFilename;
    private static boolean useAuto = true;
    private static double timesCorrect = 0.0;
    private static double timesIncorrect = 0.0;
    private static double percentCorrect = 0.0;
    private static OnePropZInt opzi;
    private static double opziLowerBound = -1;
    private static double opziUpperBound = -1;

    //Objects pertaining to the creation of the Bell Curve
    private static double sumOfPercents = 0;
    private static double average = 0;
    private static double standardDeviation = -1;
    private static ArrayList<Double> percentMatches = new ArrayList<>();
    private static double totalPercents = 0;
    private static BellCurveGUI bellCurveGUI = new BellCurveGUI();

    //Variables pertaining to the use of different points of data
    private static boolean useFreq = true;
    private static boolean useJitter = true;
    private static boolean useShimmer = true;

    //Variables pertaining to the creation and use of GUI
    private static MenuGUI menu = new MenuGUI();
    private static boolean finished = false;
    private static boolean GUICreated = false;
    private static boolean startProgram = false;

    //Variables pertaining to the maintenance of the "engine" loop
    private static int timesRun = 0;
    private static int timesWrittenMessage = 0;
}