package com.example.kirchhoff.googletest.Adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.example.kirchhoff.googletest.Fragments.FavoriteFragment;
import com.example.kirchhoff.googletest.Fragments.SearchFragment;
import com.example.kirchhoff.googletest.MainActivity;
import com.example.kirchhoff.googletest.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Created by Kirchhoff on 26.04.2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    static final int PAGE_COUNT = 2;
    private Fragment mCurrentFragment;
    private Map<Integer, String> mFragmentTags;
    private FragmentManager fragmentManager;


    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
        fragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public Fragment getItem(int i) {
        if(i % 2 == 0) {
           return new SearchFragment();
        }else{

            return new FavoriteFragment();}
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //Title of pages
    @Override
    public CharSequence getPageTitle(int position){
        if(position == 0)
            return "Search";
        else
            return "Favorites";

    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        Object obj = super.instantiateItem(container,position);
        if(obj instanceof Fragment){
            //record the frament TAG here
            Fragment f = (Fragment)obj;
            String tag = f.getTag();
            mFragmentTags.put(position,tag);
        }
        return obj;
    }

    public Fragment getFragment(int position){
        String tag = mFragmentTags.get(position);
        if(tag == null)
            return null;
        return fragmentManager.findFragmentByTag(tag);
    }



}
