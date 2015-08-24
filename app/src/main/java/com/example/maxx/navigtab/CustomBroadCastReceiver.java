package com.example.maxx.navigtab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.maxx.navigtab.helper.MySQLiteHelper;
import com.example.maxx.navigtab.model.Quotes;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dzeko on 8/17/2015.
 */
public class CustomBroadCastReceiver extends ParsePushBroadcastReceiver {
    public static final String TAG = CustomBroadCastReceiver.class.getSimpleName();

    @Override
    protected Class<? extends Activity> getActivity(Context context, Intent intent) {

        return GetExtra.class; // the activity that shows up

    }

    public CustomBroadCastReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent)
    {
        super.onPushReceive(context,intent);
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(context);
        if(intent == null)
        {
            return;
        }

        try {
            JSONObject notification = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            String notificationReceived = notification.getString("alert");
            mySQLiteHelper.addQuotes(new Quotes(notificationReceived));
            Log.d(TAG, notificationReceived);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context,intent);
    }
}
