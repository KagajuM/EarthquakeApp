package com.example.mkagaju.earthquakeapp_20;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
implements ListItemClickListener, EarthquakeTask.UpdateUI{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private DividerItemDecoration decorator;
    private TextView connectionLossTv;
    private ProgressBar progressBar;
    private WebView webView;

    private String PAST_EARTHQUAKES_URL
            = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=2";

    private EarthquakeTask earthquakeTask;

    private SimpleIdlingResource mIdlingResource;


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
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    public void setPAST_EARTHQUAKES_URL(String url){
        PAST_EARTHQUAKES_URL = url;
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
    public RecyclerView getRecyclerView () {
        return this.recyclerView;
    }

    @Override
    public void onListItemClicked(String urlText) {
        Intent detailsIntent = new Intent(this, WebViewActivity.class);
        detailsIntent.putExtra("URL", urlText);
        this.startActivity(detailsIntent);
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
        adapter = new Adapter(getBaseContext(),list, MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

    }

}
