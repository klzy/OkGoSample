package com.lzy.demo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsinfoModel implements Serializable {
    @SerializedName("_id")
    public String id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
}
