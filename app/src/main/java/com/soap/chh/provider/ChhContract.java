package com.soap.chh.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.text.format.DateUtils;

/**
 * Created by yc on 14-8-28.
 */
public class ChhContract {


    interface NewsColumns {
        String NEWS_ID = "news_id";
        String NEWS_TITLE = "news_title";
        String NEWS_LINK = "news_link";
        String NEWS_TIME = "news_time";
        String NEWS_CATEGORY = "news_category";
        String NEWS_AUTHOR = "news_author";
        String NEWS_MESSAGE_COUNT = "news_message_count";
    }


    public static final String CONTENT_AUTHORITY = "com.soap.chh";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_NEWS = "news";

    public static class News implements NewsColumns, BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.chh.news";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.chh.news";

        /** "ORDER BY" clauses. */
        public static final String DEFAULT_SORT = NewsColumns.NEWS_TIME + " DESC";

        /** Build {@link Uri} for requested {@link #NEWS_ID}. */
        public static Uri builNewsUri(String newsId) {
            return CONTENT_URI.buildUpon().appendPath(newsId).build();
        }

        /** Read {@link #NEWS_ID} from {@link News} {@link Uri}. */
        public static String getNewsId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static Uri addCallerIsSyncAdapterParameter(Uri uri) {
        return uri.buildUpon().appendQueryParameter(
                ContactsContract.CALLER_IS_SYNCADAPTER, "true").build();
    }

    public static boolean hasCallerIsSyncAdapterParameter(Uri uri) {
        return TextUtils.equals("true",
                uri.getQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER));
    }
}
