package com.Research;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taylor on 6/11/2017.
 */
public class DataProcessor {

    //Removes blank ("") or null elements from ArrayList
    public static List removeBlanks(List tempArrayOfValues){
        List arrayOfValues = new ArrayList<>();

        //Add values of tempArrayOfValues to arrayOfValues
        for (Object tempElement : tempArrayOfValues){
            arrayOfValues.add(tempElement);
        }

        //Remove all instances of "" and null from arrayOfValues
        while (arrayOfValues.contains("") || arrayOfValues.contains(null)){
            arrayOfValues.remove("");
            arrayOfValues.remove(null);
        }
        return arrayOfValues;
    }

    //Returns true if "str" is parse-able to double
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Returns true if "str" is parse-able to int
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Returns the given object as a Double object, if possible - if not, returns error value "-99999.0"
    public static Double convertToDouble(Object element) {
        if (DataProcessor.isDouble((String)(element))) {
            return Double.parseDouble((String)(element));
        } else {
            return -99999.0;
        }
    }

    //Returns the given object as an int, if possible - if not, returns error value "-99999"
    public static Integer convertToInteger(Object element) {
        if (DataProcessor.isInteger((String)(element))) {
            return Integer.parseInt((String)(element));
        } else {
            return -99999;
        }
    }
}
