package com.soap.chh.io.model;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.soap.chh.io.JSONHandler;
import com.soap.chh.provider.ChhContract;

import java.util.ArrayList;

/**
 * Created by yc on 14-8-28.
 */
public class NewsHandler extends JSONHandler {

    private ArrayList<News> mNewses = new ArrayList<News>();
    public NewsHandler(Context context) {
        super(context);
    }

    @Override
    public void process(JsonElement element) {
        for (News news : new Gson().fromJson(element, News[].class)) {
            mNewses.add(news);
        }
    }

    @Override
    public void makeContentProviderOperations(ArrayList<ContentProviderOperation> list) {
        // delete existing data
        Uri uri = ChhContract.News.CONTENT_URI;
        list.add(ContentProviderOperation.newDelete(uri).build());

        for (News news : mNewses) {
            insertNews(news, list);
        }
    }

    private static void insertNews(News news, ArrayList<ContentProviderOperation> list) {
        Uri uri = ChhContract.News.CONTENT_URI;
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(uri);
        builder.withValue(ChhContract.News.NEWS_ID, news.id);
        builder.withValue(ChhContract.News.NEWS_TITLE, news.title);
        builder.withValue(ChhContract.News.NEWS_LINK, news.link);
        builder.withValue(ChhContract.News.NEWS_CATEGORY, news.category);
        builder.withValue(ChhContract.News.NEWS_TIME, news.time);
        builder.withValue(ChhContract.News.NEWS_AUTHOR, news.author);
        builder.withValue(ChhContract.News.NEWS_MESSAGE_COUNT, news.messageCount);
        list.add(builder.build());
    }
}
