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
import android.view.ViewStub;
import android.widget.ProgressBar;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.provider.PopularMoviesContract;
import be.ryan.popularmovies.ui.adapter.MoviesCursorAdapter;
import be.ryan.popularmovies.util.Compatibility;
import be.ryan.popularmovies.util.Sync;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ryan on 22/09/15.
 * <p/>
 * Shows a list of movies, the title matches the ordertype(popular/latest/upcoming/toprated)
 */
public class ListMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String KEY_TITLE = "title";
    private static final String KEY_MOVIE_LIST_ORDER_TYPE = "order_type";
    private static final String KEY_ENABLE_PROGRESS_BAR = "has_progress_bar";

    private GridLayoutManager mPopularMoviesLayoutManager;

    private int spanCount = 2;

    @Bind(R.id.popular_movies_recycler_view)
    RecyclerView mMovieListRecyclerView;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    public static ListMovieFragment newInstance(String title, String listType) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MOVIE_LIST_ORDER_TYPE, listType);
        args.putBoolean(KEY_ENABLE_PROGRESS_BAR, true);
        ListMovieFragment fragment = new ListMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListMovieFragment newInstanceWithNoProgressBar(String title, String listType) {
        ListMovieFragment listMovieFragment = newInstance(title, listType);
        listMovieFragment.getArguments().putBoolean(KEY_ENABLE_PROGRESS_BAR, false);
        return listMovieFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(1, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void setSpanCount() {
        if (Compatibility.isLandscape(getContext())) {
            spanCount = 3;
        }else if (Compatibility.isPortait(getContext()) && Compatibility.isTablet(getContext())) {
            spanCount = 4;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);

        if (getArguments().getBoolean(KEY_ENABLE_PROGRESS_BAR)) {
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }

        setSpanCount();
        mMovieListRecyclerView.setHasFixedSize(true);
        mPopularMoviesLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mMovieListRecyclerView.setLayoutManager(mPopularMoviesLayoutManager);
        mMovieListRecyclerView.setAdapter(new MoviesCursorAdapter(getContext()));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        mMovieListRecyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final Uri qryUri = PopularMoviesContract.MovieEntry.buildMovieListUri(getArguments().getString(KEY_MOVIE_LIST_ORDER_TYPE));
        return new CursorLoader(getContext(), qryUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            String orderType = getArguments().getString(KEY_MOVIE_LIST_ORDER_TYPE);
            Sync.syncMovieList(orderType, getContext());
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
        ((MoviesCursorAdapter) mMovieListRecyclerView.getAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((MoviesCursorAdapter) mMovieListRecyclerView.getAdapter()).swapCursor(null);
    }
}