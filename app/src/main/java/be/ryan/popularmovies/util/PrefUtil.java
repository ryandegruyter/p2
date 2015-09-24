package be.ryan.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;

import java.security.Key;

import be.ryan.popularmovies.ui.activity.MainActivity;

/**
 * Created by ryan on 7/09/15.
 */
public class PrefUtil {

    public static final String SHARED_PREFERENCES_FILE_NAME = "popmovs";
    private static final String TAG = "PrefUtil";

    public static void clear(Context context) {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }
    public static boolean isFirstRun(Context context, String key) {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).getBoolean(key, true);
    }

    public static void setFirstRunFinished(Context context, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, false).commit();
    }

    public interface Keys {
        String IS_FIRST_RUN_POPULAR = "is_first_run";
        String IS_FIRST_RUN_TOP = "is_first_run";
        String IS_FIRST_RUN_LATEST = "is_first_run";
        String IS_FIRST_RUN_UPCOMING = "is_first_run";
    }
}
