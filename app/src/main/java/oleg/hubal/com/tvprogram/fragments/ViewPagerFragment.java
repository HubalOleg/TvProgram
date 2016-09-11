package oleg.hubal.com.tvprogram.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Set;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.R;

/**
 * Created by User on 09.09.2016.
 */
public class ViewPagerFragment extends Fragment {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private View view;
    private String[] channels;
    private SharedPreferences sPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        getChannelList();
        setSpinner();
        initPager();
        return view;
    }

    private void setSpinner() {
        sPref = getActivity().getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE);

    }

    private void initPager() {
        pager = (ViewPager) view.findViewById(R.id.vp_pager_FP);
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    public void getChannelList() {
        SharedPreferences sPref = getActivity().getSharedPreferences(Constants.SHARED_PREF_FILE,
                Context.MODE_PRIVATE);
        Set<String> categSet = sPref.getStringSet(Constants.SHARED_PREF_CHANNEL, null);
        if (categSet != null)
            channels = categSet.toArray(new String[categSet.size()]);
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
            return PageFragment.newInstance(position, channels[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return channels[position];
        }
    }
}
