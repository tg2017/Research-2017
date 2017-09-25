/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Research;

import java.awt.*;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Taylor
 */
public class SettingsGUI extends javax.swing.JFrame {

    /**
     * Creates new form OptionFrame
     */
    public SettingsGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        //My Variables
        //Filenames
        profileFilename = Main.getProfileFilename();
        indexFilename = Main.getIndexFilename();
        sampleFilename = Main.getSampleFilename();
        reportFilename = Main.getReportFilename();

        //Their Variables (NetBeans)
        freqCheckbox = new javax.swing.JCheckBox();
        jitterCheckbox = new javax.swing.JCheckBox();
        shimmerCheckbox = new javax.swing.JCheckBox();
        settingsBackButton = new javax.swing.JButton();
        settingsTitle = new javax.swing.JLabel();
        dataCheckboxTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        profileFileNameTextbox = new javax.swing.JTextPane();
        profileFileNameLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        indexFileNameTextbox = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        sampleFileNameTextbox = new javax.swing.JTextPane();
        indexFileNameLabel = new javax.swing.JLabel();
        sampleFileNameLabel = new javax.swing.JLabel();
        settingsExitButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        reportFileNameTextbox = new javax.swing.JTextPane();
        reportFileNameLabel = new javax.swing.JLabel();
        profileChangeButton = new javax.swing.JButton();
        indexChangeButton = new javax.swing.JButton();
        sampleChangeButton = new javax.swing.JButton();
        reportChangeButton = new javax.swing.JButton();
        autoSampleCheckbox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");

