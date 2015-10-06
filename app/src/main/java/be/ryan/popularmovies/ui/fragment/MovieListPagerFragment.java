package be.ryan.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.MovieListType;
import be.ryan.popularmovies.event.PageSelectedEvent;
import be.ryan.popularmovies.ui.adapter.TmdbPagerAdapter;
import be.ryan.popularmovies.ui.view.SlidingTabLayout;
import de.greenrobot.event.EventBus;


public class MovieListPagerFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MovieListPagerFragment";

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    public MovieListPagerFragment() {
        // Required empty public constructor
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
        mSlidingTabLayout.setOnPageChangeListener(this);

//        when the pager is created onpageselected is not called for the first item
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                onPageSelected(mViewPager.getCurrentItem());
            }
        });
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        EventBus.getDefault().post(new PageSelectedEvent(mViewPager.getAdapter().getPageTitle(position)));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
