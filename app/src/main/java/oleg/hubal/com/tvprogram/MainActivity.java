package oleg.hubal.com.tvprogram;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import oleg.hubal.com.tvprogram.api.NetworkUtils;
import oleg.hubal.com.tvprogram.api.StaticInfoRequest;
import oleg.hubal.com.tvprogram.fragments.CategoryListFragment;
import oleg.hubal.com.tvprogram.fragments.ChannelListFragment;
import oleg.hubal.com.tvprogram.fragments.ViewPagerFragment;
import oleg.hubal.com.tvprogram.service.AlarmReceiver;

public class MainActivity extends AppCompatActivity {

    private StaticInfoRequest requestTask;

    private boolean isDownloaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkData();
    }

    private void checkData() {
        NetworkUtils utils = new NetworkUtils(MainActivity.this);
        SharedPreferences sPref = getSharedPreferences(Constants.SHARED_PREF_FILE, MODE_PRIVATE);
        isDownloaded = sPref.getBoolean(Constants.SHARED_PREF_DWNLD, false);

        if(!isDownloaded) {
            if (utils.isConnectingToInternet()) {
//                StartRequest
                requestTask = new StaticInfoRequest(this);
                requestTask.execute();
            } else {
                Toast.makeText(this, R.string.no_internet_toast, Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            startApplication();;
        }
    }

    public void startApplication() {
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
    }
}
