package com.juster.data.api.database.contentprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by deepakj on 25/7/16.
 */
public class HintListContract {

    private HintListContract() {

    }

    /**
     * Content provider scheme
     */
    private static final String CONTENT_SCHEME = "content://";

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.exzeo.adjustermate.provider.hintlist";

    /**
     * Base URI. (content://com.exzeo.adjustermate.provider.hintlist)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    /**
     * Path component for "note"-type resources..
     */
    private static final String PATH_CLAIMS = "hintlist";

    /**
     * Columns supported by "notes" records.
     */
    public static class HintList implements BaseColumns {
        /**
         * MIME type for lists of entries.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.exzeo.adjustermate.provider." +
                        HintList.TABLE_NAME;
        /**
         * MIME type for individual entries.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.exzeo.adjustermate.provider." +
                        HintList.TABLE_NAME;

        /**
         * Fully qualified URI for "note" resources.
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLAIMS).build();

        /**
         * Table name where records are stored for "note" resources.
         */
        public static final String TABLE_NAME = "checklist";

        //Table Columns
        public static final String COLUMN_HINT_ID = "checklist_hint_id";
        public static final String COLUMN_LOSS_TYPE = "checklist_loss_type";
        public static final String COLUMN_CHECKLISTITEM = "checklist_item";
        public static final String COLUMN_AUDIO = "checklist_audio";
        public static final String COLUMN_VIDEO = "checklist_video";
        public static final String COLUMN_SKETCH = "checklist_sketch";
        public static final String COLUMN_SCAN = "checklist_scan";
        public static final String COLUMN_HINTLIST_EXTRA1 = TABLE_NAME + "_extra_1";
        public static final String COLUMN_HINTLIST_EXTRA2 = TABLE_NAME+"_extra_2";
    }

    public interface IDefaultValues {
        long DEFAULT_HINTLIST_ID = 0 ;
    }

    public interface ILimit {
        int SKIP = -1;
        int TAKE = -1;
    }
}
