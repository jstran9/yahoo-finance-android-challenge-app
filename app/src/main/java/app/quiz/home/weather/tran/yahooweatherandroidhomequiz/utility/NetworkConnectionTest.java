package app.quiz.home.weather.tran.yahooweatherandroidhomequiz.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * class to help test determine if there is network connectivity.
 */
public class NetworkConnectionTest {
    /**
     * The calling activity's context.
     */
    private Context mContext;
    /**
     * @param context The context of the calling activity
     */
    public NetworkConnectionTest(Context context) {mContext = context;}

    /**
     * helper method to verify if there network connectivity is avaiilable.
     */
    public boolean checkForNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
