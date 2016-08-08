package com.juster.data.api.database.contentprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by deepakj on 25/7/16.
 */
public class UserContract {

    private UserContract() {

    }

    /**
     * Content provider scheme
     */
    private static final String CONTENT_SCHEME = "content://";

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.exzeo.adjustermate.provider.user";

    /**
     * Base URI. (content://com.exzeo.adjustermate.provider.user)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    /**
     * Path component for "note"-type resources..
     */
    private static final String PATH_CLAIMS = "usertable";

    /**
     * Columns supported by "notes" records.
     */
    public static class UserTable implements BaseColumns {
        /**
         * MIME type for lists of entries.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.exzeo.adjustermate.provider." +
                        UserTable.TABLE_NAME;
        /**
         * MIME type for individual entries.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.exzeo.adjustermate.provider." +
                        UserTable.TABLE_NAME;

        /**
         * Fully qualified URI for "user" resources.
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLAIMS).build();

        /**
         * Table name where records are stored for "user" resources.
         */
        public static final String TABLE_NAME = "userinfo";

        //Table Columns
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_EMAIL = "user_mail";
        public static final String COLUMN_USER_PASSWORD = "user_basic_password";
        public static final String COLUMN_USER_PHONE_NO = "user_phone";
        public static final String COLUMN_USER_BASIC_TOKEN = "user_basic_auth";
        public static final String COLUMN_GOOGLE_EMAIL = "user_g_email";
        public static final String COLUMN_GOOGLE_TOKEN = "user_g_token";
        public static final String COLUMN_LAST_LOGIN_WITH = "user_last_login";
        public static final String COLUMN_USER_EXTRA1 = TABLE_NAME + "_extra_1";
        public static final String COLUMN_USER_EXTRA2 = TABLE_NAME+"_extra_2";

    }

    public interface IDefaultValues {
        long LAST_LOGIN_WITH = 1;
    }

    public interface LOGINTYPE {
        int GOOGLE = 1;
        int BASIC = 2;
    }

    public interface ILimit {
        int SKIP = -1;
        int TAKE = -1;
    }
}

