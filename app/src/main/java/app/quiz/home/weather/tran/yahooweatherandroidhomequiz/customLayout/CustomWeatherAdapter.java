package app.quiz.home.weather.tran.yahooweatherandroidhomequiz.customLayout;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.quiz.home.weather.tran.yahooweatherandroidhomequiz.R;
import app.quiz.home.weather.tran.yahooweatherandroidhomequiz.model.WeatherObjects;

public class CustomWeatherAdapter extends BaseAdapter {

    private List<WeatherObjects> mWeatherObjectsList;

    private Context mContext;

    public CustomWeatherAdapter(Context context, List<WeatherObjects> weatherObjectsList) {
        mContext = context;
        mWeatherObjectsList = weatherObjectsList;
    }

    @Override
    public int getCount() {
        return mWeatherObjectsList.size();
    }

    @Override
    public Object getItem(int index) {
        return mWeatherObjectsList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return 0; // not used so just return 0.
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.weather_item, null);
            holder = new ViewHolder();
            holder.descriptionText = (TextView) view.findViewById(R.id.descriptionText);
            holder.temperature = (TextView) view.findViewById(R.id.temperatureText);
            holder.formattedDate = (TextView) view.findViewById(R.id.formattedDate);
            holder.cityName = (TextView) view.findViewById(R.id.cityName);
            holder.descriptionImage = (ImageView) view.findViewById(R.id.descriptionImage);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        WeatherObjects weatherObject = (WeatherObjects) getItem(index);

        holder.descriptionText.setText(String.format("%s", weatherObject.getDescription()));
        holder.temperature.setText(String.format("%s", weatherObject.getTemperature()));
        holder.formattedDate.setText(String.format("%s", weatherObject.getFormattedDate()));
        holder.cityName.setText(String.format("%s", weatherObject.getCityName()));

        displayImageFromDescription(holder.descriptionImage, weatherObject.getDescription());

        return view;
    }

    private static class ViewHolder {
        /**
         * view to hold the description.
         */
        TextView descriptionText;
        /**
         * view to display the temperature.
         */
        TextView temperature;
        /**
         * view to display the full date with the time.
         */
        TextView formattedDate;
        /**
         * view to hold the name of the city.
         */
        TextView cityName;
        /**
         * a view to display the image of the description.
         */
        ImageView descriptionImage;
    }

    /**
     * helper method to displayed image.
     * @param imageView The view that will be modified
     * @param imageDescription The description of the image which will be used to determine what image is displayed
     */
    private void displayImageFromDescription(ImageView imageView, String imageDescription) {
        Resources resources = mContext.getResources();
        if(imageDescription.equals("Mostly Cloudy")) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_mostly_cloudy));
        }
        else if(imageDescription.equals("Mostly Clear")) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_mostly_clear));
        }
        else if(imageDescription.equals("Cloudy")) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_cloudy));
        }
        else if(imageDescription.equals("Mostly Sunny")) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_mostly_sunny));
        }
        else if(imageDescription.equals("Clear")) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_clear));
        }
        else if(imageDescription.equals("Sunny")) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_sunny));
        }
        else if(imageDescription.equals("Partly Cloudy")) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_partly_cloudy));
        }


    }
}
