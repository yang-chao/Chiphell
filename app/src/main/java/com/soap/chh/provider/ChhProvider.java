package com.soap.chh.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.soap.chh.util.SelectionBuilder;
import com.soap.chh.provider.ChhDatabase.Tables;
import com.soap.chh.provider.ChhContract.News;
import static com.soap.chh.util.LogUtils.*;

import java.util.ArrayList;
import java.util.Arrays;


public class ChhProvider extends ContentProvider {
    private static final String TAG = makeLogTag(ChhProvider.class);

    private ChhDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int NEWS = 100;
    private static final int NEWS_ID = 101;

    /**
     * Build and return a {@link UriMatcher} that catches all {@link Uri}
     * variations supported by this {@link ContentProvider}.
     */
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ChhContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, "news", NEWS);
        matcher.addURI(authority, "news/*", NEWS_ID);

        return matcher;
    }
    public ChhProvider() {
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ChhDatabase(getContext());
        return true;
    }

    private void deleteDatabase() {
        // TODO: wait for content provider operations to finish, then tear down
        mOpenHelper.close();
        ChhDatabase.deleteDatabase(getContext());
        mOpenHelper = new ChhDatabase(getContext());
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        LOGV(TAG, "delete(uri=" + uri + ")");
        if (uri == ChhContract.BASE_CONTENT_URI) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
//        final int match = sUriMatcher.match(uri);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri);
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        LOGV(TAG, "update(uri=" + uri + ", values=" + values.toString()
                + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
//        final int match = sUriMatcher.match(uri);

        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).update(db, values);
        notifyChange(uri);
        return retVal;
    }

    private void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
        // TODO: Widgets can't register content observers so we refresh widgets separately.
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NEWS:
                return ChhContract.News.CONTENT_TYPE;
            case NEWS_ID:
                return ChhContract.News.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LOGV(TAG, "insert(uri=" + uri + ", values=" + values.toString()
                + ")");

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case NEWS: {
                db.insertOrThrow(Tables.NEWS, null, values);
                notifyChange(uri);
                return News.buildNewsUri(values.getAsString(News.NEWS_ID));
            }
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }

        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);

        // avoid the expensive string concatenation below if not loggable
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            LOGV(TAG, "uri=" + uri + " match=" + match + " proj=" + Arrays.toString(projection) +
                    " selection=" + selection + " args=" + Arrays.toString(selectionArgs) + ")");
        }

        switch (match) {
            default: {
                // Most cases are handled with simple SelectionBuilder
                final SelectionBuilder builder = buildExpandedSelection(uri, match);

                boolean distinct = !TextUtils.isEmpty(
                        uri.getQueryParameter(ChhContract.QUERY_PARAMETER_DISTINCT));

                Cursor cursor = builder
                        .where(selection, selectionArgs)
                        .query(db, distinct, projection, sortOrder, null);
                Context context = getContext();
                if (null != context) {
                    cursor.setNotificationUri(context.getContentResolver(), uri);
                }
                return cursor;
            }
        }
    }

    /**
     * Apply the given set of {@link android.content.ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    /** Returns a tuple of question marks. For example, if count is 3, returns "(?,?,?)". */
    private String makeQuestionMarkTuple(int count) {
        if (count < 1) {
            return "()";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(?");
        for (int i = 1; i < count; i++) {
            stringBuilder.append(",?");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    /**
     * Build a simple {@link SelectionBuilder} to match the requested
     * {@link Uri}. This is usually enough to support {@link #insert},
     * {@link #update}, and {@link #delete} operations.
     */
    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NEWS: {
                return builder.table(Tables.NEWS);
            }
            case NEWS_ID: {
                final String newsId = News.getNewsId(uri);
                return builder.table(Tables.NEWS)
                        .where(News.NEWS_ID + "=?", newsId);
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri for " + match + ": " + uri);
            }
        }
    }

    /**
     * Build an advanced {@link SelectionBuilder} to match the requested
     * {@link Uri}. This is usually only used by {@link #query}, since it
     * performs table joins useful for {@link Cursor} data.
     */
    private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();
        switch (match) {
            case NEWS: {
                return builder.table(Tables.NEWS);
            }
            case NEWS_ID: {
                final String newsId = News.getNewsId(uri);
                return builder.table(Tables.NEWS)
                        .where(News.NEWS_ID + "=?", newsId);
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

}
