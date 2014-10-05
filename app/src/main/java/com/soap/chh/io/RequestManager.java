package com.soap.chh.io;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.soap.chh.util.FixedHurlStack;

/**
 * Created by yc on 14-8-29.
 */
public class RequestManager {

    private static RequestQueue mRequestQueue;

    /**
     * Nothing to see here.
     */
    private RequestManager() {
        // no instances
    }

    /**
     * @param context
     * 			application context
     */
    public static void init(Context context) {
//        mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue = Volley.newRequestQueue(context,new FixedHurlStack());
    }

    /**
     * @return
     * 		instance of the queue
     * @throws
     * 		IllegalStateException if init has not yet been called
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }


}
