package com.Research;

import java.util.ArrayList;

/**
 * Created by Taylor on 6/11/2017.
 */
public class DataProcessor {

    //Removes blank ("") elements from ArrayList
    public static ArrayList cleanUp(ArrayList arrayOfValues){
        for (int i = 0; i < arrayOfValues.size(); i++){
            if(arrayOfValues.get(i) == ""){
                arrayOfValues.remove(i);
            }
        }
        return arrayOfValues;
    }

    public boolean testDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }




}
