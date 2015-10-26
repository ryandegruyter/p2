package be.ryan.popularmovies.event;

/**
 * Created by Ryan on 26/10/2015.
 */
public class BackPressedEvent {
    public final String action;

    public BackPressedEvent(String action) {
        this.action = action;
    }
}
