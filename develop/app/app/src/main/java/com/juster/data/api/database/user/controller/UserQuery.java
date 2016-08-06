package com.juster.data.api.database.user.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juster.data.api.database.contentprovider.ClaimContract;
import com.juster.data.api.database.contentprovider.UserContract;
import com.juster.data.api.database.sqlite.DataBaseManager;
import com.juster.data.api.database.user.model.UserModel;
import com.juster.logger.LoggerUtils;

/**
 * Created by deepakj on 2/8/16.
 */
public class UserQuery {
    
    public static String TAG = UserQuery.class.getName();
    private Context mContext = null;

    public static final String USERTABLE_PROJECTION[] = new String[]{
            UserContract.UserTable.COLUMN_USER_ID,
            UserContract.UserTable.COLUMN_USER_EMAIL,
            UserContract.UserTable.COLUMN_USER_PHONE_NO,
            UserContract.UserTable.COLUMN_USER_PASSWORD,
            UserContract.UserTable.COLUMN_USER_BASIC_TOKEN,
            UserContract.UserTable.COLUMN_GOOGLE_EMAIL,
            UserContract.UserTable.COLUMN_GOOGLE_TOKEN,
    };

    public interface IQueryStatus {
        int FAIL = 0;
        int SUCCESS = 1;
    }

    public UserQuery(Context context) {
        this.mContext = context;
    }

    //Shows All the row count of the Activity related Table
    public int getCount() {

        int count = 0 ;
        SQLiteDatabase localSQLiteDatabase = null;
        DataBaseManager manager = null;

        try {
            manager = DataBaseManager.getInstance(mContext);
            localSQLiteDatabase = manager.openDatabase();
            String[] tableColumns = new String[]{"COUNT(" + UserContract.UserTable._ID + ")"};
            Cursor localCursor1 = localSQLiteDatabase.query(UserContract.UserTable.TABLE_NAME,
                    tableColumns, null, null, null, null, null);
            if (localCursor1.moveToFirst()) {
                LoggerUtils.info(TAG, "UserTable getCount::: "+ UserContract.UserTable
                        .TABLE_NAME +" Total records = " + localCursor1.getInt(0));
            }
            count = localCursor1.getInt(0);
            localCursor1.close();
        } finally {
            if (manager != null) {
                manager.closeDatabase();
            }
        }
        return count;
    }

    //Method to Fetch Activity
    private UserModel getUserInfo() {

        UserModel userModel = new UserModel();
        DataBaseManager manager = null;
        SQLiteDatabase localSQLiteDatabase = null;
        Cursor localCursor = null;

        try {
            manager = DataBaseManager.getInstance(mContext);
            localSQLiteDatabase = manager.openDatabase();

            // Order will be null if no skip and take
            localCursor = localSQLiteDatabase.query(UserContract.UserTable.TABLE_NAME,
                    USERTABLE_PROJECTION, null, null, null, null, null);
            LoggerUtils.info(TAG, "getUserInfo:::"+localCursor.getCount());

            if (localCursor.moveToFirst()) {
                do {
                    int i = 0;
                    userModel.setUser_id(localCursor.getLong(i++));
                    userModel.setUseremail(localCursor.getString(i++));
                    userModel.setPhoneNo(localCursor.getString(i++));
                    userModel.setPassword(localCursor.getString(i++));
                    userModel.setBasic_token(localCursor.getString(i++));
                    userModel.setgEmail(localCursor.getString(i++));
                    userModel.setgToken(localCursor.getString(i));
                } while (localCursor.moveToNext());
            }
        } finally {
            if (localCursor != null) {
                localCursor.close();
            }
            if (manager != null) {
                manager.closeDatabase();
            }
        }
        return userModel;
    }

    public int updateUserInfo(UserModel userModel) {

        LoggerUtils.info(TAG, "updateUserInfo:::");
        int result = IQueryStatus.FAIL;
        SQLiteDatabase localSQLiteDatabase = null;
        DataBaseManager manager = null;

        try {
            manager = DataBaseManager.getInstance(mContext);
            localSQLiteDatabase = manager.openDatabase();
            localSQLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(UserContract.UserTable.COLUMN_USER_ID, userModel.getUser_id());
            values.put(UserContract.UserTable.COLUMN_USER_EMAIL, userModel.getUseremail());
            values.put(UserContract.UserTable.COLUMN_USER_PASSWORD, userModel.getPassword());
            values.put(UserContract.UserTable.COLUMN_USER_PHONE_NO, userModel.getPhoneNo());
            values.put(UserContract.UserTable.COLUMN_GOOGLE_EMAIL, userModel.getgEmail());
            values.put(UserContract.UserTable.COLUMN_USER_BASIC_TOKEN, userModel.getBasic_token());
            values.put(UserContract.UserTable.COLUMN_GOOGLE_TOKEN, userModel.getgToken());

            if(getCount() == 0){
                localSQLiteDatabase.insert(UserContract.UserTable.TABLE_NAME, null , values);
            }else {
                localSQLiteDatabase.update(UserContract.UserTable.TABLE_NAME, values,
                        UserContract.UserTable._ID + " = ?", new String[]{String
                        .valueOf(0)});
            }

            localSQLiteDatabase.setTransactionSuccessful();
            localSQLiteDatabase.endTransaction();
            result = IQueryStatus.SUCCESS;

        } finally {
            if (manager != null) {
                manager.closeDatabase();
            }
        }
        return result;
    }

    // Method to get all UserTable from UserTable table
    public UserModel getUserData() {
        return getUserInfo();
    }
}
