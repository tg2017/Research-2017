package com.Research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CSVReader cr = new CSVReader("C:/Users/Taylor/Desktop/joe.csv");
        String[] initialArray;
        ArrayList<List<String>> finalValues = new ArrayList<>();

        //Read in values from csv, separated by "new"
        cr.setSplitString("new");

        //Get values from CSVReader
        initialArray = cr.getValues();

        //Add values to finalValues
        for (int i = 0; i < initialArray.length; i++) {
            List<String> parsedArray = Arrays.asList(initialArray[i].split("\\s*,\\s*"));
            finalValues.add(parsedArray);
        }

        //TODO: Process data


        //Print final values
        System.out.println("Final Values ArrayList:\n" + finalValues);

        //Example use:
        //To print the 2nd array
        System.out.println("\nThe 2nd array:\n" + finalValues.get(1));

        //To print the fourth value of the second array
        System.out.println("\nThe 4th value of the 2nd array:\n" + finalValues.get(1).get(3));

    }

}
