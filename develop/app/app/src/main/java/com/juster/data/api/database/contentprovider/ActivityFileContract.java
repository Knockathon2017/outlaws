package com.juster.data.api.database.contentprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by deepakj on 11/12/15.
 */
public class ActivityFileContract {

    private ActivityFileContract() {

    }

    /**
     * Content provider scheme
     */
    private static final String CONTENT_SCHEME = "content://";

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.exzeo.adjustermate.provider.activity.files";

    /**
     * Base URI. (content://com.exzeo.adjustermate.provider.note)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    /**
     * Path component for "note"-type resources..
     */
    private static final String PATH_ACTIVITY_FILES = "activityfiles";

    /**
     * Columns supported by "notes" records.
     */
    public static class ActivityFile implements BaseColumns {
        /**
         * MIME type for lists of entries.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +  "/vnd.com.exzeo.adjustermate.provider." +
                        ActivityFile.TABLE_NAME;
        /**
         * MIME type for individual entries.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +  "/vnd.com.exzeo.adjustermate.provider."
                        + ActivityFile.TABLE_NAME;

        /**
         * Fully qualified URI for "note" resources.
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTIVITY_FILES).build();

        /**
         * Table name where records are stored for "note" resources.
         */
        public static final String TABLE_NAME = "activityfile";

        //Table Columns
        public static final String COLUMN_ACTIVITY_ID = "act_id";

        public static final String COLUMN_ACTIVITY_UPLOADEDBY = "act_uploadedby";
        public static final String COLUMN_ACTIVITY_UPLOADEDTIME = "act_uploadedtime";

        public static final String COLUMN_ACTIVITY_FILE_ID = "act_file_id";
        public static final String COLUMN_ACTIVITY_FILE_GUID = "act_file_guid";

        public static final String COLUMN_ACTIVITY_FILE_CAPTION = "act_file_caption";
        public static final String COLUMN_ACTIVITY_FILE_TITLE = "act_file_title";
        public static final String COLUMN_ACTIVITY_FILE_SIZE = "act_file_size";
        public static final String COLUMN_ACTIVITY_FILE_MIME_TYPE = "act_file_mime_type";

        public static final String COLUMN_ACTIVITY_FILE_THUMBNAIL_URI = "act_file_thumb_uri";
        public static final String COLUMN_ACTIVITY_FILE_OPEN_URL = "act_file_open_url";

        public static final String COLUMN_ACTIVITY_FILE_UPDATE_TIME = "act_file_updatetime";

        public static final String COLUMN_ACTIVITY_FILE_DOWNLOAD_STATE = "act_file_download_state";
        public static final String COLUMN_ACTIVITY_FILE_DOWNLOADED_PATH = "act_file_download_path";
    }

    public interface ISyncStatus {
        int NOT_SYNCED = 0;
        int SYNCED = 1;
        int SYNCING = 2;
    }

    public interface IDefaultValues {
        int DEFAULT_ACTIVITY_ID = 0;
        int DEFAULT_ACTIVITY_FILE_ID = 0;
        String DEFAULT_ACTIONS = null;
        String DEFAULT_ACTIONS_NO_PERMISSION = "nopermission";
    }

    public interface IPrivacyType {
        String INTERNAL = "Private";
        String PUBLIC = "Public";
        String DEFAULT = "None";
    }

    public interface ILimit {
        int SKIP = -1;
        int TAKE = -1;
    }

}
