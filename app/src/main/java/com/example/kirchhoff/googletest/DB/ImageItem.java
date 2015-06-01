package com.example.kirchhoff.googletest.DB;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirchhoff on 07.04.2015.
 */
//Describe SearchResult
public class ImageItem {

    private String URL;
    private String Title;
    private boolean Favorite;//need for correct work of ListView

    public ImageItem(String url_, String title_){
        URL = url_;
        Title = title_;
        Favorite = false;
    }

    public ImageItem(){
        URL = null;
        Title = null;
        Favorite = false;
    }



    public String getUrl() { return URL;}

    public String getTitle() { return Title; }

    public void setTitle(String title) { Title = title; }

    public void setUrl(String url) { URL = url; }

    public void setFavorite(boolean favorite){ Favorite = favorite; }

    public boolean getFavorite() { return Favorite; }


}
