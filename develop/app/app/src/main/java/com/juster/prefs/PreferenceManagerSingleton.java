package com.juster.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManagerSingleton {

    //Adjuster application global preference file manager using this name.
    //If this name changes then a migration script using prefernce_version should be created
    private static final String PREF_NAME = "com.exzeo.adjustermate.PREF_APPLICATION";
    private final static int PREFERENCE_VERSION = 1;

    private static PreferenceManagerSingleton sInstance;
    private final SharedPreferences mPref;
    private static Context mContext = null;

    private PreferenceManagerSingleton() {
        mPref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            mContext = context.getApplicationContext();
            sInstance = new PreferenceManagerSingleton();
        }
    }

    public static synchronized PreferenceManagerSingleton getInstance(Context context) {
        if (sInstance == null) {
            mContext = context.getApplicationContext();
            sInstance = new PreferenceManagerSingleton();
        }
        return sInstance;
    }

    private static SharedPreferences getSharedPreferences() {
        return sInstance.mPref;
    }

    public static void deleteSharedPreference() {
        getSharedPreferences().edit().clear().apply();
    }

    private static final String KEY_PREF_TEMP_PASSWORD = "key_temp_password";
    private static final String KEY_PREF_TEMP_NAME = "key_temp_email";
    private static final String KEY_PREF_TEMP_CONTACT = "key_temp_contact";
    private static final String KEY_PREF_TEMP_SEARCH_LOC = "key_temp_searchloct";
    private static final String KEY_PREF_TEMP_LICENCE = "key_temp_licence";
    private static final String KEY_PREF_TEMP_IS_GUIDE = "key_temp_isguide";
    private static final String KEY_PREF_TEMP_IS_GUIDE_AVAILABLE = "key_temp_isavailable";
    private static final String KEY_PREF_TEMP_IS_LOGIN = "key_temp_islogin";


    public void putTempUserName(String tempUserName) {
        new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_NAME).set
                (tempUserName);
    }

    public String getTempUserName() {
        return new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_NAME, null).get();
    }

    public void putTempPassword(String tempPassword) {
        new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_PASSWORD).set
                (tempPassword);
    }

    public String getTempPassword() {
        return new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_PASSWORD, null).get();
    }

    public void putTempContactNo(String tempPassword) {
        new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_CONTACT).set
                (tempPassword);
    }

    public String getTempContactNo() {
        return new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_CONTACT, null).get();
    }

    public void putTempisGuide(boolean isguide) {
        new BooleanPreference(getSharedPreferences(), KEY_PREF_TEMP_IS_GUIDE).set(isguide);
    }

    public boolean getTempisGuide() {
        return new BooleanPreference(getSharedPreferences(), KEY_PREF_TEMP_IS_GUIDE, false).get();
    }

    public void putTempisLogin(boolean islogin) {
        new BooleanPreference(getSharedPreferences(), KEY_PREF_TEMP_IS_LOGIN).set(islogin);
    }

    public boolean getTempisLogin() {
        return new BooleanPreference(getSharedPreferences(), KEY_PREF_TEMP_IS_LOGIN, false).get();
    }

    public void putTempLicenceNo(String tempPassword) {
        new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_LICENCE).set
                (tempPassword);
    }

    public String getTempLicenceNo() {
        return new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_LICENCE, null).get();
    }

    public void putTempSearchLocation(String searchLocation) {
        new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_SEARCH_LOC).set
                (searchLocation);
    }

    public String getTempSearchLocation() {
        return new StringPreference(getSharedPreferences(), KEY_PREF_TEMP_SEARCH_LOC, null).get();
    }

    public void putTempguideisAvailable(boolean isguideavailable) {
        new BooleanPreference(getSharedPreferences(), KEY_PREF_TEMP_IS_GUIDE_AVAILABLE).set(isguideavailable);
    }

    public boolean getTempguideisAvailable() {
        return new BooleanPreference(getSharedPreferences(), KEY_PREF_TEMP_IS_GUIDE_AVAILABLE, false).get();
    }


}
