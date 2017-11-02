package com.Research;

/******************************************************************************
 *  Compilation:  javac BellCurve.java
 *  Execution:    java BellCurve
 *  Dependencies: StdDraw.java Gaussian.java
 *
 * Copyright © 2000–2017, Robert Sedgewick and Kevin Wayne.
 *      Edited by Taylor Giles in November 2017
 * Last updated: Fri Oct 20 14:12:12 EDT 2017.
 ******************************************************************************/

public class BellCurve {

    public static void plot(double mu, double sigma) {
        for (double x = -5.0; x <= 5.0; x += 0.01) {
            StdDraw.point(x, Gaussian.pdf(x, mu, sigma));
        }
    }

    public static void plot(int canvasWidth, int canvasHeight, int xScaleMin, int xScaleMax, int yScaleMin, int yScaleMax, double mu, double sigma) {
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(xScaleMin, xScaleMax);
        StdDraw.setYscale(yScaleMin, yScaleMax);
        //Plot scale points
        for(int scaleCount = xScaleMin; scaleCount <= xScaleMax; scaleCount += 10){
            for(double y = yScaleMin; y <= 1; y += .01){
                StdDraw.point(scaleCount, y);
            }
        }
        for (double x = xScaleMin; x <= xScaleMax; x += 0.01) {
            StdDraw.point(x, Gaussian.pdf(x, mu, sigma));
        }
    }

    /*public static void main(String[] args) {
        StdDraw.setCanvasSize(600, 400);
        StdDraw.setXscale(-5, +5);
        StdDraw.setYscale(0, 1);
        plot(0, 0.5);
        plot(0, 1.0);
        plot(0, 2.0);
        plot(-2, 0.75);
    }*/
}
