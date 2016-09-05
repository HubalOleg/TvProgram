package oleg.hubal.com.tvprogram;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oleg.hubal.com.tvprogram.api.RequestTask;

public class MainActivity extends AppCompatActivity {

    private RequestTask requestTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startRequest();
    }

    private void startRequest() {
        requestTask = new RequestTask(this);
        requestTask.execute();
    }

}
