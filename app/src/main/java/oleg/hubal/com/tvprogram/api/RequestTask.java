package oleg.hubal.com.tvprogram.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.R;
import oleg.hubal.com.tvprogram.database.model.Channel;

/**
 * Created by User on 05.09.2016.
 */
public class RequestTask extends AsyncTask<String, Integer, Void> {

    private ProgressDialog progressDialog;
    private Context context;
    private String jsonName;

    private HttpURLConnection urlConnection = null;
    private BufferedReader reader           = null;
    private String resultJson               = "";

    public RequestTask(Context context, String jsonName) {
        this.context = context;
        this.jsonName = jsonName;
    }

    @Override
    protected Void doInBackground(String... params) {
//        Download JSON
        try {
            URL url = new URL(Constants.URL + jsonName);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(Constants.GET);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultJson = buffer.toString();
            saveData(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//      Save data in database
    private void saveData(String resultJson) {
        try {
            JSONObject jObject = new JSONObject(resultJson);
            Iterator<String> keys = jObject.keys();

            while (keys.hasNext()) {
                Channel channel = new Channel();

                String key = keys.next();
                JSONObject jsonChannel = jObject.getJSONObject(key);

                channel.setJsonId(jsonChannel.getString("id"));
                channel.setName(jsonChannel.getString("name"));
                channel.setTvURL(jsonChannel.getString("tvURL"));
                channel.setCategory(getCategory(jsonChannel));
                channel.setIsFavorite(0);
                Log.d("log123", channel.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//      Get channel category from
    private String getCategory(JSONObject jsonChannel) {
        Iterator<String> objectTags = jsonChannel.keys();
        String category = "";
        while(objectTags.hasNext()) {
            category = objectTags.next();
        }
        return category;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context,
                context.getString(R.string.dialog_title),
                context.getString(R.string.dialog_text));
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);

        progressDialog.dismiss();
    }
}
