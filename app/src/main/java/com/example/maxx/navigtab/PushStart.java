package com.example.maxx.navigtab;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Dzeko on 8/16/2015.
 */
public class PushStart extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(this, "IjIXKtOdGL41OET9tUajbihIqc1AVepu0I7WvJie", "u4i0o8uZ9b69CmaLQXdOyAv22MIFFr9lnDTit8fl");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
