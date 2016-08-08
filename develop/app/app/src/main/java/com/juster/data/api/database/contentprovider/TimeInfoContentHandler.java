package com.juster.data.api.database.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by prathmeshs on 28-09-2015.
 */
public class TimeInfoContentHandler {

    private Context mContext = null;

    public class TimeInfoObject {
        private long lastSyncTime;
        private String contentType;

        public long getLastSyncTime() {
            return lastSyncTime;
        }

        public void setLastSyncTime(long lastSyncTime) {
            this.lastSyncTime = lastSyncTime;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }
    }

    public TimeInfoContentHandler(Context context) {
        this.mContext = context;
    }

    public long getKeepContentLastSyncTime() {
        String selection = "(" + TimeInfoContract.Entry.COLUMN_CONTENT_TYPE + "=?)";
        String selectionArgs[] = new String[]{TimeInfoContract.IContentType.FILE_CONTENT};
        TimeInfoObject timeData = getData(selection, selectionArgs);
        if (timeData != null) {
            return timeData.getLastSyncTime();
        }
        return 0;
    }

    public void putKeepTime(long syncTime) {
        String selection = "(" + TimeInfoContract.Entry.COLUMN_CONTENT_TYPE + "=?)";
        String selectionArgs[] = new String[]{TimeInfoContract.IContentType.FILE_CONTENT};
        TimeInfoObject object = getData(selection, selectionArgs);
        if (object == null) {
            insertTime(TimeInfoContract.IContentType.FILE_CONTENT, syncTime);
        } else {
            updateTime(selection, selectionArgs, syncTime);
        }

    }

    private void updateTime(String selection, String[] selectionArgs, long syncTime) {
        ContentValues values = new ContentValues();
        values.put(TimeInfoContract.Entry.COLUMN_CONTENT_UPDATE_TIME, syncTime);
        mContext.getContentResolver().update(TimeInfoContract.Entry.CONTENT_URI, values, selection, selectionArgs);
    }

    private void insertTime(String contentType, long syncTime) {
        ContentValues values = new ContentValues();
        values.put(TimeInfoContract.Entry.COLUMN_CONTENT_TYPE, contentType);
        values.put(TimeInfoContract.Entry.COLUMN_CONTENT_UPDATE_TIME, syncTime);
        mContext.getContentResolver().insert(TimeInfoContract.Entry.CONTENT_URI, values);
    }

    private TimeInfoObject getData(String selection, String selectionArgs[]) {
        Cursor cursor = mContext.getContentResolver().query(TimeInfoContract.Entry.CONTENT_URI, TimeInfoContract.Entry.TIME_INFO_PROJECTION, selection, selectionArgs, null);
        if (cursor != null && cursor.getCount() == 1) {
            TimeInfoObject timeInfoObject = new TimeInfoObject();
            cursor.moveToFirst();
            do {
                timeInfoObject.setContentType(cursor.getString(cursor.getColumnIndex(TimeInfoContract.Entry.COLUMN_CONTENT_TYPE)));
                timeInfoObject.setLastSyncTime(cursor.getLong(cursor.getColumnIndex(TimeInfoContract.Entry.COLUMN_CONTENT_UPDATE_TIME)));
            } while (cursor.moveToNext());
            cursor.close();
            return timeInfoObject;
        }
        return null;
    }
}
