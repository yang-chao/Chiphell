package com.soap.chh.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.soap.chh.util.LogUtils.*;
import com.soap.chh.provider.ChhContract.*;

/**
 * Created by user on 14-8-28.
 */
public class ChhDatabase extends SQLiteOpenHelper {
    private static final String TAG = makeLogTag(ChhDatabase.class);

    private static final String DATABASE_NAME = "chh.db";
    private static final int DATABASE_VERSION = 1;


    interface Tables {
        String NEWS = "news";
        String Photo = "photo";
    }

    public ChhDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 新闻
        db.execSQL("CREATE TABLE " + Tables.NEWS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NewsColumns.NEWS_ID + " TEXT NOT NULL,"
                + NewsColumns.NEWS_TITLE + " TEXT NOT NULL,"
                + NewsColumns.NEWS_LINK + " TEXT NOT NULL,"
                + NewsColumns.NEWS_TIME + " TEXT,"
                + NewsColumns.NEWS_CATEGORY + " TEXT,"
                + NewsColumns.NEWS_AUTHOR + " TEXT,"
                + NewsColumns.NEWS_MESSAGE_COUNT + " INTEGER DEFAULT 0)");

        // 照片
        db.execSQL("CREATE TABLE " + Tables.Photo + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PhotoColumns.PHOTO_ID + " TEXT NOT NULL,"
                + PhotoColumns.PHOTO_TITLE + " TEXT NOT NULL,"
                + PhotoColumns.PHOTO_LINK + " TEXT NOT NULL,"
                + PhotoColumns.PHOTO_TIME + " TEXT,"
                + PhotoColumns.PHOTO_DIGEST + " TEXT,"
                + PhotoColumns.PHOTO_IMAGE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LOGD(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

//        db.execSQL("DROP TABLE IF EXISTS " + Tables.NEWS);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
