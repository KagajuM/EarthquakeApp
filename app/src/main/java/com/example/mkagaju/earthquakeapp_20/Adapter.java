package com.example.mkagaju.earthquakeapp_20;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    public List<Earthquake> earthquakeList;
    public int numberOfItems;

    final private ListItemClickListener onItemClickListener;


    public Adapter(List<Earthquake> earthquakes, ListItemClickListener listener)
    {
        this.earthquakeList = earthquakes;
        numberOfItems = earthquakeList.size();
        onItemClickListener = listener;
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

        holder.earthquake_location_tv.setText("Location : " + curr.getLocation());
        holder.earthquake_time_tv.setText("Date : " + curr.getExact_time());
        holder.earthquake_magnitude_tv.setText("Magnitude : " + curr.getMagnitude());
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

        public ViewHolder(View itemView) {
            super(itemView);
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
