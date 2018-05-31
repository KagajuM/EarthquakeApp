package com.example.mkagaju.earthquakeapp_20;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
implements ListItemClickListener, EarthquakeTask.UpdateUI{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private DividerItemDecoration decorator;
    private TextView connectionLossTv;
    private ProgressBar progressBar;

    private final static String PAST_EARTHQUAKES_URL
            = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

    private EarthquakeTask earthquakeTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;

        //Setup resources
        connectionLossTv = (TextView) findViewById(R.id.connection_loss_tv);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //Setup the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(context);
        decorator = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decorator);
        recyclerView.setVisibility(View.INVISIBLE); //Until data is loaded

        earthquakeTask = new EarthquakeTask(this);
        earthquakeTask.execute(PAST_EARTHQUAKES_URL);

    }


    @Override
    public void onListItemClicked(String urlText) {

        Uri uri = Uri.parse(urlText);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        //To avoid lags
        if (intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
        }
        else {
           Toast.makeText(this, "Missing Link", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void disableProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showConnectionError() {
        connectionLossTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void populateAdapter(List<Earthquake> list) {
        adapter = new Adapter(list, MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClickListener() {

    }
}
