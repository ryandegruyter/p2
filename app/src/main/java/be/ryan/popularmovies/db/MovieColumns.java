package be.ryan.popularmovies.db;

import android.provider.BaseColumns;

/**
 * Created by ryan on 5/09/15.
 */
public interface MovieColumns extends BaseColumns{
    String BACKDROP_PATH = "backdrop_path";
    String POSTER_PATH = "poster_path";
    String ORIGINAL_TITLE = "original_title";
    String OVERVIEW = "overview";
    String RELEASE_DATE = "release_date";
    String VOTE_COUNT = "vote_count";
    String VOTE_AVERAGE = "vote_average";
    String MOVIE_ID = "id";
}
