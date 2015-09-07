package be.ryan.popularmovies.ui.fragment;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.ui.adapter.TmdbPagerAdapter;
import be.ryan.popularmovies.ui.view.SlidingTabLayout;
import be.ryan.popularmovies.util.Preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListPagerFragment extends android.support.v4.app.Fragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MovieListPagerFragment";

    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;

    public MovieListPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_movie_list_pager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        initPager(mViewPager);

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        initTabLayout(mSlidingTabLayout, mViewPager);

        return view;
    }

    private void initTabLayout(SlidingTabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setDistributeEvenly(true);
        tabLayout.setViewPager(viewPager);
    }

    private void initPager(ViewPager pager) {
        final android.support.v4.app.Fragment[] fragments = {
                MovieListFragment.newInstance(getString(R.string.title_now_playing)),
                MovieListFragment.newInstance(getString(R.string.title_popular_movies)),
                MovieListFragment.newInstance(getString(R.string.title_highest_rated)),
                MovieListFragment.newInstance(getString(R.string.title_upcoming))};

        final TmdbPagerAdapter pagerAdapter = new TmdbPagerAdapter(getChildFragmentManager(), fragments);
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(this);

        //the viewpager doesnt call onpageselected when created so we have to force it.
        //on page selected will set the sort type preference wich the syncadapter can read from
        ViewPager.OnPageChangeListener onPageChangeListener = this;
        onPageChangeListener.onPageSelected(0);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /*
    * When a tab is selected the syncadapter should request a list of movies based on the tab title
    *
    * */
    @Override
    public void onPageSelected(int position) {
        //set movie list sort type in prefs
        Preferences.setMovieListSortType((String) mViewPager.getAdapter().getPageTitle(position), getActivity());
        Log.d(TAG, "onPageSelected " + Preferences.getMovieListSortType(getActivity()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
