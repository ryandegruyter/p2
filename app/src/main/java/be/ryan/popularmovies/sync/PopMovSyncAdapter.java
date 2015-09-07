package be.ryan.popularmovies.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMoviesPage;
import be.ryan.popularmovies.tmdb.TmdbService;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.ui.fragment.MovieListFragment;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ryan on 6/09/15.
 */
public class PopMovSyncAdapter extends AbstractThreadedSyncAdapter implements RequestInterceptor, Callback<TmdbMoviesPage> {

    private static final String TAG = "PopMovSyncAdapter";

    // Interval at which to be.ryan.popularmovies.sync with the weather, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;


    public PopMovSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "onPerformSync");
        //TODO: implement network code
        requestMovieList(extras);
    }

    private void requestMovieList(Bundle bundle) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(TmdbWebServiceContract.BASE_URL)
                .setRequestInterceptor(this)
                .build();
        final TmdbService tmdbService = restAdapter.create(TmdbService.class);

        //TODO: change locattion of parameter key
        final String title = bundle.getString(MovieListFragment.PARAM_KEY_TITLE);
        if (title != null) {
            if (title.equals(getContext().getString(R.string.title_highest_rated))) {
                tmdbService.listTopRatedMovies(this);
            } else if (title.equals(getContext().getString(R.string.title_popular_movies))) {
                tmdbService.listPopularMovies(this);
            } else if (title.equals(getContext().getString(R.string.title_upcoming))) {
                tmdbService.listUpcoming(this);
            } else if (title.equals(getContext().getString(R.string.title_now_playing))) {
                tmdbService.listNowPlayingMovies(this);
            }
        }
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Without calling setSyncAutomatically, our periodic be.ryan.popularmovies.sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
    }

    /**
     * Helper method to have the be.ryan.popularmovies.sync adapter be.ryan.popularmovies.sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context, Bundle bundle) {
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to schedule the be.ryan.popularmovies.sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime, Bundle bundle) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic be.ryan.popularmovies.sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, bundle, syncInterval);
        }
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addQueryParam(TmdbWebServiceContract.QUERY_PARAM_API_KEY, TmdbWebServiceContract.API_KEY);
    }

    @Override
    public void success(TmdbMoviesPage tmdbMoviesPage, Response response) {
        Log.d(TAG, "success: " + tmdbMoviesPage.toString());
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d(TAG, error.toString());
    }
}
