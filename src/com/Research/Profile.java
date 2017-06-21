package com.Research;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taylor on 6/11/2017.
 */
public class Profile {
    private final int NAMEINDEX = 0;
    private final int MAXFREQINDEX = 1;
    private final int MINFREQINDEX = 2;
    private final int AVGFREQINDEX = 3;
    private final int MAXVOLINDEX = 4;
    private final int MINVOLINDEX = 5;
    private final int AVGVOLINDEX = 6;

    private List<Object> initialArray;
    private List<Double> arrayOfDoubles;
    private List<Integer> indices;
    private String name;
    private double maxFrequency;
    private double minFrequency;
    private double avgFrequency;
    private double maxVolume;
    private double minVolume;
    private double avgVolume;

    //name, maxFrequency, minFrequency, avgFrequency, maxVolume, minVolume, avgVolume

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
        this.convertToDouble();
        name = (String)(initialArray.get(indices.get(NAMEINDEX)));
        System.out.println(name);
    }

    //Converts numerical String values stored in initialArray to doubles, and stores them in arrayOfDoubles
    private void convertToDouble(){
        //Store double values of initialArray into arrayOfDoubles as doubles
        for (Object element : initialArray){
            if(DataProcessor.isDouble((String)(element))){
                arrayOfDoubles.add((Double)(element));
            }
        }
    }


}
