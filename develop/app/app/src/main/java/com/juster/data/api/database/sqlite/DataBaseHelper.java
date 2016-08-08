package com.juster.data.api.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.juster.data.api.database.contentprovider.ClaimContract;
import com.juster.data.api.database.contentprovider.GuideContract;
import com.juster.data.api.database.contentprovider.HintListContract;
import com.juster.data.api.database.contentprovider.RoomContract;
import com.juster.data.api.database.contentprovider.TimeInfoContract;
import com.juster.data.api.database.contentprovider.UserContract;
import com.juster.logger.LoggerUtils;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DataBaseHelper.class.getSimpleName();

    //Schema version
    public static final int DATABASE_VERSION = 1;

    //Filename for SQLite file
    public static final String DATABASE_NAME = "juster.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = " ,";
    private static final String TYPE_UNIQUE = " UNIQUE";
    private static final String TYPE_AUTOINCREMENT = " AUTOINCREMENT";

    private static DataBaseHelper sInstance;

    //create last sync query
    private static final String SQL_LAST_SYNC_CREATE_ENTRIES =
            "CREATE TABLE " + TimeInfoContract.Entry.TABLE_NAME + " (" +
                    TimeInfoContract.Entry._ID + " INTEGER PRIMARY KEY" + TYPE_AUTOINCREMENT + "," +
                    TimeInfoContract.Entry.COLUMN_CONTENT_TYPE + TYPE_TEXT + COMMA_SEP +
                    TimeInfoContract.Entry.COLUMN_CONTENT_UPDATE_TIME + TYPE_INTEGER + ")";


    //create file query
    private static final String SQL_CREATE_HINTLIST =
            "CREATE TABLE " + GuideContract.GuideTable.TABLE_NAME + " (" +
                    GuideContract.GuideTable._ID + " INTEGER PRIMARY KEY" + TYPE_AUTOINCREMENT + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_USER_ID + TYPE_INTEGER + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_USER_NAME + TYPE_TEXT + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_LANGUAGES + TYPE_TEXT + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_GENDER + TYPE_TEXT + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_DATE_OF_BIRTH + TYPE_TEXT + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_MOBILE + TYPE_INTEGER + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_ADDRESS + TYPE_TEXT + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_USER_REVIEW_COUNT + TYPE_INTEGER + COMMA_SEP +
                    GuideContract.GuideTable.COLUMN_USER_RATING + TYPE_INTEGER + ")";



    //create file query
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + UserContract.UserTable.TABLE_NAME + " (" +
                    UserContract.UserTable._ID + " INTEGER PRIMARY KEY" + TYPE_AUTOINCREMENT + COMMA_SEP +
                    UserContract.UserTable.COLUMN_USER_ID+ TYPE_TEXT + COMMA_SEP +
                    UserContract.UserTable.COLUMN_USER_EMAIL + TYPE_TEXT + COMMA_SEP +
                    UserContract.UserTable.COLUMN_USER_PASSWORD + TYPE_TEXT + COMMA_SEP +
                    UserContract.UserTable.COLUMN_USER_PHONE_NO + TYPE_TEXT + COMMA_SEP +
                    UserContract.UserTable.COLUMN_USER_BASIC_TOKEN + TYPE_TEXT + COMMA_SEP +
                    UserContract.UserTable.COLUMN_GOOGLE_EMAIL + TYPE_TEXT + COMMA_SEP +
                    UserContract.UserTable.COLUMN_GOOGLE_TOKEN + TYPE_TEXT + COMMA_SEP +
                    UserContract.UserTable.COLUMN_LAST_LOGIN_WITH + TYPE_INTEGER + COMMA_SEP +
                    UserContract.UserTable.COLUMN_USER_EXTRA1 + TYPE_INTEGER + COMMA_SEP +
                    UserContract.UserTable.COLUMN_USER_EXTRA2 + TYPE_TEXT + ")";


    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        LoggerUtils.info(TAG, "DataBaseHelper()");
    }

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataBaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        LoggerUtils.info(TAG, "onCreate::: " + SQL_CREATE_USER_TABLE);

        db.execSQL(SQL_CREATE_HINTLIST);
        LoggerUtils.info(TAG, "onCreate::: " + SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LoggerUtils.info(TAG, "onUpgrade::: oldVersion=" + oldVersion + " ,newVersion=" + newVersion);
    }
}
