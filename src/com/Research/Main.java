package com.Research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    //Objects pertaining to the accessing of data from csv files
    static String filename = "C:/Users/Taylor/Desktop/joe.csv"; //File location of csv file for profiles
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

    //Data for Sample
    static String sampleName;
    static String sampleMaxFreqStr;
    static String sampleMinFreqStr;
    static String sampleAvgFreqStr;
    static String sampleMaxVolStr;
    static String sampleMinVolStr;
    static String sampleAvgVolStr;
    static Profile sampleProfile;

    //Main method
    public static void main(String[] args) {

        //Read in data from csv file and store them in arrays
        readAndStore();

        //Read and store index data, and create profiles based on data
        createProfiles();

        //Print everything out
        printOutput();

        //Get data for sample from user and store it in a profile
        sampleProfile = enterSample();

        sampleProfile.printSummary();

    }

    //Reads in data from csv file and stores them in Lists
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
        ProfileComparison comparisonTest = profiles.get(0).compareToProfile(profiles.get(5));
        System.out.println("\n");
        comparisonTest.printSummary();
    }

    //Gets sample data from user and creates sample profile
    private static Profile enterSample(){

        //Get data from user
        sampleName = GetData.getString("What is the name?", "Enter Name");
        sampleMaxFreqStr = GetData.getString("What is the maximum frequency of the sample?", "Enter Max Frequency");
        sampleMinFreqStr = GetData.getString("What is the minimum frequency of the sample?", "Enter Min Frequency");
        sampleAvgFreqStr = GetData.getString("What is the average frequency of the sample?", "Enter Average Frequency");
        sampleMaxVolStr = GetData.getString("What is the maximum volume of the sample?", "Enter Max Volume");
        sampleMinVolStr = GetData.getString("What is the minimum volume of the sample?", "Enter Min Volume");
        sampleAvgVolStr = GetData.getString("What is the average volume of the sample?", "Enter Average Volume");

        //Add values to list
        sampleList.add(sampleName);
        sampleList.add(sampleMaxFreqStr);
        sampleList.add(sampleMinFreqStr);
        sampleList.add(sampleAvgFreqStr);
        sampleList.add(sampleMaxVolStr);
        sampleList.add(sampleMinVolStr);
        sampleList.add(sampleAvgVolStr);

        //Create and return sample profile
        return new Profile(sampleList, finalIndices);

    }

}

