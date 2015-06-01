package com.example.kirchhoff.googletest.Fragments;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.example.kirchhoff.googletest.Adapter.SearchItemAdapter;
import com.example.kirchhoff.googletest.Adapter.ViewPagerAdapter;
import com.example.kirchhoff.googletest.DB.DBhelper;
import com.example.kirchhoff.googletest.DB.ImageItem;
import com.example.kirchhoff.googletest.R;
import com.example.kirchhoff.googletest.Utils.SampleLoader;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;

/**
 * Created by Kirchhoff on 07.04.2015.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ImageItem>> {
    private static final int THE_LOADER = 0x01;//Id loader


    static final String TAG = "SearchFragment";
    ListView lv;
    TextView emptyText;
    ArrayList<ImageItem> mItems = new ArrayList<ImageItem>();
    private SearchItemAdapter adapter;
    boolean finish = false;//Loader finish download
    boolean LoadMore = false;//Loader need download new items
    LoaderManager.LoaderCallbacks call;
    Bundle args = new Bundle();//Bundle for Loaders
    private int Position = 0;;//Position of chosen element(need for FullScreenFragment)



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SearchFragment onCreate");
        call = this;

        //ActionBar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "On Create VIEW");
        View view = inflater.inflate(R.layout.search_fragment, null);

        lv = (ListView) view.findViewById(R.id.listView);

        //Show user text before search
        emptyText = (TextView)view.findViewById(android.R.id.empty);
        lv.setEmptyView(emptyText);

        setupAdapter();
        lv.setOnScrollListener(ScrollListner);
        lv.setOnItemClickListener(ScrollClick);
        return view;
    }


    void setupAdapter(){
        Log.d(TAG, "FromSETUPADAPER");
        if(getActivity() == null ||lv == null) return;

        if(mItems != null){
            adapter = new SearchItemAdapter(getActivity().getApplicationContext(),mItems);
            lv.setAdapter(adapter);
        }else{
            lv.setAdapter(null);
        }
    }

    //Realization of Loader
    @Override
    public Loader<ArrayList<ImageItem>> onCreateLoader(int id, Bundle args) {

        SampleLoader loader = new SampleLoader(getActivity(),
                args.getInt(SampleLoader.START_INDEX)
                ,args.getString(SampleLoader.SEARCH_STRING));
        finish = false;
        LoadMore = true;
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ImageItem>> loader, ArrayList<ImageItem> data) {
        finish = true;
        //Without this , will add new item to mItems, after returning from FullScreenFragment
        if(LoadMore == true) {
            mItems.addAll(data);
            LoadMore = false;
        }

        adapter.notifyDataSetChanged();
        int index = lv.getFirstVisiblePosition();
        int top = (lv.getChildAt(0) == null) ? 0 : lv.getChildAt(0).getTop();
        lv.setSelectionFromTop(index, top);
    }


    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<ImageItem>> loader) {
        Log.d(TAG, "On Loader Reset");
        lv.setAdapter(null);
    }



//ActionBar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_photo_gallery, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                getActivity().onSearchRequested();
                return true;
            case R.id.menu_item_clear:
                //Can use this in future, for clean search result
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateItems(String query) {
        Log.d(TAG, "UpdateItems");

        //Search String
        args.putString(SampleLoader.SEARCH_STRING, query);
        //Start index of search
        args.putInt(SampleLoader.START_INDEX, 1);

        //clear previous search
        mItems.clear();

        //restart Loader
        getLoaderManager().restartLoader(THE_LOADER,args,call).forceLoad();
        setupAdapter();

    }


    //Listener for ListView
    AbsListView.OnScrollListener ScrollListner = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
            boolean loadMore = firstVisible + visibleCount >= totalCount;

            //If loaders is finish and need download more items
            //restart loader
            if (loadMore && finish) {

                args.putInt(SampleLoader.START_INDEX, totalCount);
                getLoaderManager().restartLoader(THE_LOADER,args,call).forceLoad();
                Log.d(TAG, "SCROLL");
            }
        }

    };

    OnItemClickListener ScrollClick = new OnItemClickListener() {
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
        //Close Database
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "SearchFragment onPause");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "SearchFragment onResume");
        //After returning from FullScreenFragment chosen element will be on top of screen
        int top = (lv.getChildAt(0) == null) ? 0 : lv.getChildAt(0).getTop();
        lv.setSelectionFromTop(Position, top);
    }


}







