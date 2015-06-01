package com.example.kirchhoff.googletest.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.kirchhoff.googletest.R;
import com.example.kirchhoff.googletest.Utils.LoadImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Kirchhoff on 07.04.2015.
 */
public class FullScreenFragment extends Fragment {

    static final String TAG = "FullScreenFragment : ";
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    PhotoViewAttacher mAttacher;//Class for Picture Zoom
    View viewHierarchy;

    //Getting url of Image
    static FullScreenFragment newInstance(String URL) {
        FullScreenFragment FLFragment = new FullScreenFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PAGE_NUMBER, URL);
        FLFragment.setArguments(arguments);
        return FLFragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        viewHierarchy = inflater.inflate(R.layout.full_screen_fragment, container,
                false);


       getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String image = getArguments().getString(ARGUMENT_PAGE_NUMBER);
        ImageView imageView = (ImageView) viewHierarchy.findViewById(R.id.full_image_view);

        //Show image
        LoadImage.downloadImageToView(image,imageView);

        //Enable zoom in Picture(use uk.co.senab.photoview.PhotoViewAttacher)
        mAttacher = new PhotoViewAttacher(imageView);

        return viewHierarchy;
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "FullScreenFragment onPause");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "FullScreenFragment onDestroy");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "FullScreenFragment onResume");
    }

}
