package be.ryan.popularmovies.event;

/**
 * Created by Ryan on 31/08/2015.
 */
public class PopularMovieEvent {
    public final int mMovieId;

    public PopularMovieEvent(int movieId) {
        this.mMovieId = movieId;
    }
}
