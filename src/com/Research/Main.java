package com.Research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CSVReader cr = new CSVReader("C:/Users/Taylor/Desktop/joe.csv");
        String[] initialArray;
        ArrayList<List> tempValues = new ArrayList<>();
        ArrayList<List> finalValues = new ArrayList<>();

        //Read in values from csv, separated by "new"
        cr.setSplitString("new");

        //Get values from CSVReader
        initialArray = cr.getValues();

        //Add values to tempValues
        for (int i = 0; i < initialArray.length; i++) {
            List parsedArray = Arrays.asList(initialArray[i].split("\\s*,\\s*"));
            tempValues.add(parsedArray);
        }

        //TODO: Process data
        for (int i = 0; i < tempValues.size(); i++){
            finalValues.add(DataProcessor.removeBlanks(tempValues.get(i)));
        }

        //Print temp values
        System.out.println("Temp Values ArrayList:\n" + tempValues);

        //Example use:
        //To print the 2nd array
        System.out.println("\nThe 2nd temp array:\n" + tempValues.get(1));

        //To print the fourth value of the second array
        System.out.println("\nThe 4th value of the 2nd temp array:\n" + tempValues.get(1).get(3));


        //Print final values
        System.out.println("Final Values ArrayList:\n" + finalValues);

        //Example use:
        //To print the 2nd array
        System.out.println("\nThe 2nd array:\n" + finalValues.get(1));

        //To print the fourth value of the second array
        System.out.println("\nThe 4th value of the 2nd array:\n" + finalValues.get(1).get(3));

    }

}
