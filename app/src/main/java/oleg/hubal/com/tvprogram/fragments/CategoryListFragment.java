package oleg.hubal.com.tvprogram.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.Set;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.R;

/**
 * Created by User on 09.09.2016.
 */
public class CategoryListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String[] categories;
    private ListView listView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        view = inflater.inflate(R.layout.fragment_category, container, false);

        getCategories();
        setList();

        return view;
    }

    private void setList() {
        listView = (ListView) view.findViewById(R.id.category_list_FC);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, categories);

        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }

    private void getCategories() {
        SharedPreferences sPref = getActivity().getSharedPreferences(Constants.SHARED_PREF_FILE,
                Context.MODE_PRIVATE);
        Set<String> categSet = sPref.getStringSet(Constants.SHARED_PREF_CATEGORY, null);
        if (categSet != null)
            categories = categSet.toArray(new String[categSet.size()]);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Get clicked category
        TextView clickedView = (TextView) view;
        String categoryArgument = clickedView.getText().toString();

//        Set argument and start fragment
        ChannelListFragment channelListFragment = new ChannelListFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CATEGORY_ARG, categoryArgument);
        channelListFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame_AM, channelListFragment)
                .commit();
    }
}
