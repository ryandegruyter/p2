package be.ryan.popularmovies.ui.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.ListType;
import be.ryan.popularmovies.provider.PopularMoviesContract;
import be.ryan.popularmovies.sync.PopMovSyncAdapter;
import be.ryan.popularmovies.ui.adapter.MoviesCursorAdapter;
import be.ryan.popularmovies.util.PrefUtil;

/**
 * Created by ryan on 22/09/15.
 *
 * Shows a list of movies, the title matches the ordertype(popular/latest/upcoming/toprated)
 */
public class ListMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String KEY_TITLE = "title";
    private static final String KEY_ORDER_TYPE = "order_type";

    private RecyclerView mMovieListRecyclerView;
    private GridLayoutManager mPopularMoviesLayoutManager;

    public static ListMovieFragment newInstance(String title, String listType) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        // TODO: 23/09/15 force type safety for listtype
        args.putString(KEY_ORDER_TYPE, listType);
        ListMovieFragment fragment = new ListMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        String orderType = getArguments().getString(KEY_ORDER_TYPE);
        if (PrefUtil.isFirstRun(getContext(), orderType)) {
            Bundle syncInfo = new Bundle();
            syncInfo.putInt(PopMovSyncAdapter.SYNC_TYPE, PopMovSyncAdapter.SYNC_MOVIE_LIST);
            syncInfo.putString(PopMovSyncAdapter.KEY_LIST_PATH_NAME, orderType);
            PopMovSyncAdapter.syncImmediately(getContext(), syncInfo);
            PrefUtil.setFirstRunFinished(getContext(), orderType);
        }

        int id = 0;
        switch (getArguments().getString(KEY_ORDER_TYPE)) {
            case ListType.TOP:
                id = 1;
                break;
            case ListType.POPULAR:
                id = 2;
                break;
            case ListType.LATEST:
                id = 3;
                break;
            case ListType.UPCOMING:
                id = 4;
                break;
            case ListType.Favorites:
                id = 5;
                break;
        }
        getLoaderManager().initLoader(id, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mMovieListRecyclerView = (RecyclerView) view.findViewById(R.id.popular_movies_recycler_view);
        mMovieListRecyclerView.setHasFixedSize(true);
        mPopularMoviesLayoutManager = new GridLayoutManager(getActivity(),2);
        mMovieListRecyclerView.setLayoutManager(mPopularMoviesLayoutManager);
        mMovieListRecyclerView.setAdapter(new MoviesCursorAdapter(getContext()));
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final Uri qryUri = PopularMoviesContract.MovieEntry.buildMovieListUri(getArguments().getString(KEY_ORDER_TYPE));
        return new CursorLoader(getContext(), qryUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((MoviesCursorAdapter) mMovieListRecyclerView.getAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((MoviesCursorAdapter) mMovieListRecyclerView.getAdapter()).swapCursor(null);
    }
}
