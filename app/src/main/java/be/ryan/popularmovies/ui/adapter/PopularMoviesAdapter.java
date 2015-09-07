package be.ryan.popularmovies.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;

/**
 * Created by Ryan on 29/08/2015.
 */
public class PopularMoviesAdapter extends android.support.v7.widget.RecyclerView.Adapter<MovieHolder> {

    private final Context mContext;
    private final List<TmdbMovie> mTmdbMoviesList;

    public PopularMoviesAdapter(Context context, List<TmdbMovie> tmdbMovieList) {
        mContext = context;
        mTmdbMoviesList = tmdbMovieList;
    }
    
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View item = inflater.inflate(R.layout.tmdb_movie_grid_list_item, parent, false);
        return new MovieHolder(item);
    }

    @Override
    public void onBindViewHolder(MovieHolder viewHolder, int position) {
        viewHolder.bindData(mTmdbMoviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTmdbMoviesList.size();
    }

    public List<TmdbMovie> getMoviesList() {
        return mTmdbMoviesList;
    }
}
