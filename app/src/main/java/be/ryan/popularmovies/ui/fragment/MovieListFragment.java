package be.ryan.popularmovies.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.List;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.domain.TmdbMoviesPage;
import be.ryan.popularmovies.sync.PopMovSyncAdapter;
import be.ryan.popularmovies.ui.adapter.MoviesAdapter;
import be.ryan.popularmovies.util.Preferences;
import de.greenrobot.event.EventBus;

public class MovieListFragment extends Fragment {

    public static final String PARAM_KEY_TITLE = "title";
    private static final String TAG = "MovieListFragment";
    private static final String MOVIE_LIST = "movie_list";

    RecyclerView mMovieListRecyclerView = null;
    MoviesAdapter mMoviesAdapter = null;
    RecyclerView.LayoutManager mPopularMoviesLayoutManager = null;

    public static MovieListFragment newInstance(String title) {
        final MovieListFragment movieListFragment = new MovieListFragment();
        final Bundle args = new Bundle();
        args.putString(PARAM_KEY_TITLE, title);
        movieListFragment.setArguments(args);
        return movieListFragment;
    }

    public MovieListFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView : " + getArguments().getString(PARAM_KEY_TITLE));
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        initRecyclerView(getActivity(),view);
        populateRecyclerView(savedInstanceState);
        return view;
    }

    void populateRecyclerView(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_LIST)) {
            Parcelable list = savedInstanceState.getParcelable(MOVIE_LIST);
            List<TmdbMovie> movieList = Parcels.unwrap(list);
            setRecyclerViewAdapter(movieList);
        }else{
            PopMovSyncAdapter.syncImmediately(getActivity(), new Bundle());
        }
    }

    void setRecyclerViewAdapter(List<TmdbMovie> tmdbMovieList) {
        mMoviesAdapter = new MoviesAdapter(getActivity(), tmdbMovieList);
        mMovieListRecyclerView.setAdapter(mMoviesAdapter);
    }

    void initRecyclerView(Context context, View view) {
        mMovieListRecyclerView = (RecyclerView) view.findViewById(R.id.popular_movies_recycler_view);
        mMovieListRecyclerView.setHasFixedSize(true);
        mPopularMoviesLayoutManager = new GridLayoutManager(getActivity(),2);
        mMovieListRecyclerView.setLayoutManager(mPopularMoviesLayoutManager);
    }

    public void onEvent(TmdbMoviesPage moviesPage) {
        String prefSortType = Preferences.getMovieListSortType(getActivity());
        String pageTitle = getArguments().getString(PARAM_KEY_TITLE);
        if (prefSortType.equals(pageTitle)) {
            setRecyclerViewAdapter(moviesPage.getTmdbMovieList());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMoviesAdapter != null) {
            Parcelable movieList = Parcels.wrap(mMoviesAdapter.getMoviesList());
            outState.putParcelable(MOVIE_LIST, movieList);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
