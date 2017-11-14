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
        for (double x = -5.0; x <= 100.0; x += 0.01) {
            StdDraw.point(x, Gaussian.pdf(x, mu, sigma));
        }
    }

    public static void plot(int xScaleMin, int xScaleMax, double yScaleMin, double yScaleMax, double mu, double sigma) {

        StdDraw.setPenColor(StdDraw.BLACK);
        for(double scaleCount = xScaleMin; scaleCount <= xScaleMax; scaleCount += 10){
            if(scaleCount % 10 == 0) {
                for (double y = yScaleMin; y <= yScaleMin + .010; y += 0.001) {
                    StdDraw.point(scaleCount, y);
                }
            }
        }

        for (double x = xScaleMin; x <= xScaleMax; x += 0.01) {
            StdDraw.point(x, Gaussian.pdf(x, mu, sigma));
        }

        //Plot Standard Deviation lines
        for(double sd = mu; sd <= xScaleMax; sd += sigma){
            for(double y = yScaleMin; y <= yScaleMax; y += 0.01){
                StdDraw.point(sd,y);
            }
        }
        for(double sd = mu; sd >= xScaleMin; sd -= sigma){
            for(double y = yScaleMin; y <= yScaleMax; y += 0.01){
                StdDraw.point(sd,y);
            }
        }

    }

    /*public static void updateProgressBar(){
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Bell Curve: " + progressBar.getPercentComplete());
        progressBar.setValue(0);
        progressBar.setVisible(true);

        // add progress bar
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(progressBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);

        // update progressbar
        for (int i = 0; i <= 100; i++) {
            final int currentValue = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        progressBar.setValue(currentValue);
                    }
                });
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage());
            }
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
