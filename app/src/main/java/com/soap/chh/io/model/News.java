package com.soap.chh.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yc-mac on 14-8-28.
 */
public class News {
    public String id;
    public String title;
    public String link;
    public String author;
    @SerializedName("message_count")
    public int messageCount;
    public String category;
    public String time;
}
