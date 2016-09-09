package oleg.hubal.com.tvprogram;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.api.NetworkUtils;
import oleg.hubal.com.tvprogram.api.StaticInfoRequest;
import oleg.hubal.com.tvprogram.database.DBHandler;
import oleg.hubal.com.tvprogram.database.model.Channel;

public class MainActivity extends AppCompatActivity {

    ArrayList<Channel> channelArrayList;
    DBHandler handler;

    private StaticInfoRequest requestTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//TODO check database
        NetworkUtils utils = new NetworkUtils(MainActivity.this);
        if(utils.isConnectingToInternet()) {
            startRequest();
        }

        method();
    }

    private void method() {
        SharedPreferences sPref = getSharedPreferences(Constants.SHARED_PREF_FILE, MODE_PRIVATE);
        Log.d("log123", sPref.getStringSet(Constants.SHARED_PREF_CATEGORY, null).toString());
    }

    private void startRequest() {
        requestTask = new StaticInfoRequest(this);
        requestTask.execute();
    }

}
