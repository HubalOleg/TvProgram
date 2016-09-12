package oleg.hubal.com.tvprogram.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.widget.ResourceCursorAdapter;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.MainActivity;
import oleg.hubal.com.tvprogram.R;
import oleg.hubal.com.tvprogram.api.NetworkUtils;
import oleg.hubal.com.tvprogram.database.DBHandler;
import oleg.hubal.com.tvprogram.database.model.Program;

/**
 * Created by User on 11.09.2016.
 */
public class TvListingDownloaderService extends Service {

    private static final String DEBUG_TAG = "TvListingDownloaderService";
    private DownloaderTask listingDownloader;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("log123", "serviceStarted");

//        programHandler = new ProgramHandler(this);
        listingDownloader = new DownloaderTask();
        listingDownloader.execute();
        return Service.START_FLAG_REDELIVERY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class DownloaderTask extends AsyncTask<Void, Void, Boolean> {

        private static final String DEBUG_TAG       = "TvListingDownloaderService$DownloaderTask";
        private static final int NOTIFY_ID          = 101;

        private String programJson                  = "";
        private HttpURLConnection urlConnection     = null;
        private BufferedReader reader               = null;
        private DBHandler dbHandler;
        private NetworkUtils networkUtils;
        private boolean isDownloading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(isDownloading)
                stopSelf();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            isDownloading = false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d("log123", "listingdownload");
            isDownloading = true;
            dbHandler = new DBHandler(getApplicationContext());
            saveJsonResult(Constants.URL + Constants.PROGRAM_JSON);
            return null;
        }

        private void saveJsonResult(String programJson) {
            try {
                networkUtils = new NetworkUtils(getApplicationContext());
                SharedPreferences sPref = getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE);
                boolean isDataDownloaded = sPref.getBoolean(Constants.SHARED_PREF_PROGRAM_DWNL, false);

                if (!isDataDownloaded) {
                    if (networkUtils.isConnectingToInternet()) {
                    URL url = new URL(programJson);

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod(Constants.GET);
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(inputStream)));

                    Set<String> daysList = new HashSet<>();

                    reader.beginObject();
                    while (reader.hasNext()) {
                        ArrayList<Program> programs = new ArrayList<>();
                        String day = reader.nextName();
                        daysList.add(day);
                        reader.beginObject();
                        while (reader.hasNext()) {
                            reader.nextName();
                            reader.beginObject();
                            reader.nextName();
                            long date = reader.nextLong();
                            reader.nextName();
                            String channel = reader.nextString();
                            reader.nextName();
                            String showName = reader.nextString();
                            reader.endObject();
                            programs.add(new Program(day, date, channel, showName));
                        }
                        dbHandler.addPrograms(programs);
                        reader.endObject();
                    }
//                    Save days list
                    SharedPreferences.Editor sEditor = sPref.edit();
                    sEditor.putStringSet(Constants.SHARED_PREF_DAYS, daysList);
                    sEditor.putBoolean(Constants.SHARED_PREF_PROGRAM_DWNL, true);
                    sEditor.apply();
                    } else {
                        makeNotification(getString(R.string.notify_download_disable));
                    }
                } else {
                    makeNotification(getString(R.string.notify_text));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void makeNotification(String notify) {
            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                    0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            Resources res = getApplicationContext().getResources();
            Notification.Builder builder = new Notification.Builder(getApplicationContext());

            builder.setContentIntent(contentIntent)
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setTicker(res.getString(R.string.notify_text))
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentText(notify)
                    .setContentTitle(res.getString(R.string.notify_title));

            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFY_ID, notification);
        }
    }
}
