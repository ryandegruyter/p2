package be.ryan.popularmovies.event;

import be.ryan.popularmovies.domain.TmdbMoviesPage;

/**
 * Created by ryan on 7/09/15.
 */
public class MovieListEvent {
    public final TmdbMoviesPage tmdbMoviesPage;

    public MovieListEvent(TmdbMoviesPage tmdbMoviesPage) {
        this.tmdbMoviesPage = tmdbMoviesPage;
    }
}
