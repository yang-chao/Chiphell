package com.soap.chh.io;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.soap.chh.io.model.Photo;
import com.soap.chh.provider.ChhContract;

import java.util.ArrayList;

/**
 * Created by yc-mac on 14-10-4.
 */
public class PhotoHandler extends JSONHandler {

    private ArrayList<Photo> mPhotos = new ArrayList<Photo>();

    public PhotoHandler(Context context, boolean append) {
        super(context);
        mUri = ChhContract.Photo.CONTENT_URI;
        mAppend = append;
    }

    @Override
    public void process(JsonElement element) {
        for (Photo photo : new Gson().fromJson(element, Photo[].class)) {
            mPhotos.add(photo);
        }
    }

    @Override
    public void makeContentProviderOperations(ArrayList<ContentProviderOperation> list) {
        if (!mAppend) {
            // delete existing data
            Uri uri = ChhContract.Photo.CONTENT_URI;
            list.add(ContentProviderOperation.newDelete(uri).build());
        }

        for (Photo photo : mPhotos) {
            insertPhoto(photo, list);
        }
    }

    private static void insertPhoto(Photo photo, ArrayList<ContentProviderOperation> list) {
        Uri uri = ChhContract.Photo.CONTENT_URI;
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(uri);
        builder.withValue(ChhContract.Photo.PHOTO_ID, photo.id);
        builder.withValue(ChhContract.Photo.PHOTO_TITLE, photo.title);
        builder.withValue(ChhContract.Photo.PHOTO_LINK, photo.link);
        builder.withValue(ChhContract.Photo.PHOTO_IMAGE, photo.image);
        builder.withValue(ChhContract.Photo.PHOTO_TIME, photo.time);
        builder.withValue(ChhContract.Photo.PHOTO_DIGEST, photo.digest);
        list.add(builder.build());
    }
}
