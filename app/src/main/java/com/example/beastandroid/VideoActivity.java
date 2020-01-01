package com.example.beastandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beastandroid.model.User;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = "VideoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Bundle intent = getIntent().getExtras();
        User user = intent.getParcelable("User");

        if (user!=null)
        Log.d(TAG, "onCreate: "+user.getVideo().getTitle()+"\n Url is "+user.getVideo().getEmbed_url());

    }
}
