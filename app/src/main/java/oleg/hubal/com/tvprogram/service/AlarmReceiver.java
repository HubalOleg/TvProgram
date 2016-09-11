package oleg.hubal.com.tvprogram.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by User on 11.09.2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String DEBUG_TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("log123", "requesting download service");

        Intent downloader = new Intent(context, TvListingDownloaderService.class);
        context.startService(downloader);
    }
}
