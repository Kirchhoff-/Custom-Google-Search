package com.example.kirchhoff.googletest;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;


import com.example.kirchhoff.googletest.Adapter.ViewPagerAdapter;
import com.example.kirchhoff.googletest.DB.DBhelper;
import com.example.kirchhoff.googletest.Fragments.FavoriteFragment;
import com.example.kirchhoff.googletest.Fragments.SearchFragment;
import com.example.kirchhoff.googletest.Fragments.ViewPagerFragment;
import com.example.kirchhoff.googletest.Utils.LoadImage;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;


public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        //Init support classes
        LoadImage.initLoader(getApplicationContext());
        DBhelper.open(getApplicationContext());

        //Program start from ViewPagerFragment
        fTrans = getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new ViewPagerFragment());

        fTrans.commit();

    }

    //Get new search query
    @Override
    public void onNewIntent(Intent intent) {

        String query = null;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            Log.i(TAG, "Received a new search query: " + query);
        }

        ViewPager mViewPager = (ViewPager)findViewById(R.id.pager);
        ViewPagerAdapter viewPagerAdapter = (ViewPagerAdapter) mViewPager.getAdapter();
        SearchFragment fragment = (SearchFragment)viewPagerAdapter.getFragment(0);

        fragment.updateItems(query);

    }

    @Override
    protected void onDestroy() {
        DBhelper.close();
    }
}
