package be.ryan.popularmovies.event;

/**
 * Created by ryan on 9/09/15.
 */
public class FetchReviewsEvent {
    public final int movieId;

    public FetchReviewsEvent(int movieId) {
        this.movieId = movieId;
    }
}
