package com.juster.data.api.database.contentprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by prathmeshs on 06-11-2015.
 */
public class TimeInfoContract {

    private TimeInfoContract() {

    }

    // Content provider authority.
    public static final String CONTENT_AUTHORITY = "com.exzeo.adjustermate.provider.timeinfo";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_ENTRIES = "timeentries";


    //------------------- Common DB Queries ---------------------//
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    /**
     * SQL statement to drop "entry" table.
     */
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +  TimeInfoContract
            .Entry.TABLE_NAME;

    //------------------- Common DB Queries ---------------------//

    public static class Entry implements BaseColumns {
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.exzeotime.entries";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.exzeotime.entry";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

        public static final String TABLE_NAME = "exzeo_time";

        //Table Columns
        public static final String COLUMN_CONTENT_UPDATE_TIME = "content_update_time";
        public static final String COLUMN_CONTENT_TYPE = "content_type";

        public static final String TIME_INFO_PROJECTION[] = new String[]{
                TimeInfoContract.Entry._ID,
                TimeInfoContract.Entry.COLUMN_CONTENT_UPDATE_TIME,
                TimeInfoContract.Entry.COLUMN_CONTENT_TYPE};

    }

    public interface IContentType {
        String FILE_CONTENT = ClaimProvider.class.getName();
    }
}