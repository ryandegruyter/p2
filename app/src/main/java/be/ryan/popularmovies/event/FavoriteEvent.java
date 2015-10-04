package be.ryan.popularmovies.event;

/**
 * Created by ryan on 04.10.15.
 */
public class FavoriteEvent {
    public final int movieId;


    public FavoriteEvent(int movieId) {
        this.movieId = movieId;
    }
}
