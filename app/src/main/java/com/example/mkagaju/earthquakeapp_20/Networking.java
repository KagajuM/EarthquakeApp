package com.example.mkagaju.earthquakeapp_20;


import java.text.DecimalFormat;
import android.net.Uri;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Networking {
    //Tags of Relevant Information
    final String FEATURES = "features";
    final String PROPERTIES = "properties";
    final String LOCATION = "place";
    final String EXACT_TIME = "time";
    final String DETAILS = "url";
    final String MAGNITUDE = "mag";

    final static String TAG = "JSON Debugging";
    //Parsing the URl
    public URL buildUrl (String urlText)
    {
        Uri uri = Uri.parse(urlText);
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //Method to return a list of earthquake objects
    public List<Earthquake> getEarthquakesList (String urlText) throws IOException
    {
        URL url = buildUrl(urlText);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = connection.getInputStream();
        String charsetName = "UTF-8";
        JsonReader jsonReader = new JsonReader(new InputStreamReader(in, charsetName));

        try{
//            long startTime = System.nanoTime();
            List<Earthquake> list = readEarthquakeList(jsonReader);
//            long endTime = System.nanoTime();
//            long totalTime = (endTime - startTime)/1000000;
//            Log.e("Json Parsing Runtime", String.valueOf(totalTime)+"ms");
            return list;
        } finally{
            jsonReader.close();
        }
    }

    public List<Earthquake> readEarthquakeList (JsonReader reader) throws IOException {
        List<Earthquake> earthquakes = new ArrayList<Earthquake>();

        //Get Json array of features (Size  = 12949)
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(FEATURES)) {
                //Get Json Object of properties
                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String name1 = reader.nextName();
                        if (name1.equals(PROPERTIES)) {
                            //Add the earthquake object containing relevant details
                            earthquakes.add(getEarthquake(reader));
                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                    //reader.skipValue();
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return earthquakes;
    }

    public Earthquake getEarthquake (JsonReader reader) throws IOException {
        String location = null;
        Long exact_time = null;
        String mag = "n/a";
        String date = null;
        String detailsUrl = null;

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if (name.equals(MAGNITUDE) && reader.peek() != JsonToken.NULL) {
                DecimalFormat df = new DecimalFormat("0.0");
                mag = df.format(reader.nextDouble());
            } else if (name.equals(LOCATION)) {
                location = reader.nextString();
            } else if (name.equals(EXACT_TIME)) {
                exact_time = reader.nextLong();
                Date dateObject = new Date(exact_time);
                SimpleDateFormat simpleDate = new SimpleDateFormat("EEE, MMM dd");
                date = simpleDate.format(dateObject);
            } else if (name.equals(DETAILS)) {
                detailsUrl = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Earthquake retVal = new Earthquake(location, date, mag, detailsUrl);

        return retVal;
    }

}
