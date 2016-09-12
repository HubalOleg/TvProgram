package oleg.hubal.com.tvprogram.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.R;
import oleg.hubal.com.tvprogram.api.ProgramHandler;
import oleg.hubal.com.tvprogram.database.DBHandler;
import oleg.hubal.com.tvprogram.database.model.Program;

/**
 * Created by User on 09.09.2016.
 */
public class ViewPagerFragment extends Fragment implements View.OnClickListener {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    private View view;
    private Button btnShowData;

    private DBHandler dbHandler;
    private ProgramHandler programHandler;

    private SharedPreferences sPref;
    private ArrayList<Program> programs;
    private String[] channels, days;
    private String day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        dbHandler = new DBHandler(getActivity());
        programs = new ArrayList<>();

        setViews();
        return view;
    }

    @Override
    public void onClick(View v) {
        GetDataRequest getDataRequest = new GetDataRequest();
        getDataRequest.execute();
    }

    private void setViews() {
        btnShowData = (Button) view.findViewById(R.id.btn_show_FVP);
        btnShowData.setOnClickListener(this);
        setSpinner();
    }

    private void setSpinner() {
        sPref = getActivity().getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        Set<String> daysSet = sPref.getStringSet(Constants.SHARED_PREF_DAYS, null);
        if (daysSet != null) {
            days = daysSet.toArray(new String[daysSet.size()]);
            Arrays.sort(days);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, days);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner spinner = (Spinner) view.findViewById(R.id.spinner_FVP);
            spinner.setAdapter(adapter);

            spinner.setPrompt("Choose");
            spinner.setSelection(0);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day = days[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    day = days[0];
                }
            });

        }
    }

    private void initPager() {
        pager = (ViewPager) view.findViewById(R.id.vp_pager_FP);
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    private class GetDataRequest extends AsyncTask<Void, Void, ArrayList<Program>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),
                    getActivity().getString(R.string.dialog_title),
                    getActivity().getString(R.string.dialog_text));
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Program> doInBackground(Void... params) {
            return dbHandler.getProgramByDay(day);
        }

        @Override
        protected void onPostExecute(ArrayList<Program> _programs) {
            super.onPostExecute(_programs);
            programHandler = new ProgramHandler(_programs);
            channels = programHandler.getChannels();
            initPager();
            progressDialog.dismiss();
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return channels.length;
        }

        @Override
        public Fragment getItem(int position) {
            ArrayList<Program> programs = programHandler.getProgramFromChannel(channels[position]);
            PageFragment fragment = new PageFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.BUNDLE_PROGRAMS, programs);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return channels[position];
        }


    }
}
