package be.ryan.popularmovies;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.stetho.Stetho;

import be.ryan.popularmovies.db.PopMovSqlHelper;
import be.ryan.popularmovies.ui.activity.ToolbarDelegate;
import be.ryan.popularmovies.util.PrefUtil;
import timber.log.Timber;

/**
 * Created by ryan on 23/09/15.
 */
public class App extends Application{
    public static final boolean DEBUG = true;

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        if (DEBUG) {
            deleteDatabase(PopMovSqlHelper.DB_NAME);
            PrefUtil.clear(this);
            initializeStetho(this);
        }
    }

    private void initializeStetho(final Context context) {
        Stetho.initializeWithDefaults(context);
    }
}
