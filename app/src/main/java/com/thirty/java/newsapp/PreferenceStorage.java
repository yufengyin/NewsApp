package com.thirty.java.newsapp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * Created by zyj on 2017/9/10.
 */

public class PreferenceStorage {
    private static String name = "PreferenceStorage";
    private static String defaultSetting = "1111111111111";
    private static boolean isFirstRun() {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(
                name, MyApplication.getContext().MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        Editor editor = sharedPreferences.edit();
        if (!isFirstRun) {
            return false;
        } else {
            editor.putBoolean("isFirstRun", false);
            editor.putString("preference", defaultSetting);
            editor.commit();
            return true;
        }
    }
    public static boolean[] changeStringToBooleanArray(String perference)
    {
        boolean[] booleanArray = new boolean[13];
        for (int i = 0; i < perference.length(); i++)
        if (perference.charAt(i) == '1')
            booleanArray[i] = true;
        else
            booleanArray[i] = false;
        return booleanArray;
    }
    public static String changeBooleanArrayToString(boolean[] booleanArray)
    {
        String tempString = "";
        for (int i = 0; i < booleanArray.length; i++)
        if (booleanArray[i] == true)
            tempString += "1";
        else
            tempString += 0;
        return tempString;
    }
    public static void storePreference(boolean[] booleanArray)
    {
        isFirstRun();
        MyApplication.getContext().getSharedPreferences(name, MyApplication.getContext().MODE_PRIVATE)
                .edit().putString("preference", changeBooleanArrayToString(booleanArray)).commit();
    }
    public static boolean[] loadPreference()
    {
        isFirstRun();
        boolean[] booleen = changeStringToBooleanArray(MyApplication.getContext()
                .getSharedPreferences(name, MyApplication.getContext().MODE_PRIVATE)
                .getString("preference", defaultSetting));
        return booleen;
    }
}
