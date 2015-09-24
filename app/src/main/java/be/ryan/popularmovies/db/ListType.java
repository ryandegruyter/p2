package be.ryan.popularmovies.db;

import be.ryan.popularmovies.provider.PopularMoviesContract;

/**
 * Created by ryan on 22/09/15.
 */
public interface ListType {
    String Favorites = "favorites";
    String POPULAR = PopularMoviesContract.MovieEntry.PATH_POPULAR;
    String UPCOMING = "upcoming";
    String LATEST = "latest";
    String TOP = "topRated";

    String[] orderTypes = {POPULAR, UPCOMING, LATEST, TOP};
}
