package com.example.kirchhoff.googletest.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kirchhoff.googletest.DB.ImageItem;
import com.example.kirchhoff.googletest.R;
import com.example.kirchhoff.googletest.Utils.LoadImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Kirchhoff on 26.04.2015.
 */
//Adapter for lv
public class FavoriteItemAdapter extends ArrayAdapter<ImageItem> {
    final static String TAG = "FavoriteItemAdpter: ";

    public FavoriteItemAdapter(Context context, ArrayList<ImageItem> items){
        super(context, 0 , items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "List item Adapter onGetView");
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.favorite_element, parent, false);

        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.favoriteimagepart);
        TextView textView = (TextView) convertView.findViewById(R.id.favoritetextpart);
        ImageItem item = getItem(position);

       // imageLoader.displayImage(item.getUrl(), imageView);
        LoadImage.downloadImageToView(item.getUrl(),imageView);
        textView.setText(item.getTitle());

        return  convertView;
    }
};