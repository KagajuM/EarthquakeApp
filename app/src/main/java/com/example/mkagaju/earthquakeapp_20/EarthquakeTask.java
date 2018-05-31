package com.example.mkagaju.earthquakeapp_20;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class EarthquakeTask extends AsyncTask<String, Void, List<Earthquake>> {

    public interface UpdateUI {
        void disableProgressBar();
        void showConnectionError();
        void populateAdapter(List<Earthquake> list);
        void onClickListener();
        //Todo: Implement On click Listening
    }

    UpdateUI updateUI;

    public EarthquakeTask (UpdateUI updateUI) {
        this.updateUI = updateUI;
    }

//    Adapter adapter;
//    ListItemClickListener listener;
//    ProgressBar bar;
//
//    public EarthquakeTask (Adapter adapter, ListItemClickListener listener, ProgressBar bar) {
//        this.adapter = adapter;
//        this.listener = listener;
//        this.bar = bar;
//    }
//
    @Override
    protected List<Earthquake> doInBackground(String... strings) {
        Networking network = new Networking();
        try {
            Log.e("Earthquake Task", strings[0]);
            return network.getEarthquakesList(strings[0]);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Earthquake> earthquakes) {
        super.onPostExecute(earthquakes);
        updateUI.disableProgressBar();

        if (earthquakes != null && earthquakes.size()>0) {
            updateUI.populateAdapter(earthquakes);
        } else {
            updateUI.showConnectionError();
        }

    }
}
