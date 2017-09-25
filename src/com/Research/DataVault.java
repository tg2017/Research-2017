package com.Research;

/**
 * Created as a way to store data for GUIs across instances within a single run of the program
 * NOTE: the "Data Vault" is CLEARED every time the program is closed. Data that needs to be stored across uses is
 * stored within .txt files elsewhere
 */
public class DataVault {
    private static boolean settingsUseFreq;
    private static boolean settingsUseJitter;
    private static boolean settingsUseShimmer;
    private static boolean settingsUseAuto;

    public static boolean getSettingsUseFreq(){ return settingsUseFreq; }
    public static void setSettingsUseFreq(boolean settingsUseFreq) {
        DataVault.settingsUseFreq = settingsUseFreq;
    }

    public static boolean getSettingsUseJitter() {
        return settingsUseJitter;
    }
    public static void setSettingsUseJitter(boolean settingsUseJitter) {
        DataVault.settingsUseJitter = settingsUseJitter;
    }

    public static boolean getSettingsUseShimmer() {
        return settingsUseShimmer;
    }
    public static void setSettingsUseShimmer(boolean settingsUseShimmer) {
        DataVault.settingsUseShimmer = settingsUseShimmer;
    }

    public static boolean getSettingsUseAuto() {
        return settingsUseAuto;
    }
    public static void setSettingsUseAuto(boolean settingsUseAuto) {
        DataVault.settingsUseAuto = settingsUseAuto;
    }


}
