package com.Research;

/**
 * Created by Taylor on 6/11/2017.
 */
public class DataProcessor {

    public DataProcessor(){}

    public boolean testDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }




}
