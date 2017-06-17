package com.Research;
import java.io.*;

/**
 * Created by Taylor Giles on 6/11/2017.
 */

public class CSVReader {
    private BufferedReader br;
    private String csvFile;
    private String csvSplitBy = ",";
    private String[] dataFromFile;

    //Constructors
    public CSVReader(){
        csvFile = "";
    }
    public CSVReader(String fileName){
        csvFile = fileName;
    }

    //Sets string to split by
    public void setSplitString(String splitString){
        csvSplitBy = splitString;
    }

    //Sets the file name
    public void setFileName(String fileName){
        csvFile = fileName;
    }

    //Reads in values from file, separates by csvSplitBy and stores them in String[]
    private void read() {
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                dataFromFile = line.split(csvSplitBy);

                //Print values
                /*int x = 0;
                while(dataFromFile[x] != null) {
                    System.out.println(dataFromFile[x]);
                    x++;
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Returns length of String[]
    public int getLength() {
        this.read();
        return dataFromFile.length;
    }

    //Returns values in csv file as String[]
    public String[] getValues(){
        this.read();
        return dataFromFile;
    }


}
