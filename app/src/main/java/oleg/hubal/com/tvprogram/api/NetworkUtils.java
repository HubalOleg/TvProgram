package oleg.hubal.com.tvprogram.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by User on 06.09.2016.
 */
public class NetworkUtils {

    private Context context;

    public NetworkUtils(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null)
                if(info.getState() == NetworkInfo.State.CONNECTED)
                    return true;

        }
        return false;
    }
}
