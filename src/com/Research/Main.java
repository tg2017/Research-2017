package com.Research;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    //Objects pertaining to the accessing of data from csv files
    static String filename = "C:/Users/Taylor/Desktop/2017 Project Values - Sheet1 - Copy.csv"; //File location of csv file for profiles
    static String indexFilename = "C:/Users/Taylor/Desktop/indices.txt"; //File location of indices csv file
    static CSVReader cr = new CSVReader(filename); //Reads in data from csv file for profiles
    static CSVReader indexReader = new CSVReader(indexFilename); //Reads in indices from indices csv file

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
    static String sampleName;
    static String sampleMaxFreqStr;
    static String sampleMinFreqStr;
    static String sampleAvgFreqStr;
    static Profile sampleProfile;

    //Objects regarding the closest match
    static int indexOfLowest; //Stores the index of the lowest sumOfDiffs in the sumsOfDiffs list
    static ProfileComparison closestMatch; //Stores the comparison object of the closest match to the sample

    //Objects for writing data to report file
    static String reportFile = "C:/Users/Taylor/Desktop/Matches.txt";
    static String repOutput;

    //Objects pertaining to the use of automatic data entry
    static String sampleCSVFilename = "C:/Users/Taylor/Desktop/Samples.csv";
    static boolean useAuto = true;
    static double timesCorrect = 0.0;
    static double timesIncorrect = 0.0;
    static double percentCorrect;



    //Main method
    public static void main(String[] args) {

        //Read in data from csv file and store them in arrays
        readAndStore();

        //Read and store index data, and create profiles based on data
        createProfiles();

        //Print everything out (before sample entry)
        printOutput();

        //Get sample data and compare it to profiles
        if(useAuto) { //If user chooses to enter data automatically, do so...
            autoSampleIn();
            for(int autoCounter = 0; autoCounter < samples.size(); autoCounter++){

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
                } else if (closestMatch.getNameDiff()){ //If names are DIFFERENT, program was incorrect
                    timesIncorrect++;
                }
            }
            //Determine percentage of times the program was correct
            if((timesCorrect + timesIncorrect)!= samples.size()){
                System.out.println("ERROR. Something went wrong while determining percentage correct.");
            } else {
                percentCorrect = (timesCorrect / samples.size()) * 100;
                System.out.println("\n**********************************************************************\n\nThe program correctly identified " + (int)timesCorrect + " out of " + samples.size() + " samples.");
                System.out.println("The program was correct " + percentCorrect + "% of the time.");
                printToReport("\n\n**********************************************************************\n\nThe program correctly identified " + (int)timesCorrect + " out of " + samples.size() + " samples.\nThe program was correct " + percentCorrect + "% of the time.\n\n**********************************************************************");
            }


        } else { //...Otherwise, get and handle data manually
            //Get data for sample from user and store it in a profile
            sampleProfile = manualSampleIn();

            //Compare sample to all profiles and get results as a List of ProfileComparison objects
            comparisons = sampleProfile.compareToProfiles(profiles);

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

        //Get data from user
        sampleName = GetData.getString("What is the name?", "Enter Name");
        sampleMaxFreqStr = GetData.getString("What is the maximum frequency of the sample?", "Enter Max Frequency");
        sampleMinFreqStr = GetData.getString("What is the minimum frequency of the sample?", "Enter Min Frequency");
        sampleAvgFreqStr = GetData.getString("What is the average frequency of the sample?", "Enter Average Frequency");

        //Add values to list
        sampleList.add(sampleName);
        sampleList.add(sampleMaxFreqStr);
        sampleList.add(sampleMinFreqStr);
        sampleList.add(sampleAvgFreqStr);

        //Create and return sample profile
        return new Profile(sampleList, finalIndices);

    }

    //Gets sample data automatically from csv file and stores it in Profile "samples" object
    private static void autoSampleIn(){

        //CSVReader for sample auto-entry
        CSVReader sampleReader = new CSVReader(sampleCSVFilename);

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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile, true))) {
            //Take in String from user (String "output" parameter) and store in repOutputTemp
            String repOutputTemp = output;

            //Replace '\n' escape sequence with '\r\n', which writes to a new line IN FILE
            repOutput = repOutputTemp.replaceAll("\n", "\r\n");

            //Write data to file
            bw.write(repOutput);

            System.out.println("\n\nSuccessfully wrote data to file: " + reportFile);

        } catch (IOException e) {
            //Print error message if exception is caught
            e.printStackTrace();
            System.out.println("Error writing to file: " + reportFile);
        }
    }
}

