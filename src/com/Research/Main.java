package com.Research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        CSVReader cr = new CSVReader("C:/Users/Taylor/Desktop/joe.csv");
        CSVReader indexReader = new CSVReader("C:/Users/Taylor/Desktop/indices.txt");
        String[] initialArray;
        ArrayList<List> tempValues = new ArrayList<>();
        ArrayList<List> finalValues = new ArrayList<>();
        List<Profile> profiles;

        //Read in values from csv, separated by "new"
        cr.setSplitString("new");

        //Get values from CSVReader
        initialArray = cr.getValues();

        //Add values to tempValues
        for (String anInitialArray : initialArray) {
            List parsedArray = Arrays.asList(anInitialArray.split("\\s*,\\s*"));
            tempValues.add(parsedArray);
        }

        //Remove blank ("") values from arrays and store new arrays in finalValues
        for (List tempValue : tempValues) {
            finalValues.add(DataProcessor.removeBlanks(tempValue));
        }

        //Print temp values
        System.out.println("Temp Values ArrayList:\n" + tempValues);

        //Example use:
        //To print the 2nd array
        System.out.println("\nThe 2nd temp array:\n" + tempValues.get(1));

        //To print the fourth value of the second array
        System.out.println("\nThe 4th value of the 2nd temp array:\n" + tempValues.get(1).get(3));

        System.out.println();

        //Print final values
        System.out.println("Final Values ArrayList:\n" + finalValues);

        //Example use:
        //To print the 2nd array
        System.out.println("\nThe 2nd array:\n" + finalValues.get(1));

        //To print the fourth value of the second array
        System.out.println("\nThe 4th value of the 2nd array:\n" + finalValues.get(1).get(3));

    }

}
