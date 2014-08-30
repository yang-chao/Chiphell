package com.soap.chh.model;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.soap.chh.io.JSONHandler;
import com.soap.chh.provider.ChhContract;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by yc on 14-8-30.
 */
public class NetToDBRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;

    private JSONHandler mJsonHandler;

    public NetToDBRequest(JSONHandler jsonHandler, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
        mJsonHandler = jsonHandler;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String result =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            boolean responseResult = false;
            JsonElement json = new JsonParser().parse(result);
            if (json != null && !json.isJsonNull()) {
                ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
                mJsonHandler.process(json);
                mJsonHandler.makeContentProviderOperations(batch);
                ContentResolver resolver = mJsonHandler.getContext().getContentResolver();
                resolver.applyBatch(ChhContract.CONTENT_AUTHORITY, batch);
                resolver.notifyChange(mJsonHandler.getUri(), null);
                responseResult = true;
            }
            return Response.success(responseResult,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
