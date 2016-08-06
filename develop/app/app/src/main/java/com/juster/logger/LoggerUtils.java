package com.juster.logger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.juster.BuildConfig;


public class LoggerUtils {

    public static boolean DEBUG_MODE = BuildConfig.DEBUG;

    public static void info(@NonNull String TAG, @NonNull String message){
        if(DEBUG_MODE){
            Log.i(TAG,message);
        }
    }
    public static void warning(@NonNull String TAG, @NonNull String message){
        if(DEBUG_MODE){
            Log.w(TAG,message);
        }
    }
    public static void verbose(@NonNull String TAG, @NonNull String message){
        if(DEBUG_MODE){
            Log.v(TAG,message);
        }
    }
    public static void error(@NonNull String TAG, @NonNull String message){
        if(DEBUG_MODE){
            Log.e(TAG,message);
        }
    }
}
