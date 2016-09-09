package oleg.hubal.com.tvprogram.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.MainActivity;
import oleg.hubal.com.tvprogram.R;

/**
 * Created by User on 05.09.2016.
 */
public class StaticInfoRequest extends AsyncTask<String, Integer, Void> {

    private ProgressDialog progressDialog;
    private MainActivity activity;
    private DataHandler dataHandler;

    private HttpURLConnection urlConnection = null;
    private BufferedReader reader           = null;
    private String channelJson              = "";
    private String categoryJson             = "";

    public StaticInfoRequest(MainActivity activity) {
        this.activity = activity;
        dataHandler = new DataHandler(activity);
    }

    @Override
    protected Void doInBackground(String... params) {
//        Download JSON channels
        channelJson = getJsonResult(Constants.CHANNEL_JSON);
        dataHandler.saveChannels(channelJson);

//        Download JSON categories
        categoryJson = getJsonResult(Constants.CATEGORY_JSON);
        dataHandler.saveCategories(categoryJson);
        dataHandler.setDataDownloaded();

        return null;
    }

    private String getJsonResult(String jsonURL) {
        try {
            URL url = new URL(Constants.URL + jsonURL);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(Constants.GET);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer channelBuffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                channelBuffer.append(line);
            }
            String resultJson = channelBuffer.toString();
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(activity,
                activity.getString(R.string.dialog_title),
                activity.getString(R.string.dialog_text));
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        activity.setProgramData();
        progressDialog.dismiss();
    }
}
