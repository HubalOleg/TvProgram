package oleg.hubal.com.tvprogram.list;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import oleg.hubal.com.tvprogram.ChannelManager;
import oleg.hubal.com.tvprogram.R;
import oleg.hubal.com.tvprogram.database.model.Channel;

/**
 * Created by User on 09.09.2016.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    private List<Channel> channels;
    private ChannelManager iChannelManager;


    public ChannelAdapter(List<Channel> channels, ChannelManager iChannelManager) {
        this.channels = channels;
        this.iChannelManager = iChannelManager;
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_card, parent,
                false);
        ChannelViewHolder channelViewHolder = new ChannelViewHolder(v, iChannelManager);
        return channelViewHolder;
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        holder.channelName.setText(channels.get(position).getName());
        holder.channelCategory.setText(channels.get(position).getCategory());
        holder.channelURL.setText(channels.get(position).getTvURL());
        holder.isFavorite = channels.get(position).getIsFavorite();
        holder.channelId = channels.get(position).getId();

        if(holder.isFavorite == 0)
            holder.ibFavorite.setImageDrawable(Resources.getSystem().getDrawable(android.R.drawable.star_off));
        else
            holder.ibFavorite.setImageDrawable(Resources.getSystem().getDrawable(android.R.drawable.star_on));
    }
}
