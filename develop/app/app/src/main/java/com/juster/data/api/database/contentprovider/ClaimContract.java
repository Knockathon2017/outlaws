package com.juster.data.api.database.contentprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by deepakj on 11/12/15.
 */
public class ClaimContract {

    private ClaimContract() {

    }

    /**
     * Content provider scheme
     */
    private static final String CONTENT_SCHEME = "content://";

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.exzeo.adjustermate.provider.claim";

    /**
     * Base URI. (content://com.exzeo.adjustermate.provider.claim)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    /**
     * Path component for "note"-type resources..
     */
    private static final String PATH_CLAIMS = "claim";

    /**
     * Columns supported by "notes" records.
     */
    public static class Claims implements BaseColumns {
        /**
         * MIME type for lists of entries.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.exzeo.adjustermate.provider." +
                        Claims.TABLE_NAME;
        /**
         * MIME type for individual entries.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.exzeo.adjustermate.provider." +
                        Claims.TABLE_NAME;

        /**
         * Fully qualified URI for "note" resources.
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLAIMS).build();

        /**
         * Table name where records are stored for "note" resources.
         */
        public static final String TABLE_NAME = "claims";

        //Table Columns
        public static final String COLUMN_CLAIM_ID = "claim_id";
        public static final String COLUMN_CLAIM_NUMBER = "claim_number";
        public static final String COLUMN_CLAIM_CREATED_DATE = "claim_created_date";
        public static final String COLUMN_CLAIM_SCHEDULE_DATE_TIME = "claim_schedule_date_time";
        public static final String COLUMN_CLAIM_SCHEDULE_DATE = "claim_schedule_date";

        public static final String COLUMN_CLAIM_INSURER = "claim_insurer";
        public static final String COLUMN_CLAIM_AGENCY = "claim_agency";
        public static final String COLUMN_CLAIM_INSURED = "claim_insured";
        public static final String COLUMN_POLICY_TYPE = "claim_policy_type";
        public static final String COLUMN_LOSS_TYPE = "claim_loss_type";

        public static final String COLUMN_CLAIM_PROP_CITY = "claim_city";
        public static final String COLUMN_CLAIM_PROP_STATE = "claim_state";
        public static final String COLUMN_CLAIM_PROP_COUNTY = "claim_county";
        public static final String COLUMN_CLAIM_PROP_PINCODE = "claim_pincode";
        public static final String COLUMN_CLAIM_PROP_LOC_JSON = "claim_location_json";

        public static final String COLUMN_CLAIM_MEDIA_JSON = "claim_media_json";

        public static final String COLUMN_CLAIM_CLAIM_STATUS = "claim_claim_status";
        public static final String COLUMN_CLAIM_DEFAULT_ROOM = "claim_default_room";
        public static final String COLUMN_CLAIM_EXTRA1 = TABLE_NAME + "_extra_1";
        public static final String COLUMN_CLAIM_EXTRA2 = TABLE_NAME+"_extra_2";

    }

    public interface IClaimStatus {
        int SCHEDULED = 0;
        int NOT_SCHEDULED = 1;
        int PAST = 2;
    }

    public interface IDefaultValues {
        int DEFAULT_CLAIM_ID = 0;
        String DEFAULT_CLAIM_NUMBER = null;
        int DEFAULT_CLAIM_STATUS = 0;
        long DEFAULT_CLAIM_SCHEDULED_DATE = 0;
        long COLUMN_CLAIM_CREATED_DATE = 0;
    }

    public interface ILimit {
        int SKIP = -1;
        int TAKE = -1;
    }
}
