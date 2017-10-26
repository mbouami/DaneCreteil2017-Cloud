package com.bouami.danecreteil2017_cloud.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Mohammed on 26/09/2017.
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "MyFragmentPagerAdapter";
    private Fragment[] mFragments;
    private String[] mFragmentNames;

    public MyFragmentPagerAdapter(FragmentManager fm, Fragment[] mfragments, String[] mfragmentNames) throws JSONException {
        super(fm);
        mFragments = mfragments;
        mFragmentNames = mfragmentNames;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentNames[position];
    }

    @Override
    public int getItemPosition(Object object) {
// POSITION_NONE makes it possible to reload the PagerAdapter
//        return POSITION_NONE;
//        if (object instanceof EtablissementListFragment || object instanceof AnimateurListFragment
//                || object instanceof PersonnelListFragment) {
        return POSITION_UNCHANGED; // don't force a reload
//        } else {
//            // POSITION_NONE means something like: this fragment is no longer valid
//            // triggering the ViewPager to re-build the instance of this fragment.
//            return POSITION_NONE;
//        }
    }
}