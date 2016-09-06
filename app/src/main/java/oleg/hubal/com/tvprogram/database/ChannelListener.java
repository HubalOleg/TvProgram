package oleg.hubal.com.tvprogram.database;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.database.model.Channel;

/**
 * Created by User on 05.09.2016.
 */
public interface ChannelListener {
    public void addChannel(Channel chanel);
    public ArrayList<Channel> getAllChannel();
    public int getChannelCount();
}
