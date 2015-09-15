package be.ryan.popularmovies.event;

import be.ryan.popularmovies.domain.TmdbMovie;

/**
 * Created by ryan on 8/09/15.
 */
public class FetchTrailerEvent {
    public final int movieId;

    public FetchTrailerEvent(int movieId) {
        this.movieId= movieId;
    }
}
