package oleg.hubal.com.tvprogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.api.NetworkUtils;
import oleg.hubal.com.tvprogram.api.RequestTask;
import oleg.hubal.com.tvprogram.database.DBHandler;
import oleg.hubal.com.tvprogram.database.model.Channel;

public class MainActivity extends AppCompatActivity {

    ArrayList<Channel> channelArrayList;
    DBHandler handler;

    private RequestTask requestTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//TODO check database
        NetworkUtils utils = new NetworkUtils(MainActivity.this);
        if(utils.isConnectingToInternet()) {
            startRequest();
        }
    }

    private void startRequest() {
        requestTask = new RequestTask(this, Constants.CHANNEL_JSON);
        requestTask.execute();
    }

}
