package be.ryan.popularmovies.event;

import be.ryan.popularmovies.domain.TmdbMovie;

/**
 * Created by Ryan on 31/08/2015.
 */
public class PopularMovieEvent {
    public final TmdbMovie mMovie;

    public PopularMovieEvent(TmdbMovie movie) {
        this.mMovie= movie;
    }
}