        freqCheckbox.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        freqCheckbox.setSelected(true);
        freqCheckbox.setText("Frequency");
        freqCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freqCheckboxActionPerformed(evt);
            }
        });

        jitterCheckbox.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jitterCheckbox.setSelected(true);
        jitterCheckbox.setText("Jitter");
        jitterCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jitterCheckboxActionPerformed(evt);
            }
        });

        shimmerCheckbox.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        shimmerCheckbox.setSelected(true);
        shimmerCheckbox.setText("Shimmer");
        shimmerCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shimmerCheckboxActionPerformed(evt);
            }
        });

        settingsBackButton.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        settingsBackButton.setText("Back");
        settingsBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsBackButtonActionPerformed(evt);
            }
        });

        settingsTitle.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        settingsTitle.setText("Settings");

        dataCheckboxTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dataCheckboxTitle.setText("Data to Test For:");

        profileFileNameTextbox.setText(Main.getProfileFilename());
        jScrollPane1.setViewportView(profileFileNameTextbox);

        profileFileNameLabel.setText("Profiles CSV File Location:");

        indexFileNameTextbox.setText(Main.getIndexFilename());
        jScrollPane2.setViewportView(indexFileNameTextbox);

        sampleFileNameTextbox.setText(Main.getSampleFilename());
        jScrollPane3.setViewportView(sampleFileNameTextbox);

        indexFileNameLabel.setText("Indices CSV File Location:");
        indexFileNameLabel.setToolTipText("");

        sampleFileNameLabel.setText("Samples CSV File Location:");

        settingsExitButton.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        settingsExitButton.setText("Exit");
        settingsExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsExitButtonActionPerformed(evt);
            }
        });

        reportFileNameTextbox.setText(Main.getReportFilename());
        jScrollPane4.setViewportView(reportFileNameTextbox);

        reportFileNameLabel.setText("Report Text File Location:");

        profileChangeButton.setText("Change");
        profileChangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileChangeButtonActionPerformed(evt);
            }
        });

        indexChangeButton.setText("Change");
        indexChangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indexChangeButtonActionPerformed(evt);
            }
        });

        sampleChangeButton.setText("Change");
        sampleChangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sampleChangeButtonActionPerformed(evt);
            }
        });

        reportChangeButton.setText("Change");
        reportChangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportChangeButtonActionPerformed(evt);
            }
        });

        autoSampleCheckbox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        autoSampleCheckbox.setText("Check Samples Automatically");
        autoSampleCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                autoSampleCheckboxStateChanged(evt);
            }
        });
        autoSampleCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoSampleCheckboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(settingsBackButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(settingsExitButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(autoSampleCheckbox)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                                                                                        .addComponent(jScrollPane3)
                                                                                        .addComponent(jScrollPane4)
                                                                                        .addComponent(jScrollPane1)
                                                                                        .addComponent(profileFileNameLabel)
                                                                                        .addComponent(indexFileNameLabel)
                                                                                        .addComponent(sampleFileNameLabel)
                                                                                        .addComponent(reportFileNameLabel))
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(profileChangeButton)
                                                                                        .addComponent(indexChangeButton)
                                                                                        .addComponent(sampleChangeButton)
                                                                                        .addComponent(reportChangeButton)))))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(settingsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(dataCheckboxTitle)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addGap(10, 10, 10)
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(jitterCheckbox)
                                                                                                .addComponent(freqCheckbox)
                                                                                                .addComponent(shimmerCheckbox))))
                                                                        .addGap(32, 32, 32))))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(settingsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dataCheckboxTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(freqCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jitterCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(shimmerCheckbox)
                                .addGap(25, 25, 25)
                                .addComponent(profileFileNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(profileChangeButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(indexFileNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(indexChangeButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sampleFileNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sampleChangeButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(reportFileNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(reportChangeButton))
                                .addGap(27, 27, 27)
                                .addComponent(autoSampleCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(settingsBackButton)
                                        .addComponent(settingsExitButton))
                                .addContainerGap())
        );

        //IMPORTANT NOTE: I, the creator of the class, have no idea how to follow the above code. The following is my
        //way of overriding the states of the components that I need to change across instances.
        if(alreadyUsed) {
            freqCheckbox.setSelected(DataVault.getSettingsUseFreq());
            jitterCheckbox.setSelected(DataVault.getSettingsUseJitter());
            shimmerCheckbox.setSelected(DataVault.getSettingsUseShimmer());
            autoSampleCheckbox.setSelected(DataVault.getSettingsUseAuto());
        } else {
            alreadyUsed = true;
        }

        pack();

        //Center the jFrame on screen
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }// </editor-fold>

    private void freqCheckboxActionPerformed(java.awt.event.ActionEvent evt) {
        if(freqCheckbox.isSelected()){
            Main.setUseFrequency(true);
        } else {
            Main.setUseFrequency(false);
        }
    }

    private void jitterCheckboxActionPerformed(java.awt.event.ActionEvent evt) {
        if(jitterCheckbox.isSelected()){
            Main.setUseJitter(true);
        } else {
            Main.setUseJitter(false);
        }
    }

    private void shimmerCheckboxActionPerformed(java.awt.event.ActionEvent evt) {
        if(jitterCheckbox.isSelected()){
            Main.setUseShimmer(true);
        } else {
            Main.setUseShimmer(false);
        }
    }

    private void settingsExitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(2);
    }

    private void profileChangeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //Sets text in textbox to reflect change
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        profileFilename = file.getAbsolutePath().replace("\\", "/1112");
        profileFileNameTextbox.setText(profileFilename);
        Main.setProfileFilename(profileFilename);

        changeFilenames();
    }

    private void indexChangeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //Sets text in textbox to reflect change
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        indexFilename = file.getAbsolutePath().replace("\\", "/");;
        indexFileNameTextbox.setText(indexFilename);
        Main.setIndexFilename(indexFilename);

        changeFilenames();
    }

    private void settingsBackButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //Create new menu GUI after leaving settings menu
        Main.createGUI();

        //Before disposal, store data in DataVault
        DataVault.setSettingsUseAuto(checkUseAuto());
        DataVault.setSettingsUseFreq(checkUseFreq());
        DataVault.setSettingsUseJitter(checkUseJitter());
        DataVault.setSettingsUseShimmer(checkUseShimmer());

        this.dispose();
    }

    private void autoSampleCheckboxActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void autoSampleCheckboxStateChanged(javax.swing.event.ChangeEvent evt) {
        Main.setAutoInput(autoSampleCheckbox.isSelected());
    }

    private void reportChangeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //Sets text in textbox to reflect change
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        reportFilename = file.getAbsolutePath().replace("\\", "/");;
        reportFileNameTextbox.setText(reportFilename);
        Main.setReportFilename(reportFilename);

        changeFilenames();
    }

    private void sampleChangeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //Sets text in textbox to reflect change
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        sampleFilename = file.getAbsolutePath().replace("\\", "/");
        sampleFileNameTextbox.setText(sampleFilename);
        Main.setSampleFilename(sampleFilename);

        changeFilenames();
    }

    //Returns values of checkboxes, for Main class
    public static boolean checkUseAuto(){
        return autoSampleCheckbox.isSelected();
    }
    public static boolean checkUseFreq(){ return freqCheckbox.isSelected(); }
    public static boolean checkUseJitter() { return jitterCheckbox.isSelected(); }
    public static boolean checkUseShimmer() { return shimmerCheckbox.isSelected(); }

    //Tells Main class to change filenames to reflect changes made in this class
    public static void changeFilenames(){
        System.out.println(reportFilename);
        System.out.println(profileFilename);
        //System.out.println(changedFilenames[0]);
        //Initialize changedFilenames[]
        changedFilenames = new String[4];
        changedFilenames[Main.PROFILEFILENAMEINDEX] = profileFilename;
        changedFilenames[Main.INDEXFILENAMEINDEX] = indexFilename;
        changedFilenames[Main.SAMPLEFILENAMEINDEX] = sampleFilename;
        changedFilenames[Main.REPORTFILENAMEINDEX] = reportFilename;

        //Tell main class to reflect these changes
        Main.changeFilenames(changedFilenames);
    }

    //Changes textboxes to reflect filenames. Note: Changes data in THIS CLASS, not Main class
    public static void setFilenames(String filenameProfiles, String filenameIndices, String filenameSamples, String filenameReport) {
        profileFileNameTextbox.setText(filenameProfiles);
        indexFileNameTextbox.setText(filenameIndices);
        sampleFileNameTextbox.setText(filenameSamples);
        reportFileNameTextbox.setText(filenameReport);
    }

    //Ensures that initialization by Main class does not reset checkboxes
    public static void setAlreadyUsed(boolean used){alreadyUsed = used;}


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettingsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SettingsGUI().setVisible(true);
            }
        });
    }

    // Variables declaration
    //My Variables
    //Filenames
    private static String profileFilename;
    private static String indexFilename;
    private static String sampleFilename;
    private static String reportFilename;
    private static String[] changedFilenames;
    private static boolean alreadyUsed = false;

    //Their (NetBeans) Variables
    private static javax.swing.JCheckBox autoSampleCheckbox;
    private javax.swing.JLabel dataCheckboxTitle;
    private static javax.swing.JCheckBox freqCheckbox;
    private javax.swing.JButton indexChangeButton;
    private javax.swing.JLabel indexFileNameLabel;
    private static javax.swing.JTextPane indexFileNameTextbox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private static javax.swing.JCheckBox jitterCheckbox;
    private javax.swing.JButton profileChangeButton;
    private javax.swing.JLabel profileFileNameLabel;
    private static javax.swing.JTextPane profileFileNameTextbox;
    private javax.swing.JButton reportChangeButton;
    private javax.swing.JLabel reportFileNameLabel;
    private static javax.swing.JTextPane reportFileNameTextbox;
    private javax.swing.JButton sampleChangeButton;
    private javax.swing.JLabel sampleFileNameLabel;
    private static javax.swing.JTextPane sampleFileNameTextbox;
    private javax.swing.JButton settingsBackButton;
    private javax.swing.JButton settingsExitButton;
    private javax.swing.JLabel settingsTitle;
    private static javax.swing.JCheckBox shimmerCheckbox;
    // End of variables declaration
}
