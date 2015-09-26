package be.ryan.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.MovieListType;
import be.ryan.popularmovies.ui.adapter.TmdbPagerAdapter;
import be.ryan.popularmovies.ui.view.SlidingTabLayout;


public class MovieListPagerFragment extends Fragment{

    private static final String TAG = "MovieListPagerFragment";

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    public MovieListPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_list_pager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        final Fragment[] fragments = {
                ListMovieFragment.newInstance(getString(R.string.title_favorites), MovieListType.Favorites),
                ListMovieFragment.newInstance(getString(R.string.title_now_playing) , MovieListType.LATEST),
                ListMovieFragment.newInstance(getString(R.string.title_popular_movies), MovieListType.POPULAR),
                ListMovieFragment.newInstance(getString(R.string.title_highest_rated), MovieListType.TOP),
                ListMovieFragment.newInstance(getString(R.string.title_upcoming), MovieListType.UPCOMING)
        };

        final TmdbPagerAdapter pagerAdapter = new TmdbPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(pagerAdapter);

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);

        return view;
    }

}
