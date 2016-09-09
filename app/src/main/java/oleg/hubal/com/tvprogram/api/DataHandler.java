package oleg.hubal.com.tvprogram.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.database.DBHandler;
import oleg.hubal.com.tvprogram.database.model.Channel;

/**
 * Created by User on 08.09.2016.
 */
public class DataHandler {

    private DBHandler dbHandler;
    private final SharedPreferences sPref;

    private Context context;

    public DataHandler(Context context) {
        dbHandler = new DBHandler(context);
        sPref = context.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE);
    }

    //      Save data in database
    public void saveChannels(String resultJson) {
        try {
            JSONObject jObject = new JSONObject(resultJson);
            Iterator<String> keys = jObject.keys();
            ArrayList<Channel> channels = new ArrayList<>();
            Set<String> channelSet = new HashSet<>();

            while (keys.hasNext()) {
                Channel channel = new Channel();

                String key = keys.next();
                JSONObject jsonChannel = jObject.getJSONObject(key);

                channel.setJsonId(jsonChannel.getString("id"));
                channel.setName(jsonChannel.getString("name"));
                channel.setTvURL(jsonChannel.getString("tvURL"));
                channel.setCategory(getCategory(jsonChannel));

                channelSet.add(jsonChannel.getString("name"));
                channels.add(channel);

            }

            saveChannelList(channelSet);
            dbHandler.addChannels(channels);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //      Get and save category list from json
    public void saveCategories(String resultJson) {
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            Iterator<String> keys = jsonObject.keys();
            Set<String> categorySet = new HashSet<>();
            while (keys.hasNext()) {
                categorySet.add(keys.next());
            }
            saveCategoryList(categorySet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //      Get channel category from json channelObject
    private String getCategory(JSONObject jsonChannel) {
        Iterator<String> objectTags = jsonChannel.keys();
        String category = "";
        while(objectTags.hasNext()) {
            category = objectTags.next();
        }
        return category;
    }

    //    Save channel list in sharedPref
    private void saveChannelList(Set<String> channelSet) {
        SharedPreferences.Editor sEditor = sPref.edit();
        sEditor.putStringSet(Constants.SHARED_PREF_CHANNEL, channelSet);
        sEditor.apply();
    }

    //    Save categories list in sharedPref
    private void saveCategoryList(Set<String> categorySet) {
        SharedPreferences.Editor sEditor = sPref.edit();
        sEditor.putStringSet(Constants.SHARED_PREF_CATEGORY, categorySet);
        sEditor.apply();
    }
}
