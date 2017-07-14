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
    private List<Integer> indices = new ArrayList<>();
    private String name;
    private double maxFrequency;
    private double minFrequency;
    private double avgFrequency;
    private double maxVolume;
    private double minVolume;
    private double avgVolume;


    //Constructor: Takes in a List ("array") that contains values to be stored, and a List<Integer> that contains the indices
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

    //"Get" methods
    public String getName() {
        return name;
    }
    public double getMaxFrequency(){
        return maxFrequency;
    }
    public double getMinFrequency(){
        return minFrequency;
    }
    public double getAvgFrequency(){
        return avgFrequency;
    }
    public double getMaxVolume(){
        return maxVolume;
    }
    public double getMinVolume(){
        return minVolume;
    }
    public double getAvgVolume(){
        return avgVolume;
    }


    public ProfileComparison compareToProfile(Profile otherProfile){
        //Declare and instantiate local instance variables for otherProfile
        String otherName = otherProfile.getName();
        double otherMaxFrequency = otherProfile.getMaxFrequency();
        double otherMinFrequency = otherProfile.getMinFrequency();
        double otherAvgFrequency = otherProfile.getAvgFrequency();
        double otherMaxVolume = otherProfile.getMaxVolume();
        double otherMinVolume = otherProfile.getMinVolume();
        double otherAvgVolume = otherProfile.getAvgVolume();

        boolean nameDiff;
        double maxFreqDiff;
        double minFreqDiff;
        double avgFreqDiff;
        double maxVolDiff;
        double minVolDiff;
        double avgVolDiff;

        //Determines if profiles are exactly equal, including name
        if(name.equals(otherName)){
            if(maxFrequency == otherMaxFrequency && minFrequency == otherMinFrequency && avgFrequency == otherAvgFrequency){
                if(maxVolume == otherMaxVolume && minVolume == otherMinVolume && avgVolume == otherAvgVolume){
                    return new ProfileComparison(false, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }

        //Determines if profiles are exactly equal, but name is different
        if (!name.equals(otherName)){
            if(maxFrequency == otherMaxFrequency && minFrequency == otherMinFrequency && avgFrequency == otherAvgFrequency) {
                if (maxVolume == otherMaxVolume && minVolume == otherMinVolume && avgVolume == otherAvgVolume) {
                    return new ProfileComparison(true, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }

        //Determines the data to be used in ProfileComparison object (differences, etc) and returns that object
        if(name.equals(otherName)) {
            nameDiff = false;
        } else {
            nameDiff = true;
        }

        //Differences are absolute value of this object's data minus the other object's data
        maxFreqDiff = Math.abs(maxFrequency - otherMaxFrequency);
        minFreqDiff = Math.abs(minFrequency - otherMinFrequency);
        avgFreqDiff = Math.abs(avgFrequency - otherAvgFrequency);
        maxVolDiff = Math.abs(maxVolume - otherMaxVolume);
        minVolDiff = Math.abs(minVolume - otherMinVolume);
        avgVolDiff = Math.abs(avgVolume - otherAvgVolume);

        return new ProfileComparison(nameDiff, maxFreqDiff, minFreqDiff, avgFreqDiff, maxVolDiff, minVolDiff, avgVolDiff);


    } //End of method

} //End of class
