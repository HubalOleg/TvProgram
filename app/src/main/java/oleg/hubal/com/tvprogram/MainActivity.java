package oleg.hubal.com.tvprogram;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.api.NetworkUtils;
import oleg.hubal.com.tvprogram.api.StaticInfoRequest;
import oleg.hubal.com.tvprogram.database.DBHandler;
import oleg.hubal.com.tvprogram.database.model.Channel;

public class MainActivity extends AppCompatActivity {

    private StaticInfoRequest requestTask;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence drawerTitle;
    private CharSequence title;

    private String[] viewsNames;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;
        checkData();
    }

    private void checkData() {
        NetworkUtils utils = new NetworkUtils(MainActivity.this);
        SharedPreferences sPref = getSharedPreferences(Constants.SHARED_PREF_FILE, MODE_PRIVATE);
        boolean isDownloaded = sPref.getBoolean(Constants.SHARED_PREF_DWNLD, false);

        if(!isDownloaded) {
            if(utils.isConnectingToInternet()) {
//                StartRequest
                requestTask = new StaticInfoRequest(this);
                requestTask.execute();
            } else {
                Toast.makeText(this, "Internet connection is disable", Toast.LENGTH_LONG).show();
            }
        } else {
            buildProgram();
        }
    }

    public void buildProgram() {
        buildNavigationDrawer();
    }

    private void buildNavigationDrawer() {
        title = getTitle();
        drawerTitle = getResources().getString(R.string.menu);
        viewsNames = getResources().getStringArray(R.array.navig_items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, viewsNames));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_menu,
                R.string.close_menu) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            displayView(0);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id ) {
            displayView(position);
        }
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FirstFragment();
                break;
            case 1:
                Log.d("log123", "second fragment");
                break;
            case 2:
                Log.d("log123", "third fragment");
                break;
            case 3:
                Log.d("log123", "fourth fragment");
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            setTitle(viewsNames[position]);
            drawerLayout.closeDrawer(drawerList);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence _title) {
        title = _title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
