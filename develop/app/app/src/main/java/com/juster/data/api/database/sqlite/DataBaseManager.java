package com.juster.data.api.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.juster.logger.LoggerUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Anurag Singh on 28/10/15 4:24 PM.
 */
public class DataBaseManager {

    public static String TAG = DataBaseManager.class.getName();

    private AtomicInteger mOpenCounter = new AtomicInteger();

    private static DataBaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(Context context) {
        LoggerUtils.info(TAG, "initializeInstance:::");
        if (instance == null) {
            LoggerUtils.info(TAG, "initializeInstance instance is null");
            instance = new DataBaseManager();
            mDatabaseHelper = DataBaseHelper.getInstance(context);
        }
    }

    public static synchronized DataBaseManager getInstance(Context context) {
        LoggerUtils.info(TAG, "getInstance:::");
        if (instance == null) {
            initializeInstance(context);
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();
        }
    }

}
