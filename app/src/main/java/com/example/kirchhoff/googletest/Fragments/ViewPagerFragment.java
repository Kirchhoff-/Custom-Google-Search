package com.example.kirchhoff.googletest.Fragments;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kirchhoff.googletest.Adapter.ViewPagerAdapter;
import com.example.kirchhoff.googletest.MainActivity;
import com.example.kirchhoff.googletest.R;

/**
 * Created by Kirchhoff on 18.04.2015.
 */

public class ViewPagerFragment extends Fragment {
    static final String TAG = "ViewPager: ";
    ViewPager pager;
    PagerAdapter pagerAdapter;
    View viewHierarchy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SearchFragment onCreate");
}

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        viewHierarchy = inflater.inflate(R.layout.view_pager, container,
                false);

        pager = (ViewPager)viewHierarchy.findViewById(R.id.pager);

        //Need to getChildFragmentManager for correct work of SearchFragment
        //after returning from FullScrenFrament
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d(TAG, "onPageSelected, position = " + i);
                Fragment fragment = ((ViewPagerAdapter)pager.getAdapter()).getFragment(i);
                if (i == 1 && fragment != null) {
                    fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return viewHierarchy;
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "ViewPagerFragment onPause");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "ViewPagerFragment onResume");
    }

}
