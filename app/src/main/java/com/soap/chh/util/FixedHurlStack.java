package com.soap.chh.util;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yc-mac on 14-10-5.
 */
public class FixedHurlStack extends HurlStack {

    @Override protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Fix for bug in Android runtime(!!!):
        // https://code.google.com/p/android/issues/detail?id=24672
        // http://stackoverflow.com/questions/18686671/com-android-volley-noconnectionerror-after-pausing-app
        connection.setRequestProperty("Accept-Encoding", "");
        return connection;
    }
}
