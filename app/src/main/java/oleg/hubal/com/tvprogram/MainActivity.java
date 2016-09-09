package oleg.hubal.com.tvprogram;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
        checkData();
    }

    private void checkData() {
        NetworkUtils utils = new NetworkUtils(MainActivity.this);
        SharedPreferences sPref = getSharedPreferences(Constants.SHARED_PREF_FILE, MODE_PRIVATE);
        boolean isDownloaded = sPref.getBoolean(Constants.SHARED_PREF_DWNLD, false);

        if(!isDownloaded) {
            if(utils.isConnectingToInternet()) {
                startRequest();
            } else {
                Toast.makeText(this, "Internet connection is disable", Toast.LENGTH_LONG).show();
            }
        } else {
            setProgramData();
        }
    }

    public void setProgramData() {
        Log.d("log123", "dratuti");
    }

    private void startRequest() {
        requestTask = new StaticInfoRequest(this);
        requestTask.execute();
    }

}
