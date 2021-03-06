package com.Research;

/*
 * Created by Taylor on 10/26/2017
 */
public class OnePropZInt {
    private double z = 1.96; //Value for confidence level
    private double n; //Number of people tested (sample size)
    private double p; //Proportion of correct to total
    private double q; //1 - p
    public double lowerBound; //The lower value in the confidence interval
    public double upperBound; //The upper value in the confidence interval

    public OnePropZInt(double sampleSize, double numberCorrect){
        n = sampleSize;
        p = (numberCorrect / n);
        q = 1 - p;
        this.calc();
    }

    //Calculate the One Proportional Z Interval
    private void calc() {
        //Calculate values
        lowerBound = p - z * (Math.sqrt((p * q) / n));
        upperBound = p + z * (Math.sqrt((p * q) / n));
    }

    public void checkParameters()throws ParameterNotMetException{
        System.out.println("n: " + n + " * " + "p: " + p + " = " + n*p);
        System.out.println("n: " + n + " * " + "q: " + q + " = " + n*q);

        if(!(n * p >= 10)){ //Check for Parameter 1
            throw new ParameterNotMetException("n * p < 10");
        } else if(!(n * q >= 10)) { //Check for Parameter 2
            throw new ParameterNotMetException("n * q < 10");
        }
    }
}
