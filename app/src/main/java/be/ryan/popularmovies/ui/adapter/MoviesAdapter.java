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
public class MoviesAdapter extends android.support.v7.widget.RecyclerView.Adapter<MovieHolder> {

    private final Context mContext;
    private final List<TmdbMovie> mTmdbMoviesList;

    public MoviesAdapter(Context context, List<TmdbMovie> tmdbMovieList) {
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
        // TODO: 04.10.15 check if movie is a favorite, set button
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
