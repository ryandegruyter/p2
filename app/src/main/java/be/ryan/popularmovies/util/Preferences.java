package be.ryan.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;

import be.ryan.popularmovies.ui.activity.MainActivity;

/**
 * Created by ryan on 7/09/15.
 */
public class Preferences {

    public static final String SHARED_PREFERENCES_FILE_NAME = "popmovs";
    public static final String UNKNOWN = "unknown";

    public static void setMovieListSortType(String sortOrder, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(Keys.MOVIE_LIST_SORT_TYPE, sortOrder).commit();
    }

    public static String getMovieListSortType(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).getString(Keys.MOVIE_LIST_SORT_TYPE, UNKNOWN);
    }

    public static void clearMoveListSortType(Context context) {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit().remove(Keys.MOVIE_LIST_SORT_TYPE).commit();
    }

    public interface Keys {
        String MOVIE_LIST_SORT_TYPE = "movies_to_fetch";
    }
}
