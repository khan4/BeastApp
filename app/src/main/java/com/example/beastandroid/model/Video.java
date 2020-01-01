package com.example.beastandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video implements Parcelable {


    @SerializedName("embed_url")
    @Expose
    private String embed_url;

    @SerializedName("default_thumb")
    @Expose
    private String defaultThub;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("views")
    @Expose
    private String views;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("duration")
    @Expose
    private String duration;

    public String getEmbed_url() {
        return embed_url;
    }

    public String getDefaultThub() {
        return defaultThub;
    }

    public String getTitle() {
        return title;
    }

    public String getViews() {
        return views;
    }

    public String getRating() {
        return rating;
    }

    public String getDuration() {
        return duration;
    }


    protected Video(Parcel in) {
        embed_url = in.readString();
        defaultThub = in.readString();
        title = in.readString();
        views = in.readString();
        rating = in.readString();
        duration = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(embed_url);
        parcel.writeString(defaultThub);
        parcel.writeString(title);
        parcel.writeString(views);
        parcel.writeString(rating);
        parcel.writeString(duration);
    }
}
