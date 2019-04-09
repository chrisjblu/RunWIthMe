package com.example.runwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PremiumActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textTF = (TextView) findViewById(R.id.text);
        TextView textt = (TextView) findViewById(R.id.text);
        TextView texttt = (TextView) findViewById(R.id.text);
        TextView textttt = (TextView) findViewById(R.id.text);
        TextView textttttt = (TextView) findViewById(R.id.text);


        setContentView(R.layout.activity_premium);
        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.running);

        MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);

        videoView.start();



    }


}
