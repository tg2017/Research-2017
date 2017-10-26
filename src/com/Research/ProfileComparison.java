package com.Research;

import java.text.NumberFormat;

/*
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
    private double sumOfDiffs = 0.0;
    private double percentMatch = -1;
    private static boolean useFreq = true, useJitter = true, useShimmer = true;


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

        if(useFreq){
            sumOfDiffs += maxFreqDiff + minFreqDiff + avgFreqDiff;
        }
        if(useJitter){
            sumOfDiffs += jitterDiff;
        }
        if(useShimmer){
            sumOfDiffs += shimmerDiff;
        }

        if (sumOfDiffs == 0.0){
            if (!nameDiff){
                isEqual = true;
                isEqualIgnoreName = true;
            } else {
                isEqual = false;
                isEqualIgnoreName = true;
            }
        }
    }

    //Same as above, but also takes in percentMatch data
    public ProfileComparison(boolean namesDifferent, double maxFrequencyDifference, double minFrequencyDifference,
                             double avgFrequencyDifference, double jitterDifference, double shimmerDifference, double percentOfMatch){
        nameDiff = namesDifferent;
        maxFreqDiff = maxFrequencyDifference;
        minFreqDiff = minFrequencyDifference;
        avgFreqDiff = avgFrequencyDifference;
        jitterDiff = jitterDifference;
        shimmerDiff = shimmerDifference;
        percentMatch = percentOfMatch;

        if(useFreq){
            sumOfDiffs += maxFreqDiff + minFreqDiff + avgFreqDiff;
        }
        if(useJitter){
            sumOfDiffs += jitterDiff;
        }
        if(useShimmer){
            sumOfDiffs += shimmerDiff;
        }

        if (sumOfDiffs == 0.0){
            if (!nameDiff){
                isEqual = true;
                isEqualIgnoreName = true;
            } else {
                isEqual = false;
                isEqualIgnoreName = true;
            }
        }
    }

    //Getter methods
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

    public double getPercentMatch(){
        return percentMatch;
    }

    public static void setUseFreq(boolean useIt){
        useFreq = useIt;
    }
    public static void setUseJitter(boolean useIt){
        useJitter = useIt;
    }
    public static void setUseShimmer(boolean useIt){
        useShimmer = useIt;
    }

    public String toString(){

        //To format percentMatch part
        NumberFormat percent = NumberFormat.getNumberInstance();
        percent.setMaximumFractionDigits(2);

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
            if(useFreq) {
                output += "\nThe difference in maximum frequency is: " + maxFreqDiff + " Hz";
                output += "\nThe difference in minimum frequency is: " + minFreqDiff + " Hz";
                output += "\nThe difference in average frequency is: " + avgFreqDiff + " Hz";
            }
            if(useJitter) {
                output += "\nThe difference in jitter is: " + jitterDiff;
            }
            if(useShimmer) {
                output += "\nThe difference in shimmer is: " + shimmerDiff;
            }

            output += "\nThe overall difference is: " + sumOfDiffs;
            output += "\nThe sample matches the profile by " + percent.format(percentMatch) + "%";
        }

        return output;
    }

    public void printSummary(){
        System.out.println(this.toString());
    }

} //End of class