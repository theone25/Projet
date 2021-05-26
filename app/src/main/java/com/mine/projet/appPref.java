package com.mine.projet;

import android.content.Context;
import android.content.SharedPreferences;

public class appPref {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences constants
    private static final String PREF_NAME = "SharedPreferences";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    public appPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
    public SharedPreferences getPref(){
        SharedPreferences prefs = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs;
    }
}
