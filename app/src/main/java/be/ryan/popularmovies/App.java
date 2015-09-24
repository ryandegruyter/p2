package be.ryan.popularmovies;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

import be.ryan.popularmovies.db.PopMovSqlHelper;
import be.ryan.popularmovies.util.PrefUtil;

/**
 * Created by ryan on 23/09/15.
 */
public class App extends Application{
    public static final boolean DEBUG = false;
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        if (DEBUG) {
            deleteDatabase(PopMovSqlHelper.DB_NAME);
            PrefUtil.clear(this);

            long startTime = SystemClock.elapsedRealtime();
            initializeStetho(this);
            long elapsed = SystemClock.elapsedRealtime() - startTime;
            Log.i(TAG, "Stetho initialized in " + elapsed + " ms");
        }
    }

    private void initializeStetho(final Context context) {
        Stetho.initializeWithDefaults(context);
    }
}
