package com.example.kirchhoff.googletest.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirchhoff.googletest.DB.DBhelper;
import com.example.kirchhoff.googletest.DB.ImageItem;
import com.example.kirchhoff.googletest.R;
import com.example.kirchhoff.googletest.Utils.LoadImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Kirchhoff on 26.04.2015.
 */
public class SearchItemAdapter extends ArrayAdapter<ImageItem> {
    final static String TAG = "SearchItemAdapter: ";
    ArrayList<ImageItem> mItems;
    ImageView imageView;
    TextView textView;
    CheckBox AddToFavorite;
    ImageItem fItem;

    public SearchItemAdapter(Context context, ArrayList<ImageItem> items) {
        super(context, 0, items);
        mItems = items;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "List item Adapter onGetView");
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.search_element, parent, false);
        }

        final ProgressBar spinner = (ProgressBar) convertView.findViewById(R.id.progressImageBar);
        imageView = (ImageView) convertView.findViewById(R.id.imagepart);
        textView = (TextView) convertView.findViewById(R.id.textpart);
        AddToFavorite = (CheckBox) convertView.findViewById(R.id.checkBox);

        // imageView.setImageResource(R.drawable.loading);
        ImageItem item = mItems.get(position);
        fItem = mItems.get(position);
        textView.setText(item.getTitle());

       //Show image
        LoadImage.downloadImageToView(item.getUrl(), imageView, spinner);


        AddToFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {

                    if (mItems.get(position).getFavorite() == true) {
                        return;
                    } else {
                        mItems.get(position).setFavorite(true);
                        Log.d(TAG, "Log from CheckElement");
                        DBhelper.insertFavorite(fItem);

                        Toast toast = Toast.makeText(getContext(),
                                "Add to favorite",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        AddToFavorite.setEnabled(false);
                    }
                } else {
                    Log.d(TAG, "LOg from ELse");
                }
            }
        });

        if (item.getFavorite() == true) {
            AddToFavorite.setChecked(true);
            AddToFavorite.setEnabled(false);
        } else {
            AddToFavorite.setChecked(false);
        }

        return convertView;
    }

};