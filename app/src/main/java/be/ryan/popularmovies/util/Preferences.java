package be.ryan.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ryan on 7/09/15.
 */
public class Preferences {

    public static final String SHARED_PREFERENCES_FILE_NAME = "popmovs";

    public static void setMoviesToFetch(String sortOrder, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(Keys.MOVIES_TO_FETCH, sortOrder).apply();
    }

    public interface Keys {
        String MOVIES_TO_FETCH = "movies_to_fetch";
    }
}
