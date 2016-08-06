package com.juster.data.api.database.contentprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by deepakj on 6/8/16.
 */
public class GuideContract {

    private GuideContract() {

    }

    /**
     * Content provider scheme
     */
    private static final String CONTENT_SCHEME = "content://";

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.exzeo.adjustermate.provider.guides";

    /**
     * Base URI. (content://com.exzeo.adjustermate.provider.user)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    /**
     * Path component for "note"-type resources..
     */
    private static final String PATH_CLAIMS = "guideTable";

    /**
     * Columns supported by "notes" records.
     */
    public static class GuideTable implements BaseColumns {
        /**
         * MIME type for lists of entries.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.exzeo.adjustermate.provider." +
                        GuideTable.TABLE_NAME;
        /**
         * MIME type for individual entries.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.exzeo.adjustermate.provider." +
                        GuideTable.TABLE_NAME;

        /**
         * Fully qualified URI for "user" resources.
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLAIMS).build();

        /**
         * Table name where records are stored for "user" resources.
         */
        public static final String TABLE_NAME = "guideTable";

        //Table Columns
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_USER_REVIEW_COUNT = "user_review_count";
        public static final String COLUMN_ADDRESS = "user_address";
        public static final String COLUMN_GENDER = "user_gender";
        public static final String COLUMN_LANGUAGES = "user_languages";
        public static final String COLUMN_DATE_OF_BIRTH = "user_date_of_birth";
        public static final String COLUMN_MOBILE = "user_mobile";
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
