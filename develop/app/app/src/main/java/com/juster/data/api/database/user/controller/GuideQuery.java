package com.juster.data.api.database.user.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juster.data.api.database.contentprovider.GuideContract;
import com.juster.data.api.database.contentprovider.HintListContract;
import com.juster.data.api.database.contentprovider.UserContract;
import com.juster.data.api.database.sqlite.DataBaseManager;
import com.juster.data.api.database.user.model.GuidesDetail;
import com.juster.data.api.database.user.model.UserModel;
import com.juster.logger.LoggerUtils;

import java.util.ArrayList;

/**
 * Created by deepakj on 6/8/16.
 */
public class GuideQuery {

    public static String TAG = UserQuery.class.getName();
    private Context mContext = null;

    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_RATING = "user_rating";
    public static final String COLUMN_USER_REVIEW_COUNT = "user_review_count";
    public static final String COLUMN_ADDRESS = "user_address";
    public static final String COLUMN_GENDER = "user_gender";
    public static final String COLUMN_LANGUAGES = "user_languages";
    public static final String COLUMN_DATE_OF_BIRTH = "user_date_of_birth";
    public static final String COLUMN_MOBILE = "user_mobile";

    public static final String GUIDETABLE_PROJECTION[] = new String[]{
            GuideContract.GuideTable.COLUMN_USER_ID,
            GuideContract.GuideTable.COLUMN_USER_NAME,
            GuideContract.GuideTable.COLUMN_USER_RATING,
            GuideContract.GuideTable.COLUMN_USER_REVIEW_COUNT,
            GuideContract.GuideTable.COLUMN_ADDRESS,
            GuideContract.GuideTable.COLUMN_GENDER,
            GuideContract.GuideTable.COLUMN_LANGUAGES,
            GuideContract.GuideTable.COLUMN_DATE_OF_BIRTH,
            GuideContract.GuideTable.COLUMN_MOBILE
    };

    public interface IQueryStatus {
        int FAIL = 0;
        int SUCCESS = 1;
    }

    public GuideQuery(Context context) {
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
            String[] tableColumns = new String[]{"COUNT(" + GuideContract.GuideTable._ID + ")"};
            Cursor localCursor1 = localSQLiteDatabase.query(GuideContract.GuideTable.TABLE_NAME,
                    tableColumns, null, null, null, null, null);
            if (localCursor1.moveToFirst()) {
                LoggerUtils.info(TAG, "UserTable getCount::: "+ GuideContract.GuideTable
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
    private ArrayList<GuidesDetail> getUserInfo() {

        ArrayList<GuidesDetail> distinctClaimArrayList = new ArrayList<>();

        DataBaseManager manager = null;
        SQLiteDatabase localSQLiteDatabase = null;
        Cursor localCursor = null;

        try {
            manager = DataBaseManager.getInstance(mContext);
            localSQLiteDatabase = manager.openDatabase();

            // Order will be null if no skip and take
            localCursor = localSQLiteDatabase.query(GuideContract.GuideTable.TABLE_NAME,
                    GUIDETABLE_PROJECTION, null, null, null, null, null);
            LoggerUtils.info(TAG, "getUserInfo:::"+localCursor.getCount());
            if (localCursor.moveToFirst()) {
                do {
                    int i = 0;
                    GuidesDetail guidesDetail = new GuidesDetail();
                    guidesDetail.setUserId(localCursor.getString(i++));
                    guidesDetail.setName(localCursor.getString(i++));
                    guidesDetail.setRating(localCursor.getInt(i++));
                    guidesDetail.setRatingCount(localCursor.getInt(i++));
                    guidesDetail.setAddress(localCursor.getString(i++));
                    guidesDetail.setGender(localCursor.getString(i++));
                    guidesDetail.setLanguages(localCursor.getString(i++));
                    guidesDetail.setDateOfBirth(localCursor.getString(i++));
                    guidesDetail.setMobile(localCursor.getLong(i));
                    distinctClaimArrayList.add(guidesDetail);
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
        return distinctClaimArrayList;
    }

    public int updateUserInfo(ArrayList<GuidesDetail> guidesDetails) {

        LoggerUtils.info(TAG, "updateUserInfo:::");
        int result = IQueryStatus.FAIL;
        SQLiteDatabase localSQLiteDatabase = null;
        DataBaseManager manager = null;

        try {
            manager = DataBaseManager.getInstance(mContext);
            localSQLiteDatabase = manager.openDatabase();
            localSQLiteDatabase.beginTransaction();

            localSQLiteDatabase.delete(GuideContract.GuideTable.TABLE_NAME,
                    "1", null);

            for (int i =0; i < guidesDetails.size();i++) {

                GuidesDetail guidesDetail = guidesDetails.get(i);
                ContentValues values = new ContentValues();
                values.put(GuideContract.GuideTable.COLUMN_USER_ID, guidesDetail.getUserId());
                values.put(GuideContract.GuideTable.COLUMN_USER_NAME, guidesDetail.getName());
                values.put(GuideContract.GuideTable.COLUMN_USER_RATING, guidesDetail.getRating());
                values.put(GuideContract.GuideTable.COLUMN_USER_REVIEW_COUNT, guidesDetail.getRatingCount());
                values.put(GuideContract.GuideTable.COLUMN_ADDRESS, guidesDetail.getGender());
                values.put(GuideContract.GuideTable.COLUMN_ADDRESS, guidesDetail.getAddress());
                values.put(GuideContract.GuideTable.COLUMN_LANGUAGES, guidesDetail.getLanguages());
                values.put(GuideContract.GuideTable.COLUMN_DATE_OF_BIRTH, guidesDetail.getDateOfBirth());
                values.put(GuideContract.GuideTable.COLUMN_MOBILE, guidesDetail.getMobile());

                long row = localSQLiteDatabase.insert(GuideContract.GuideTable.TABLE_NAME, null,
                        values);
                LoggerUtils.info(TAG, "row ::::"+row);
                getCount();
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
    public ArrayList<GuidesDetail> getUserData() {
        return getUserInfo();
    }
}
