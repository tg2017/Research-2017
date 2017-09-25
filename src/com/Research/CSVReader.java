package com.Research;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taylor Giles on 6/11/2017.
 */

public class CSVReader {
    private BufferedReader br;
    private String csvFile;
    private String csvSplitBy = ",";
    private List<String> dataFromFileTemp = new ArrayList<>();
    private String[] dataFromFile;

    //Constructor
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
            try {
                br = new BufferedReader(new FileReader(csvFile));
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "The file:\n" + csvFile + "\ncannot be found by the system. \n\nPlease revise this filename in the settings and try again.", "File Not Found Error", JOptionPane.ERROR_MESSAGE);
                Main.createGUI();
            }
            //If splitting by new line...
            if(csvSplitBy.equalsIgnoreCase("new line")){

                //Split lines from csv file by new line
                for (line = br.readLine(); line != null; line = br.readLine()) {
                    dataFromFileTemp.add(line);
                }

                //Put data from dataFromFileTemp into dataFromFile
                dataFromFile = new String[dataFromFileTemp.size()];
                for(int x = 0; x < dataFromFileTemp.size(); x++) {
                    dataFromFile[x] = dataFromFileTemp.get(x);

                    //Print Values
                    //System.out.println(dataFromFile[x]);
                }
            } else {
                while ((line = br.readLine()) != null) {

                    //csvSplitBy used as separator
                    dataFromFile = line.split(csvSplitBy);

                    //Print values
                    /*for(int x = 0; x < dataFromFile.length; x++) {
                        System.out.println(dataFromFile[x]);
                    }*/
                }
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


} //End of class
