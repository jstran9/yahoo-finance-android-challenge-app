package app.quiz.home.weather.tran.yahooweatherandroidhomequiz.model;

/**
 * This class holds the information about the weather
 */
public class WeatherObjects {

    private String description; // cloudy, sunny, etc.
    private String formattedDate;
    private String cityName;
    private String temperature;

    public WeatherObjects(String description, String formattedDate, String cityName, String temperature) {
        this.description = description;
        this.formattedDate = formattedDate;
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getTemperature() {
        return temperature;
    }
}
