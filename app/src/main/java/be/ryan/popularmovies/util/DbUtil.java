package be.ryan.popularmovies.util;

import android.content.ContentValues;

import be.ryan.popularmovies.db.MovieColumns;
import be.ryan.popularmovies.db.MoviePerListColumns;
import be.ryan.popularmovies.domain.TmdbMovie;

/**
 * Created by ryan on 6/09/15.
 */
public class DbUtil {
    public static ContentValues getMoviePerListCv(TmdbMovie movie, int typeId, int rank) {
        ContentValues values = new ContentValues();
        values.put(MoviePerListColumns.MOVIE_ID, movie.getId());
        values.put(MoviePerListColumns.RANK, rank);
        values.put(MoviePerListColumns.LIST_ID, typeId);
        return values;
    }
    public static ContentValues getTmdbMovieContentValues(TmdbMovie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieColumns.BACKDROP_PATH, movie.getBackdropImgPath());
        values.put(MovieColumns._ID, movie.getId());
        values.put(MovieColumns.ORIGINAL_TITLE, movie.getOriginal_title());
        values.put(MovieColumns.OVERVIEW, movie.getOverView());
        values.put(MovieColumns.POSTER_PATH, movie.getPosterImgPath());
        values.put(MovieColumns.RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieColumns.VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieColumns.VOTE_COUNT, movie.getVoteCount());
        return values;
    }
}
