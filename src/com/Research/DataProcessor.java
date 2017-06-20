package com.Research;

import java.util.*;

/**
 * Created by Taylor on 6/11/2017.
 */
public class DataProcessor {

    //Removes blank ("") elements from ArrayList
    public static List removeBlanks(List tempArrayOfValues){
        List arrayOfValues = new ArrayList<>();
        for (int counter = 0; counter < tempArrayOfValues.size(); counter++){
            arrayOfValues.add(tempArrayOfValues.get(counter));
        }

        while (arrayOfValues.contains("") || arrayOfValues.contains(null)){
            arrayOfValues.remove("");
            arrayOfValues.remove(null);
        }
        /*for (int i = 0; i < arrayOfValues.size(); i++){
            if(arrayOfValues.get(i).equals("") || arrayOfValues.get(i) == null){
                Object acceptor = arrayOfValues.remove(i);
            }
        }
        return arrayOfValues; */
        return arrayOfValues;
    }

    public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }






}
