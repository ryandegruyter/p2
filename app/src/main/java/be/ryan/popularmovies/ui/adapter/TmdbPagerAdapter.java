package be.ryan.popularmovies.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import be.ryan.popularmovies.ui.fragment.MovieListFragment;

/**
 * Created by Ryan on 01/09/2015.
 */
public class TmdbPagerAdapter extends FragmentPagerAdapter {

    private final Fragment[] mFragments;

    public TmdbPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        mFragments = fragments;
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
        return mFragments[position].getArguments().getString(MovieListFragment.PARAM_KEY_TITLE);
    }
}
