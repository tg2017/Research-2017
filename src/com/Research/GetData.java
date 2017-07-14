package com.Research;

import javax.swing.*;

/**
 * Created by Taylor on 7/14/2017.
 */
public class GetData {
    public static String getString(String message, String title){
        String output = JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
        return output;
    }

    public static int getInt(String message, String title){
        String outputStr = JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
        while (!DataProcessor.isInteger(outputStr)) {
            if (DataProcessor.isInteger(outputStr)) {
                return DataProcessor.convertToInteger(outputStr);
            } else {
                outputStr = JOptionPane.showInputDialog(null, "ERROR. Input must be an integer value.\n\n" + message, title, JOptionPane.PLAIN_MESSAGE);
            }
        }
        return -99999;
    }

    public static double getDouble(String message, String title){
        String outputStr = JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
        while (!DataProcessor.isDouble(outputStr)) {
            if (DataProcessor.isDouble(outputStr)) {
                return DataProcessor.convertToDouble(outputStr);
            } else {
                outputStr = JOptionPane.showInputDialog(null, "ERROR. Input must be a double value.\n\n" + message, title, JOptionPane.PLAIN_MESSAGE);
            }
        }
        return -99999.0;
    }

    public static boolean getBoolean(String message, String title){
        String outputStr = JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
        while (!outputStr.equals("true") && !outputStr.equals("false")) {
            switch (outputStr) {
                case "true":
                    return true;
                case "false":
                    return false;
                default:
                    outputStr = JOptionPane.showInputDialog(null, "ERROR. Input must be a boolean value (true or false).\n\n" + message, title, JOptionPane.PLAIN_MESSAGE);
                    break;
            }
        }
        return false;
    }
}

