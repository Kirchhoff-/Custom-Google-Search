package com.example.kirchhoff.googletest.Fragments;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kirchhoff.googletest.Adapter.FavoriteItemAdapter;
import com.example.kirchhoff.googletest.DB.DBhelper;
import com.example.kirchhoff.googletest.DB.ImageItem;
import com.example.kirchhoff.googletest.R;

import java.util.ArrayList;

/**
 * Created by Kirchhoff on 16.04.2015.
 */
//Fragment shows List of favorite Image
public class FavoriteFragment extends Fragment {

    final static String TAG = "FavoriteFragment: ";
    ListView lv;
    TextView emptyText;
    ArrayList<ImageItem> mItems = new ArrayList<>();
    private FavoriteItemAdapter adapter;
    int Position = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "FavoriteFragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment, null);
        lv = (ListView) view.findViewById(R.id.favoritelistView);

        //If no record in favorite, show text
        emptyText = (TextView) view.findViewById(android.R.id.empty);
        lv.setEmptyView(emptyText);
        setupAdapter();

        lv.setOnItemClickListener(ScrollClick);
        return view;
    }

    public void setupAdapter() {
        Log.d(TAG, "FromSETUPADAPER");

        if (getActivity() == null || lv == null) return;


        if ((mItems != null) && (DBhelper.getSize() > mItems.size())) {
            mItems.clear();
            mItems = DBhelper.getAllFavorite();
            adapter = new FavoriteItemAdapter(getActivity().getApplicationContext(), mItems);
            lv.setAdapter(adapter);
        } else if ((mItems != null) && (DBhelper.getSize() == mItems.size())) {
            adapter = new FavoriteItemAdapter(getActivity().getApplicationContext(), mItems);
            lv.setAdapter(adapter);
        }else{
            lv.setAdapter(null);
        }
    }

    AdapterView.OnItemClickListener ScrollClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "" + position);

            //Save Position of select element
            Position = position;

            //Start fullScreenActivity
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().
                    beginTransaction();
            transaction.replace(R.id.fragmentContainer,
                    FullScreenFragment.newInstance(mItems.get(position).getUrl()));
            transaction.addToBackStack(null).commit();
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fragment OnDestroy");
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "FavoriteFragment onPause");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "FavoriteFragment onResume");
        //Need to call this after returning from FullScreenFragment
        //Other way don't show favorite list
        setupAdapter();
        int top = (lv.getChildAt(0) == null) ? 0 : lv.getChildAt(0).getTop();
        lv.setSelectionFromTop(Position, top);
    }
}
