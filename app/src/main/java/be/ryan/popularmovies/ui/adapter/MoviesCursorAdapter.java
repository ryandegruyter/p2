package be.ryan.popularmovies.ui.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.MovieColumns;
import be.ryan.popularmovies.domain.TmdbMovie;

/**
 * Created by ryan on 8/09/15.
 */
public class MoviesCursorAdapter extends RecyclerView.Adapter<MovieHolder> {

    private final Context mContext;
    Cursor movies = null;

    public MoviesCursorAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View item = inflater.inflate(R.layout.tmdb_movie_grid_list_item, parent, false);
        return new MovieHolder(item);
    }

    public void swapCursor(Cursor cursor) {
        movies = cursor;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        movies.moveToPosition(position);
        bindData(holder);
    }

    private void bindData(MovieHolder holder) {
        String backDropPath = movies.getString(movies.getColumnIndex(MovieColumns.BACKDROP_PATH));
        int movieId = movies.getInt(movies.getColumnIndex(MovieColumns._ID));
        String title = movies.getString(movies.getColumnIndex(MovieColumns.ORIGINAL_TITLE));
        String overView = movies.getString(movies.getColumnIndex(MovieColumns.OVERVIEW));
        String posterPath = movies.getString(movies.getColumnIndex(MovieColumns.POSTER_PATH));
        String releaseDate = movies.getString(movies.getColumnIndex(MovieColumns.RELEASE_DATE));
        double voteAverage = movies.getDouble(movies.getColumnIndex(MovieColumns.VOTE_AVERAGE));
        int voteCount = movies.getInt(movies.getColumnIndex(MovieColumns.VOTE_COUNT));

        TmdbMovie movie = new TmdbMovie();
        movie.setBackdropImgPath(backDropPath);
        movie.setVoteAverage(voteAverage);
        movie.setVoteCount(voteCount);
        movie.setReleaseDate(releaseDate);
        movie.setOriginal_title(title);
        movie.setId(movieId);
        movie.setOverView(overView);
        movie.setPosterImgPath(posterPath);

        holder.bindData(movie);
    }


    @Override
    public int getItemCount() {
        if (movies != null) {
            return movies.getCount();
        }
        return 0;
    }
}
