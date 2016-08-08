package com.juster.data.api.database.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.juster.data.api.database.handler.SelectionBuilder;
import com.juster.data.api.database.sqlite.DataBaseManager;
import com.juster.logger.LoggerUtils;


public class TimeInfoContentProvider extends ContentProvider {

    public static String TAG = TimeInfoContentProvider.class.getName();

    private static final String AUTHORITY = TimeInfoContract.CONTENT_AUTHORITY;
    public static final int ROUTE_ENTRIES = 1;
    public static final int ROUTE_ENTRIES_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, "timeentries", ROUTE_ENTRIES);
        sUriMatcher.addURI(AUTHORITY, "timeentries/*", ROUTE_ENTRIES_ID);
    }

    public TimeInfoContentProvider() {
    }

    @Override
    public boolean onCreate() {
        LoggerUtils.info(TAG, "onCreate");
        DataBaseManager.initializeInstance(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ROUTE_ENTRIES:
                return TimeInfoContract.Entry.CONTENT_TYPE;
            case ROUTE_ENTRIES_ID:
                return TimeInfoContract.Entry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /**
     * Perform a database query by URI.
     * <p/>
     * <p>Currently supports returning all entries (/entries) and individual entries by ID
     * (/entries/{ID}).
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SelectionBuilder builder = new SelectionBuilder();
        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {
            case ROUTE_ENTRIES_ID:
                // Return a single entry, by ID.
                String id = uri.getLastPathSegment();
                builder.where(TimeInfoContract.Entry._ID + "=?", id);
            case ROUTE_ENTRIES:
                // Return all known entries.
                builder.table(TimeInfoContract.Entry.TABLE_NAME)
                        .where(selection, selectionArgs);
                Cursor c = builder.query(DataBaseManager.getInstance(getContext()).openDatabase(), projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                assert getContext() != null;
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /**
     * Insert a new entry into the database.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        assert DataBaseManager.getInstance(getContext()).openDatabase() != null;
        final int match = sUriMatcher.match(uri);
        Uri result;
        switch (match) {
            case ROUTE_ENTRIES:
                long id = DataBaseManager.getInstance(getContext()).openDatabase().insertOrThrow(TimeInfoContract.Entry.TABLE_NAME, null, values);
                result = Uri.parse(TimeInfoContract.Entry.CONTENT_URI + "/" + id);
                break;
            case ROUTE_ENTRIES_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        assert getContext() != null;
        getContext().getContentResolver().notifyChange(uri, null, false);
        return result;
    }

    /**
     * Delete an entry by database by URI.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case ROUTE_ENTRIES:
                count = builder.table(TimeInfoContract.Entry.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .delete(DataBaseManager.getInstance(getContext()).openDatabase());
                break;
            case ROUTE_ENTRIES_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(TimeInfoContract.Entry.TABLE_NAME)
                        .where(TimeInfoContract.Entry._ID + "=?", id)
                        .where(selection, selectionArgs)
                        .delete(DataBaseManager.getInstance(getContext()).openDatabase());
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        assert getContext() != null;
        getContext().getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    /**
     * Update an etry in the database by URI.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case ROUTE_ENTRIES:
                count = builder.table(TimeInfoContract.Entry.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .update(DataBaseManager.getInstance(getContext()).openDatabase(), values);
                break;
            case ROUTE_ENTRIES_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(TimeInfoContract.Entry.TABLE_NAME)
                        .where(TimeInfoContract.Entry._ID + "=?", id)
                        .where(selection, selectionArgs)
                        .update(DataBaseManager.getInstance(getContext()).openDatabase(), values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        assert getContext() != null;
        getContext().getContentResolver().notifyChange(uri, null, false);
        return count;
    }
}
