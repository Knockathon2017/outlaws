package com.juster;

import android.app.Application;
import android.content.Context;

import com.juster.data.api.database.sqlite.DataBaseManager;
import com.juster.prefs.PreferenceManagerSingleton;

public class JusterApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        JusterApplication.context = getApplicationContext();
        PreferenceManagerSingleton.initializeInstance(getApplicationContext());

        //Database Initialisation
        DataBaseManager.initializeInstance(getAppContext());
    }

    public static Context getAppContext() {
        return JusterApplication.context;
    }
}
