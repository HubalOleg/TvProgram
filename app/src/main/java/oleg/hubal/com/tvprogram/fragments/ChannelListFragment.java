package oleg.hubal.com.tvprogram.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.R;
import oleg.hubal.com.tvprogram.database.DBHandler;
import oleg.hubal.com.tvprogram.database.model.Channel;
import oleg.hubal.com.tvprogram.ChannelManager;
import oleg.hubal.com.tvprogram.list.ChannelAdapter;

/**
 * Created by User on 09.09.2016.
 */
public class ChannelListFragment extends Fragment implements ChannelManager {

    private RecyclerView channelRecyclerView;
    private RecyclerView.Adapter channelAdapter;
    private ArrayList<Channel> channels;
    private DBHandler dbHandler;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_channel_list, container, false);

        getData();
        setRecyclerView();

        return view;
    }

    private void getData() {
        dbHandler = new DBHandler(getActivity());
        channels = dbHandler.getAllChannels();

        filterData();
    }

    private void filterData() {
        Bundle data = getArguments();
        if (data != null) {
            boolean filterFavorite = data.getBoolean(Constants.BUNDLE_ONLY_FAVORITE, false);
//  Check arguments and filter data if need
            if (filterFavorite) {
                ArrayList<Channel> favoriteChannels = new ArrayList<>();
                for (Channel channel : channels) {
                    if (channel.getIsFavorite() == 1) {
                        favoriteChannels.add(channel);
                    }
                }
                channels = favoriteChannels;
            } else {
                String category = data.getString(Constants.BUNDLE_CATEGORY_ARG, null);
                if (category != null) {
                    ArrayList<Channel> categoryChannels = new ArrayList<>();
                    for (Channel channel : channels) {
                        if (category.equals(channel.getCategory())) {
                            categoryChannels.add(channel);
                        }
                    }

                    channels = categoryChannels;
                }
            }
        }
    }

    private void setRecyclerView() {
        channelRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_FCL);
        channelRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        channelRecyclerView.setLayoutManager(layoutManager);

        channelAdapter = new ChannelAdapter(channels, this);
        channelRecyclerView.setAdapter(channelAdapter);

    }

    @Override
    public void setFavorite(int id, int isFavorite) {
        dbHandler.setFavoriteChannel(id, isFavorite);
    }
}
