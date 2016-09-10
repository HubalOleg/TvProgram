package oleg.hubal.com.tvprogram.database;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.database.model.Channel;

/**
 * Created by User on 05.09.2016.
 */
public interface ChannelListener {
    public void addChannels(ArrayList<Channel> channels);
    public ArrayList<Channel> getAllChannels();
    public int getChannelCount();
    public void setFavorite(int id, int isFavorite);
}
