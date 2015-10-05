package be.ryan.popularmovies.util;

import android.content.ContentValues;

import java.util.List;

import be.ryan.popularmovies.db.FavoriteColumns;
import be.ryan.popularmovies.db.MoviePerListColumns;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.domain.TmdbMoviesPage;

/**
 * Created by ryan on 22/09/15.
 */
public class ContentUtils {

    public static ContentValues[] getValuesFromTmdbPage(TmdbMoviesPage page) {
        final List<TmdbMovie> tmdbMovieList = page.getTmdbMovieList();

        ContentValues[] valuesArray = new ContentValues[tmdbMovieList.size()];

        for (int i = 0; i <tmdbMovieList.size(); i++) {
            TmdbMovie movie = tmdbMovieList.get(i);
            ContentValues movieContentValues = DbUtil.getTmdbMovieContentValues(movie);
            valuesArray[i] = movieContentValues;
        }

        return valuesArray;
    }

    public static ContentValues prepareMoviePerListValues(int listTypeId, int movieId, int rank) {
        ContentValues values = new ContentValues();

        values.put(MoviePerListColumns.LIST_ID, listTypeId);
        values.put(MoviePerListColumns.MOVIE_ID, movieId);
        values.put(MoviePerListColumns.RANK, rank);
        return values;
    }

    public static ContentValues prepareFavoriteValues(int movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteColumns.MOVIE_ID, movieId);
        return contentValues;
    }
}
