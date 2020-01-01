package com.example.beastandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    private int id;

    @SerializedName("videos")
    @Expose
    List<User> feedvideoList = new ArrayList();

    public List<User> getFeedvideoList() {
        return feedvideoList;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
