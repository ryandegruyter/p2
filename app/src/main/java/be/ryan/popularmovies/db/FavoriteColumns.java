package be.ryan.popularmovies.db;

import android.provider.BaseColumns;

/**
 * Created by ryan on 23/09/15.
 */
public interface FavoriteColumns extends BaseColumns{
    String MOVIE_ID = "movie_id";
    String IS_FAVORITE = "fav";
}
