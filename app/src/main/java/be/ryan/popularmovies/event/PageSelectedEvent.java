package be.ryan.popularmovies.event;

/**
 * Created by ryan on 06.10.15.
 */
public class PageSelectedEvent {
    public final CharSequence pageTitle;

    public PageSelectedEvent(CharSequence pageTitle) {
        this.pageTitle = pageTitle;
    }
}
