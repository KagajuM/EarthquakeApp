package com.example.mkagaju.earthquakeapp_20;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.res.Resources;
import java.util.List;
import java.util.regex.Pattern;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public Context context;
    public List<Earthquake> earthquakeList;
    public static int numberOfItems;

    final private ListItemClickListener onItemClickListener;


    public Adapter(Context context, List<Earthquake> earthquakes, ListItemClickListener listener)
    {
        this.context = context;
        this.earthquakeList = earthquakes;
        numberOfItems = earthquakeList.size();
        onItemClickListener = listener;
    }

    public List<Earthquake> getEarthquakeList() {
        return earthquakeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.earthquake_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Earthquake curr = earthquakeList.get(position);

        holder.earthquake_distance_tv.setText(getDistanceText(curr.getLocation()));
        holder.earthquake_location_tv.setText(getLocationText(curr.getLocation()));
        holder.earthquake_time_tv.setText(curr.getExact_time());
        holder.earthquake_magnitude_tv.setText(curr.getMagnitude());
        GradientDrawable drawable = (GradientDrawable) holder.earthquake_magnitude_tv.getBackground();
        int magnitudeColor = getMagnitudeColor(curr.getMagnitude());
        drawable.setColor(magnitudeColor);
    }

    private int getMagnitudeColor(String magnitude) {
        Double magnitudeColor = Double.valueOf(magnitude);
        int magnitudeFloor = (int) Math.floor(magnitudeColor);
        int magnitudeColorResourceId;
        switch (magnitudeFloor) {
            case 0:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
       return ContextCompat.getColor(context, magnitudeColorResourceId);

    }

    private String getDistanceText (String text) {
        char firstChar = text.charAt(0);
        boolean isDigit = Character.isDigit(firstChar);
        String retVal;

        if (isDigit) {
            int indexOf_of = text.indexOf("of");
            retVal = text.substring(0, indexOf_of + 2);
        }else {
            retVal = "Near the";
        }

        return retVal;
    }

    private String getLocationText (String text) {
        char firstChar = text.charAt(0);
        boolean isDigit = Character.isDigit(firstChar);
        String retVal;

        if (isDigit) {
            int indexOf_of = text.indexOf("of");
            retVal = text.substring(indexOf_of + 2);
        }else {
            retVal = text;
        }

        return retVal;
    }

    @Override
    public int getItemCount() {
        return numberOfItems;
    }

    /* ViewHolder Inner Class */
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        public TextView earthquake_location_tv;
        public TextView earthquake_time_tv;
        public TextView earthquake_magnitude_tv;
        public TextView earthquake_distance_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            earthquake_distance_tv = (TextView) itemView.findViewById(R.id.earthquake_distance_tv);
            earthquake_location_tv= (TextView) itemView.findViewById(R.id.earthquake_location_tv);
            earthquake_time_tv= (TextView) itemView.findViewById(R.id.earthquake_time_tv);
            earthquake_magnitude_tv= (TextView) itemView.findViewById(R.id.earthquake_magnitude_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Earthquake clickedEarthquake = earthquakeList.get(clickedPosition);
            String urlText = clickedEarthquake.getDetailsWebpage();

            onItemClickListener.onListItemClicked(urlText);
        }


    }
}
