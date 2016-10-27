package com.freephone.justfofun.freephone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Shenh on 2016/10/26.
 */

public class SharedPreferencesUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context,String spName){
        sp = context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        editor = sp.edit();
    }

<<<<<<< HEAD
    public boolean saveBoolean(String key,boolean value){
        editor.putBoolean(key,value);
        return editor.commit();
    }

    public boolean saveStringSet(String key, Set<String> value){
        editor.putStringSet(key,value);
=======
    public void saveBoolean(String key,boolean value){
        editor.putBoolean(key,value);
    }

    public void saveStringSet(String key, Set<String> value){
        editor.putStringSet(key,value);
    }

    public void saveString(String key,String value){
        editor.putString(key,value);
    }

    public boolean commit(){
>>>>>>> dev
        return editor.commit();
    }

    public boolean readBoolean(String key){
        return sp.getBoolean(key,false);
    }

    public Set<String> readStringSet(String key){
        return sp.getStringSet(key,new HashSet<>());
    }
<<<<<<< HEAD
=======

    public String readString(String key){
        return sp.getString(key,"");
    }
>>>>>>> dev
}
