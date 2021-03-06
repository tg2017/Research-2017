package com.Research;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Taylor on 6/11/2017.
 */

public class Profile {

    private final Integer NAMEINDEX = 0;
    private final Integer MAXFREQINDEX = 1;
    private final Integer MINFREQINDEX = 2;
    private final Integer AVGFREQINDEX = 3;
    private final Integer JITTERINDEX = 4;
    private final Integer SHIMMERINDEX = 5;

    //Order of indexes in indices.csv:
    //name, maxFrequency, minFrequency, avgFrequency, jitter, shimmer
    //
    //NOTE: this is to read in the indices of the values in the initialArray. So, in the original csv file containing
    //the data for the profiles, the data can be sorted in any order; but, in the indices file, the index of each value
    //must be specified in this order.
    //Example:
    //If, in the original csv file, the name is the first value, followed by maxFreq, followed by minFreq, shimmer,
    //avgFreq, and finally jitter, then in the indices csv file, the numbers must appear in the order:
    //"0,1,2,5,3,4".
    //This says that the name is index 0, maxFrequency is index 1, minFrequency is index 2, shimmer is index 3, etc.

    private List<Object> initialArray = new ArrayList<>();
    private List<Integer> indices = new ArrayList<>();
    private String name;
    private double maxFrequency;
    private double minFrequency;
    private double avgFrequency;
    private double jitter;
    private double shimmer;

    private double sumOfValues = 0;

    private static boolean useFreq;
    private static boolean useJitter;
    private static boolean useShimmer;


    //Frequency Only constructor: Takes in a List ("array") that contains values to be stored, and a List<Integer> that contains the indices
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
        jitter = DataProcessor.convertToDouble(initialArray.get(indices.get(JITTERINDEX)));
        shimmer = DataProcessor.convertToDouble(initialArray.get(indices.get(SHIMMERINDEX)));

        if(useFreq){
            sumOfValues = maxFrequency + minFrequency + avgFrequency;
        }
        if(useJitter){
            sumOfValues += jitter;
        }
        if(useShimmer){
            sumOfValues += shimmer;
        }
    }

    //Returns profile data summary as a String
    public String toString(){
        String output;
        output = "Name: " + name;
        if(useFreq) {
            output += "\nMaximum Frequency: " + maxFrequency + " Hz";
            output += "\nMinimum Frequency: " + minFrequency + " Hz";
            output += "\nAverage Frequency: " + avgFrequency + " Hz";
        }
        if(useJitter) {
            output += "\nJitter Ratio:      " + jitter;
        }
        if(useShimmer) {
            output += "\nShimmer:           " + shimmer;
        }
        return output;
    }

    //Prints out data summary for this profile
    public void printSummary(){
        System.out.println(this.toString());
    }

    //Getter methods
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
    public double getJitter() { return jitter; }
    public double getShimmer() { return shimmer; }

    //Setter methods
    public static void setUseFreq(boolean useIt){
        useFreq = useIt;
    }
    public static void setUseJitter(boolean useIt){
        useJitter = useIt;
    }
    public static void setUseShimmer(boolean useIt){
        useShimmer = useIt;
    }


    //Method that compares two profiles, and returns a ProfileComparison object
    public ProfileComparison compareToProfile(Profile otherProfile){
        //Declare and instantiate local instance variables for otherProfile
        String otherName = otherProfile.getName();
        double otherMaxFrequency = otherProfile.getMaxFrequency();
        double otherMinFrequency = otherProfile.getMinFrequency();
        double otherAvgFrequency = otherProfile.getAvgFrequency();
        double otherJitter = otherProfile.getJitter();
        double otherShimmer = otherProfile.getShimmer();


        //Declaring variables to be passed into ProfileComparison object as parameters
        boolean nameDiff;
        double maxFreqDiff;
        double minFreqDiff;
        double avgFreqDiff;
        double jitterDiff;
        double shimmerDiff;
        double percentMatch;

        //Determines if profiles are exactly equal, including name
        if(name.equals(otherName)){
            if(maxFrequency == otherMaxFrequency && minFrequency == otherMinFrequency && avgFrequency == otherAvgFrequency && jitter == otherJitter && shimmer == otherShimmer){
                return new ProfileComparison(false, 0.0, 0.0, 0.0, 0.0,0.0);
            }
        }

        //Determines if profiles are exactly equal, but name is different
        if (!name.equals(otherName)){
            if(maxFrequency == otherMaxFrequency && minFrequency == otherMinFrequency && avgFrequency == otherAvgFrequency && jitter == otherJitter && shimmer == otherShimmer) {
                return new ProfileComparison(true, 0.0, 0.0, 0.0, 0.0,0.0);
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
        jitterDiff = Math.abs(jitter - otherJitter);
        shimmerDiff = Math.abs(shimmer - otherShimmer);

        double sumOfDiffs = 0;
        if(useFreq){
            sumOfDiffs += maxFreqDiff + minFreqDiff + avgFreqDiff;
        }
        if(useJitter){
            sumOfDiffs += jitterDiff;
        }
        if(useShimmer){
            sumOfDiffs += shimmerDiff;
        }

        percentMatch = 100 - ((sumOfDiffs / this.getSumOfValues()) * 100);

        return new ProfileComparison(nameDiff, maxFreqDiff, minFreqDiff, avgFreqDiff, jitterDiff, shimmerDiff, percentMatch);

    } //End of method

    //Method that compares a profile to multiple profiles, and returns results in a list of ProfileComparison objects
    public List<ProfileComparison> compareToProfiles(List<Profile> profileList){
        List<ProfileComparison> listOfComparisons = new ArrayList<>();
        for(int count = 0; count < profileList.size(); count++){
            listOfComparisons.add(count, this.compareToProfile(profileList.get(count)));
        }
        return listOfComparisons;
    }

    public double getSumOfValues() {
        return sumOfValues;
    }
} //End of class