package app.quiz.home.weather.tran.yahooweatherandroidhomequiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import app.quiz.home.weather.tran.yahooweatherandroidhomequiz.customLayout.CustomWeatherAdapter;
import app.quiz.home.weather.tran.yahooweatherandroidhomequiz.model.WeatherObjects;
import app.quiz.home.weather.tran.yahooweatherandroidhomequiz.utility.NetworkConnectionTest;
import app.quiz.home.weather.tran.yahooweatherandroidhomequiz.weatherParser.ParseWeatherAPICall;

public class MainActivity extends AppCompatActivity {

    /**
     * The textview to display if the weather api call is successful or not.
     */
    private TextView aboutWeatherMessage;

    /**
     * a list view to display the weather api results.
     */
    private ListView weatherResults;

    /**
     * a list to hold the weather forecast.
     */
    private List<WeatherObjects> mWeatherObjectsList;

    /**
     * an adapter to display the data to the list view.
     */
    private  CustomWeatherAdapter customWeatherAdapter;

    /**
     * a filler message.
     */
    private static final String INTRO_MESSAGE = "Filler Message";

    /**
     * a notification string for when the weather updates are being fetched.
     */
    private static final String API_CALL_MESSAGE = "Getting your weather forecasts!";

    /**
     * a provided URL from the challenge.
     */
    private static final String API_URL_STRING = "https://query.yahooapis.com/v1/public/yql?q=select%20location,item.condition%20from%20weather.forecast%20where%20woeid%20in%20%28select%20woeid%20from%20geo.places%281%29%20where%20text%20in%20%28%22tokyo%22,%20%22new%20york%22,%20%22sao%20paulo%22,%20%22seoul%22,%20%22mumbai%22,%20%22delhi%22,%20%22jakarta%22,%20%22cairo%22,%20%22los%20angeles%22,%20%22buenos%20aires%22,%20%22moscow%22,%20%22shanghai%22,%20%22paris%22,%20%22istanbul%22,%20%22beijing%22,%20%22london%22,%20%22singapore%22,%20%22hong%20kong%22,%20%22sydney%22,%20%22madrid%22,%20%22rio%22%29%29&format=json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutWeatherMessage = (TextView) findViewById(R.id.aboutWeatherMessage);
        weatherResults = (ListView) findViewById(R.id.resultsView);

        aboutWeatherMessage.setText(INTRO_MESSAGE);
        ActionBar bar = getSupportActionBar();

        mWeatherObjectsList = new ArrayList<WeatherObjects>();

        if(bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setTitle(R.string.homePage_actionbarName);
        }

        updateWeatherForecast();
    }

    /**
     * Create menu options
     * @param menu The item to inflate action bar.
     * @return A modified action bar with item(s).
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_item_choice, menu);
        return true;
    }

    /**
     * Creates a(n) event handler(s) for menu items.
     * @param item The menu item
     * @return true or false depending if an item has been pressed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.refreshWeather) {
            updateWeatherForecast();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * helper method that attempts to update the UI by first checking if there is network connectivity.
     */
    private void updateWeatherForecast() {
        if(checkForNetworkConnection()) {
            makeAPICall();
        }
        else {
            aboutWeatherMessage.setText(R.string.error_message);
            mWeatherObjectsList.clear();
            customWeatherAdapter.notifyDataSetChanged();
        }
    }

    /**
     * helper method to check if there is a network connection available.
     */
    private boolean checkForNetworkConnection() {
        NetworkConnectionTest networkTest = new NetworkConnectionTest(this);
        return networkTest.checkForNetworkConnection();
    }

    /**
     * helper method to invoke the Yahoo Weather API call.
     */
    private void makeAPICall() {
        GetWeatherResults getWeatherResults = new GetWeatherResults(this);
        getWeatherResults.execute();
    }

    /**
     * helper class to make the api call in a background thread.
     */
    private class GetWeatherResults extends AsyncTask<Void, Void, Void> {
        /**
         * current activity context
         */
        private final Context mContext;

        /**
         * a dialog to display to the user for when the yelp search is querying.
         */
        private ProgressDialog mDialog;

        /**
         * @param context The context of the calling activity.
         */
        public GetWeatherResults(Context context) {
            mContext = context;
        }

        /**
         * Creates the dialog for the user while querying for a list of restaurants.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage(API_CALL_MESSAGE);
            mDialog.show();
        }

        /**
         * Makes the Yahoo API Weather call
         * @param params void parameter
         * @return A null object
         */
        @Override
        protected Void doInBackground(Void... params) {

            ParseWeatherAPICall getWeatherUpdates = new ParseWeatherAPICall();
            String unparsedResults = getWeatherUpdates.getWeatherResults(API_URL_STRING);
            getWeatherUpdates.fillWeatherObjectList(unparsedResults);
            mWeatherObjectsList = getWeatherUpdates.getmWeatherObjectList();
            return null;
        }

        /**
         * Creates the view with a suggestion of restaurants for the user.
         * @param aVoid void parameter
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            customWeatherAdapter = new CustomWeatherAdapter(mContext, mWeatherObjectsList);
            weatherResults.setAdapter(customWeatherAdapter);
            aboutWeatherMessage.setText(R.string.success_message);
            super.onPostExecute(aVoid);
            mDialog.dismiss();
        }
    }
}
