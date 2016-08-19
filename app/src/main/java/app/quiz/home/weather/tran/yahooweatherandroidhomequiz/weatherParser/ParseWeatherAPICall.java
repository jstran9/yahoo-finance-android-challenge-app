package app.quiz.home.weather.tran.yahooweatherandroidhomequiz.weatherParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.quiz.home.weather.tran.yahooweatherandroidhomequiz.model.WeatherObjects;

public class ParseWeatherAPICall {

    private List<WeatherObjects> mWeatherObjectList;

    public ParseWeatherAPICall() {
        mWeatherObjectList = new ArrayList<WeatherObjects>();
    }

    /**
     * @param yahooAPIQueryString The URL to use the Yahoo API
     * @return JSON Object as a string with the weather of 20 hard coded cities.
     */
    public String getWeatherResults(String yahooAPIQueryString) {
        StringBuilder apiCallResults = new StringBuilder();
        URL apiCallURL = null;
        BufferedReader inputResults = null;
        HttpURLConnection openConnection = null;

        String resultantLine;

        try {
            apiCallURL = new URL(yahooAPIQueryString);
            openConnection = (HttpURLConnection) apiCallURL.openConnection();
            openConnection.setDoOutput(true);
            openConnection.connect();
            inputResults = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));
            while ((resultantLine = inputResults.readLine()) != null)
                apiCallResults.append(resultantLine);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                if(inputResults != null) inputResults.close();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return apiCallResults.toString();
    }

    /**
     * @param weatherResults An unparsed JSONObject holding the results of the API call.
     */
    public void fillWeatherObjectList(String weatherResults) {
        JSONObject unparsedResults, query, results = null;
        JSONArray channel = null;

        try {
            unparsedResults = new JSONObject(weatherResults);
            query = unparsedResults.getJSONObject("query");
            results = query.getJSONObject("results");
            channel = results.getJSONArray("channel");
            for (int i = 0; i < channel.length(); i++) {
                JSONObject arrayContent = channel.getJSONObject(i);
                JSONObject item = arrayContent.getJSONObject("item");
                JSONObject condition = item.getJSONObject("condition");
                JSONObject location = arrayContent.getJSONObject("location");
                String formattedDate = condition.getString("date");
                String temperature = condition.getString("temp");
                String weatherDescription = condition.getString("text");
                String cityName = location.getString("city");
                mWeatherObjectList.add(new WeatherObjects(weatherDescription, formattedDate, cityName, temperature));
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Returns the weather object list.
     */
    public List<WeatherObjects> getmWeatherObjectList() {
        return mWeatherObjectList;
    }

}
