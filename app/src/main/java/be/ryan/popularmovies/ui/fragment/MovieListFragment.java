package be.ryan.popularmovies.ui.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.ryan.popularmovies.sync.PopMovSyncAdapter;
import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.domain.TmdbMoviesPage;
import be.ryan.popularmovies.tmdb.TmdbService;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.ui.adapter.PopularMoviesAdapter;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieListFragment extends android.support.v4.app.Fragment {

    public static final String PARAM_KEY_TITLE = "title";
    private RecyclerView mMovieListRecyclerView = null;
    private RecyclerView.Adapter mPopularMoviesAdapter = null;
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
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mMovieListRecyclerView = (RecyclerView) view.findViewById(R.id.popular_movies_recycler_view);

        initRecyclerView(getActivity());
        return view;
    }

    private void setRecyclerViewAdapter(List<TmdbMovie> tmdbMovieList) {
        //TODO: Set Adapter
        mPopularMoviesAdapter = new PopularMoviesAdapter(getActivity(), tmdbMovieList);
        mMovieListRecyclerView.setAdapter(mPopularMoviesAdapter);
    }

    private void initRecyclerView(Context context) {
        mMovieListRecyclerView.setHasFixedSize(true);
        mPopularMoviesLayoutManager = new GridLayoutManager(context,3);
        mMovieListRecyclerView.setLayoutManager(mPopularMoviesLayoutManager);
    }
}
