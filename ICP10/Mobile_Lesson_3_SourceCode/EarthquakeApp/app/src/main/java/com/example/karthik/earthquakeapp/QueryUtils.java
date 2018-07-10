package com.example.karthik.earthquakeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthik on 11/3/17.
 */

public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData2(String requestUrl) {
        // An empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        //  URL object to store the url for a given string
        URL url = null;
        // A string to store the response obtained from rest call in the form of string
        String jsonResponse = "";
        try {
                //TODO: 1. Create a URL from the requestUrl string and make a GET request to it;
            url = new URL (requestUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
                //TODO: 2. Read from the Url Connection and store it as a string(jsonResponse)
            StringBuilder response = new StringBuilder();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line !=null){
                response.append(line);
                line = reader.readLine();
            }
            jsonResponse = response.toString();
                /*TODO: 3. Parse the jsonResponse string obtained in step 2 above into JSONObject to extract the values of
                        "mag","place","time","url"for every earth quake and create corresponding Earthquake objects with them
                        Add each earthquake object to the list(earthquakes) and return it.
                */
JSONObject json = new JSONObject(jsonResponse);
JSONArray jsonArray = json.getJSONArray("features");
for(int i =0; i < jsonArray.length(); i++){
JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("properties");
double mag = jsonObject.getDouble("mag");
String place = jsonObject.getString("place");
int time = jsonObject.getInt("time");
String url1 = jsonObject.getString("url");
Earthquake earthquake = new Earthquake(mag,place,time,url1);
earthquakes.add(earthquake);
}
// Return the list of earthquakes

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  ", e);
        }
        // Return the list of earthquakes
        return earthquakes;
    }



}
