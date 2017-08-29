package com.Research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    //Objects pertaining to the accessing of data from csv files
    static String filename = "C:/Users/Taylor/Desktop/NewValues.txt"; //File location of csv file for profiles
    static String indexFilename = "C:/Users/Taylor/Desktop/indices.txt"; //File location of indices csv file
    static CSVReader cr = new CSVReader(filename); //Reads in data from csv file for profiles
    static CSVReader indexReader = new CSVReader(indexFilename); //Reads in indices from indices csv file

    //Objects pertaining to the storing of data from csv files
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

    //Data for Sample
    static String sampleName;
    static String sampleMaxFreqStr;
    static String sampleMinFreqStr;
    static String sampleAvgFreqStr;
    static Profile sampleProfile;

    static int indexOfLowest; //Stores the index of the lowest sumOfDiffs in the sumsOfDiffs list
    static ProfileComparison closestMatch;

    //Main method
    public static void main(String[] args) {

        //Read in data from csv file and store them in arrays
        readAndStore();

        //Read and store index data, and create profiles based on data
        createProfiles();

        //Print everything out (before sample entry)
        printOutput();

        //Get data for sample from user and store it in a profile
        sampleProfile = enterSample();

        //Compare sample to all profiles and get results as a List of ProfileComparison objects
        comparisons = sampleProfile.compareToProfiles(profiles);

        //Put sum of diffs values into sumsOfDiffs
        for (ProfileComparison tempComparison : comparisons) {
            sumsOfDiffs.add(tempComparison.getSumOfDiffs());
        }

        //Determine which profile most closely matches the sample by determining which Sum of Diffs is lowest
        indexOfLowest = sumsOfDiffs.indexOf(Collections.min(sumsOfDiffs));
        closestMatch = comparisons.get(indexOfLowest);

        //Print the data for the closest match
        System.out.println("\n\nClosest match: \n"  + profiles.get(indexOfLowest));
        System.out.println("\nClosest match summary: " + closestMatch.toString());

        //Print the sample data
        System.out.println("\nSample:");
        sampleProfile.printSummary();

    }

    //Reads in data from csv file and stores them in Lists
    //Takes no user input from keyboard or mouse
    private static void readAndStore(){
        //Read in values from csv, separated by "new"
        cr.setSplitString("new");

        //Get values from CSVReader
        initialArray = cr.getValues();

        //Add values to tempValues
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
        //Get indices values from indices.csv file
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
    private static Profile enterSample(){

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



}

