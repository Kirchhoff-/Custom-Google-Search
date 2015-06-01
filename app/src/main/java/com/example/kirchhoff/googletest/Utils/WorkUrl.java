package com.example.kirchhoff.googletest.Utils;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import android.util.Log;

import com.example.kirchhoff.googletest.DB.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kirchhoff on 02.04.2015.
 */
//Class for working with Google API
public class WorkUrl {
    public static final String TAG = "WorkURL";
    private static final String GOOGLE = "https://www.googleapis.com/customsearch/v1?";
    private static final String KEY = "key=AIzaSyAgXgvtk08wGpfn6_7YFwnSUuEi9DA_aUg";
    private static final String CX = "&cx=017948032783244066612:lkyr0zbajsy&q=";
    private static final String SEARCHTYPE = "&searchType=image";
    private static final String INDEX = "&start=";


    byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();


        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer))> 0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public String getUrl(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }


    //Get Result of Search
    public ArrayList<ImageItem> fetchItems(String request, int start) {
        ArrayList<ImageItem> items = new ArrayList<ImageItem>();

        Log.d(TAG, " Start index = " + start);
        //Need to decode space to -> %20
        //other way can't do query with two or more words
        request = request.replaceAll(" ","%20");
        Log.d(TAG, " String = " + request);
        try {
            String url = Uri.parse(GOOGLE + KEY + CX + request + SEARCHTYPE + INDEX + start).buildUpon()
                    .build().toString();

            //Get JSON string from query
            String JSONString = getUrl(url);

            JSONObject json = new JSONObject(JSONString);
            //Get array of result
            JSONArray imageArray = json.getJSONArray("items");

            //get information from array
            for(int i=0; i<imageArray.length(); i++)
            {
                ImageItem item = new ImageItem();
                item.setUrl(imageArray.getJSONObject(i).getString("link"));
                item.setTitle(imageArray.getJSONObject(i).getString("title"));
                items.add(item);
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }  catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

}

