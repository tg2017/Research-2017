package com.Research;

/**
 * Created by Taylor on 6/30/2017.
 */

//Class serves as an object that contains the data gained from the compareToProfile method of the Profile class:
    //made solely as an object to be returned by that method
public class ProfileComparison {
    private boolean isEqual;
    private boolean isEqualIgnoreName;
    private boolean nameDiff;
    private double maxFreqDiff;
    private double minFreqDiff;
    private double avgFreqDiff;
    private double jitterDiff;
    private double shimmerDiff;
    private double sumOfDiffs;

    //Constructor
    //Takes in all data as differences: the difference between data stored in the profile and data given in the sample
    public ProfileComparison(boolean namesDifferent, double maxFrequencyDifference, double minFrequencyDifference,
                             double avgFrequencyDifference, double jitterDifference, double shimmerDifference){
        nameDiff = namesDifferent;
        maxFreqDiff = maxFrequencyDifference;
        minFreqDiff = minFrequencyDifference;
        avgFreqDiff = avgFrequencyDifference;
        jitterDiff = jitterDifference;
        shimmerDiff = shimmerDifference;
        sumOfDiffs = maxFreqDiff + minFreqDiff + avgFreqDiff + jitterDiff + shimmerDiff;

        if (maxFreqDiff == 0 && minFreqDiff == 0 && avgFreqDiff == 0 && jitterDiff == 0 && shimmerDiff == 0){
            if (!nameDiff){
                isEqual = true;
                isEqualIgnoreName = true;
            } else {
                isEqual = false;
                isEqualIgnoreName = true;
            }
        }
    }

    //"Get" methods
    public boolean getNameDiff() {
        return nameDiff;
    }
    public double getMaxFreqDiff(){
        return maxFreqDiff;
    }
    public double getMinFreqDiff(){
        return minFreqDiff;
    }
    public double getAvgFreqDiff(){
        return avgFreqDiff;
    }
    public double getJitterDiff() { return jitterDiff; }
    public double getShimmerDiff() { return shimmerDiff; }
    public double getSumOfDiffs(){
        return sumOfDiffs;
    }

    public String toString(){
        //Initialize output message with Error message, to be overwritten later
        String output = "ERROR";

        //If they are equal...
        if(isEqual || isEqualIgnoreName){
            if (isEqual){
                output = "The profiles are exactly the same.";
            } else if (isEqualIgnoreName) {
                output = "The profiles are exactly the same, except for the name, which differs.";
            }
        //Otherwise...
        } else {
            //Determine name equality and represent as String
            if (!nameDiff) {
                output = "The names are the same.\n";
            } else {
                output = "The names are not the same.\n";
            }

            //Represent data (differences) as Strings
            output += "\nThe difference in maximum frequency is: " + maxFreqDiff + " Hz";
            output += "\nThe difference in minimum frequency is: " + minFreqDiff + " Hz";
            output += "\nThe difference in average frequency is: " + avgFreqDiff + " Hz";
            output += "\nThe difference in jitter is: " + jitterDiff;
            output += "\nThe difference in shimmer is: " + shimmerDiff;
            output += "\nThe overall difference is: " + sumOfDiffs;
        }

        return output;
    }

    public void printSummary(){
        System.out.println(this.toString());
    }

} //End of class