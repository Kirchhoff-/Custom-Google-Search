package com.example.kirchhoff.googletest.Utils;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.kirchhoff.googletest.DB.ImageItem;

import java.util.ArrayList;

/**
 * Created by Kirchhoff on 08.04.2015.
 */
//Loaders
public class SampleLoader extends AsyncTaskLoader<ArrayList<ImageItem>> {
    final static String TAG = "SampleLoader :";
    final public static String START_INDEX = "Start index";
    final public  static String SEARCH_STRING = "Search string";
    static int Start;
    static String Search;

    public SampleLoader(Context context, int start, String search) {
        super(context);
        Start = start;
        Search = search;
    }

    @Override
    public ArrayList<ImageItem> loadInBackground() {
        Log.d(TAG," START INDEX = " + Start );
        Log.d(TAG," STRING = " + Search );

        return new WorkUrl().fetchItems(Search,Start);
    }

}
