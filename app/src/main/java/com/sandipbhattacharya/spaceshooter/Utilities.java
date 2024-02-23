package com.sandipbhattacharya.spaceshooter;

import android.content.Context;
import android.content.SharedPreferences;

public class Utilities {
    final static  String NAME="name";
    final static String AGE="age";
    final static String FILENAME="MYSP";

    public static void save(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(FILENAME,0);//mode 0 read and write
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static void saveInt(Context context,String key, int value){
        SharedPreferences sp = context.getSharedPreferences(FILENAME,0);//mode 0 read and write
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key,value);
        editor.commit();
    }
    public static String getStringValue(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(FILENAME,0);
        return  sp.getString(key,null);//default value is null
    }
    public static int getIntValue(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(FILENAME,0);
        return  sp.getInt(key,0);//default value is null
    }

}
