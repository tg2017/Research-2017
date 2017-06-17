package com.Research;


import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        CSVReader cr = new CSVReader("C:/Users/Taylor/Desktop/joe.csv");
        String[] initialArray;
        String[][] finalValues = new String[4][6];

        //Read in values from csv, separated by "new"
        cr.setSplitString("new");
        initialArray = cr.getValues();

        //Print values of initialArray
        for (int i = 0; i < initialArray.length; i++) {
            System.out.println(initialArray[i]);
        }

        //Split values of initialArray into finalValues[][] by ","
        for(int count = 0; count < initialArray.length; count++){
                finalValues[count] = initialArray[count].split(",");
        }
        printValues(finalValues);
    }

    private static void printValues(String[][] arrayArray){
        System.out.println(Arrays.deepToString(arrayArray));
    }

}
