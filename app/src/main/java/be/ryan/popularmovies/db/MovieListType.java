package be.ryan.popularmovies.db;

import be.ryan.popularmovies.provider.PopularMoviesContract;

/**
 * Created by ryan on 22/09/15.
 */
public interface MovieListType {
    String Favorites = PopularMoviesContract.MovieEntry.PATH_FAVORITE;
    String POPULAR = PopularMoviesContract.MovieEntry.PATH_POPULAR;
    String UPCOMING = PopularMoviesContract.MovieEntry.PATH_UPCOMING;
    String LATEST = PopularMoviesContract.MovieEntry.PATH_LATEST;
    String TOP = PopularMoviesContract.MovieEntry.PATH_TOP;

    String[] MOVIE_LIST_TYPES = {POPULAR, UPCOMING, LATEST, TOP};
}
