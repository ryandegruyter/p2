package be.ryan.popularmovies.ui.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.List;

import be.ryan.popularmovies.sync.PopMovSyncAdapter;
import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.domain.TmdbMoviesPage;
import be.ryan.popularmovies.tmdb.TmdbService;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.ui.adapter.PopularMoviesAdapter;
import be.ryan.popularmovies.util.Preferences;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieListFragment extends android.support.v4.app.Fragment {

    public static final String PARAM_KEY_TITLE = "title";
    private static final String TAG = "MovieListFragment";
    private RecyclerView mMovieListRecyclerView = null;
    private PopularMoviesAdapter mPopularMoviesAdapter = null;
    private RecyclerView.LayoutManager mPopularMoviesLayoutManager = null;

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
        mMovieListRecyclerView = (RecyclerView) view.findViewById(R.id.popular_movies_recycler_view);
        initRecyclerView(getActivity(),savedInstanceState);
        return view;
    }

    private void setRecyclerViewAdapter(List<TmdbMovie> tmdbMovieList) {
        //TODO: Set Adapter
        mPopularMoviesAdapter = new PopularMoviesAdapter(getActivity(), tmdbMovieList);
        mMovieListRecyclerView.setAdapter(mPopularMoviesAdapter);
    }

    private void initRecyclerView(Context context, Bundle savedInstanceState) {
        mMovieListRecyclerView.setHasFixedSize(true);
        mPopularMoviesLayoutManager = new GridLayoutManager(context,3);
        mMovieListRecyclerView.setLayoutManager(mPopularMoviesLayoutManager);
        if (savedInstanceState != null && savedInstanceState.containsKey("list")) {
            Parcelable list = savedInstanceState.getParcelable("list");
            List<TmdbMovie> movieList = Parcels.unwrap(list);
            setRecyclerViewAdapter(movieList);
        }
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
        if (mPopularMoviesAdapter != null) {
            Parcelable movieList = Parcels.wrap(mPopularMoviesAdapter.getMoviesList());
            outState.putParcelable("list", movieList);
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
