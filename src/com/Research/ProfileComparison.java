package com.Research;

/**
 * Created by Taylor on 6/30/2017.
 */
public class ProfileComparison {
    private boolean isEqual;
    private boolean isEqualIgnoreName;
    private boolean nameDiff;
    private double maxFreqDiff;
    private double minFreqDiff;
    private double avgFreqDiff;
    private double maxVolDiff;
    private double minVolDiff;
    private double avgVolDiff;

    //Constructors
    //Determines only if the names are different
    public ProfileComparison(boolean namesDifferent){
        nameDiff = namesDifferent;
    }

    //Takes in all data as differences: the difference between data stored in the profile and data given in the sample
    public ProfileComparison(boolean namesDifferent, double maxFrequencyDifference, double minFrequencyDifference,
                             double avgFrequencyDifference, double maxVolumeDifference, double minVolumeDifference, double avgVolumeDifference){
        nameDiff = namesDifferent;
        maxFreqDiff = maxFrequencyDifference;
        minFreqDiff = minFrequencyDifference;
        avgFreqDiff = minFrequencyDifference;
        maxVolDiff = maxVolumeDifference;
        minVolDiff = minVolumeDifference;
        avgVolDiff = avgVolumeDifference;

        if (maxFreqDiff == 0.0 && minFreqDiff == 0.0 && avgFreqDiff == 0.0){
            if (maxVolDiff == 0.0 && minVolDiff == 0.0 && avgVolDiff == 0.0){
                if (nameDiff){
                    isEqual = true;
                    isEqualIgnoreName = true;
                } else {
                    isEqual = false;
                    isEqualIgnoreName = true;
                }
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
    public double getMaxVolDiff(){
        return maxVolDiff;
    }
    public double getMinVolDiff(){
        return minVolDiff;
    }
    public double getAvgVolDiff(){
        return avgVolDiff;
    }

    public String toString(){
        String output;

        //If they are equal...
        if (isEqual){
            output = "The profiles are exactly the same.";
        } else if (isEqualIgnoreName){
            output = "The profiles are exactly the same except for the name, which differs.";

        //Otherwise...
        } else {
            //Determine name equality and represent as String
            if (nameDiff) {
                output = "The names are the same.\n";
            } else {
                output = "The names are not the same.\n";
            }

            //Represent data (differences) as Strings
            output += "\nThe difference in maximum frequency is: " + maxFreqDiff;
            output += "\nThe difference in minimum frequency is: " + minFreqDiff;
            output += "\nThe difference in average frequency is: " + avgFreqDiff;
            output += "\n\nThe difference in maximum volume is: " + maxVolDiff;
            output += "\nThe difference in minimum volume is: " + minVolDiff;
            output += "\nThe difference in average frequency is: " + avgVolDiff;
        }
        return output;


    }

    public void printSummary(){
        System.out.println(this.toString());
    }


}
