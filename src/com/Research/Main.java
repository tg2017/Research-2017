package com.Research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Objects pertaining to the accessing of data from csv files
        String filename = "C:/Users/Taylor/Desktop/joe.csv"; //File location of csv file for profiles
        String indexFilename = "C:/Users/Taylor/Desktop/indices.txt"; //File location of indices csv file
        CSVReader cr = new CSVReader(filename); //Reads in data from csv file for profiles
        CSVReader indexReader = new CSVReader(indexFilename); //Reads in indices from indices csv file

        //Objects pertaining to the storing of data from csv files
        String[] initialArray; //Stores data for profiles from csv as Strings
        String[] initialIndices; //Stores indices from indices csv file as Strings

        List<List> tempValues = new ArrayList<>(); //Temporarily stores data for profiles in a List of Lists
        List<List> finalValues = new ArrayList<>(); //Stores final, processed (formatted) data for profiles in a List of Lists
        List<Integer> finalIndices = new ArrayList<>(); // Stores indices values from indices.csv, as Integer objects
        List<Profile> profiles = new ArrayList<>(); //Array of profiles stored in the program - data is accessed from the csv file


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

        //Print summaries of Profiles
        for (Profile printTest : profiles){
            printTest.printSummary();
            System.out.println();
            System.out.println();
        }

        //Print test values
        //Print temp values
        System.out.println("Temp Values ArrayList:\n" + tempValues);

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

    }
}
