package com.Research;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taylor on 6/11/2017.
 */

public class Profile {

    private final Integer NAMEINDEX = 0;
    private final Integer MAXFREQINDEX = 1;
    private final Integer MINFREQINDEX = 2;
    private final Integer AVGFREQINDEX = 3;
    private final Integer MAXVOLINDEX = 4;
    private final Integer MINVOLINDEX = 5;
    private final Integer AVGVOLINDEX = 6;
    //Order of indexes in indices.csv:
    //name, maxFrequency, minFrequency, avgFrequency, maxVolume, minVolume, avgVolume
    //
    //NOTE: this is to read in the indices of the values in the initialArray. So, in the original csv file containing
    //the data for the profiles, the data can be sorted in any order; but, in the indices file, the index of each value
    //must be specified in this order.
    //Example:
    //If, in the original csv file, the name is the first value, followed by maxFreq, followed by maxVol, then minFreq,
    //minVol, avgFreq, and finally, avgVol, then in the indices csv file, the numbers must appear in the order
    //"0,1,3,5,2,4,6".
    //This says that the name is index 0, maxFrequency is index 1, minFrequency is index 3, etc.

    private List<Object> initialArray = new ArrayList<>();
    private List<Double> arrayOfDoubles = new ArrayList<>();
    private List<Integer> indices = new ArrayList<>();
    private String name;
    private double maxFrequency;
    private double minFrequency;
    private double avgFrequency;
    private double maxVolume;
    private double minVolume;
    private double avgVolume;



    public Profile(List array){
        //Store values of "array" in "initialArray"
        for (Object element : array) {
            initialArray.add(element);
        }
        this.initialize();
    }

    public Profile(List array, List<Integer> arrayOfIndices){
        //Store values of "array" in "initialArray"
        for (Object initializerObject : array) {
            initialArray.add(initializerObject);
        }

        //Store values of arrayOfIndices into "indices"
        for (Integer tempIndexElement : arrayOfIndices){
            indices.add(tempIndexElement);
        }
        this.initialize();
    }

    //Calls convertToDouble and stores name value in initialArray into "name"
    private void initialize(){
        //Stores the name in String "name" - data is obtained from array passed in from client, index is based on indices.csv
        name = (String)(initialArray.get(indices.get(NAMEINDEX)));
        maxFrequency = DataProcessor.convertToDouble(initialArray.get(indices.get(MAXFREQINDEX)));
        minFrequency = DataProcessor.convertToDouble(initialArray.get(indices.get(MINFREQINDEX)));
        avgFrequency = DataProcessor.convertToDouble(initialArray.get(indices.get(AVGFREQINDEX)));
        maxVolume = DataProcessor.convertToDouble(initialArray.get(indices.get(MAXVOLINDEX)));
        minVolume = DataProcessor.convertToDouble(initialArray.get(indices.get(MINVOLINDEX)));
        avgVolume = DataProcessor.convertToDouble(initialArray.get(indices.get(AVGVOLINDEX)));
    }

    //Returns profile data summary as a String
    public String toString(){
        String output;
        output = "Name: " + name +
                "\nMaximum Frequency: " + maxFrequency +
                "\nMinimum Frequency: " + minFrequency +
                "\nAverage Frequency: " + avgFrequency +
                "\nMaximum Volume: " + maxVolume +
                "\nMinimum Volume: " + minVolume +
                "\nAverage Volume: " + avgVolume;
        return output;
    }

    //Prints out data summary for this profile
    public void printSummary(){
        System.out.println(this.toString());
    }
}
