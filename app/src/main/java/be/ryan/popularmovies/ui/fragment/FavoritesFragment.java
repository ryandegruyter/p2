package be.ryan.popularmovies.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.provider.PopularMoviesContract;
import be.ryan.popularmovies.ui.adapter.MoviesCursorAdapter;

/**
 * Created by ryan on 8/09/15.
 */
public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int FAVORITES_LOADER_ID = 1;
    public static final String PARAM_KEY_TITLE = "title";

    RecyclerView recyclerViewFavoritesList;

    public static FavoritesFragment newInstance(String title) {

        Bundle args = new Bundle();

        FavoritesFragment fragment = new FavoritesFragment();
        args.putString(PARAM_KEY_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(FAVORITES_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        recyclerViewFavoritesList = (RecyclerView) view.findViewById(R.id.popular_movies_recycler_view);
        recyclerViewFavoritesList.hasFixedSize();
        recyclerViewFavoritesList.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recyclerViewFavoritesList.setAdapter(new MoviesCursorAdapter(getActivity()));

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.CursorLoader(getContext(), PopularMoviesContract.MovieEntry.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ((MoviesCursorAdapter) recyclerViewFavoritesList.getAdapter()).swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((MoviesCursorAdapter) recyclerViewFavoritesList.getAdapter()).swapCursor(null);
    }
}
