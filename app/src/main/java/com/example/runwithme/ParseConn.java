package com.example.runwithme;

import android.app.Application;

import com.parse.Parse;

public class ParseConn extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("03c0dc38863bb300a45c779c27451c95af905044")
                // if defined
                .clientKey("87c819ccc6dcb22b56210c809699f2b3ef797ea1")
                .server("http://13.58.100.213:80/parse/")
                .build()
        );
    }
}
