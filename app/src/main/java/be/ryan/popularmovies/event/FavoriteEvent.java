package be.ryan.popularmovies.event;

/**
 * Created by ryan on 04.10.15.
 */
public class FavoriteEvent {
    public final boolean isFavorite;
    public final int movieId;


    public FavoriteEvent(int movieId, boolean isFavorite) {
        this.movieId = movieId;
        this.isFavorite = isFavorite;
    }
}
