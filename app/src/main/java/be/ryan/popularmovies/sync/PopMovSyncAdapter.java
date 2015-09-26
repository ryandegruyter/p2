package be.ryan.popularmovies.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.AsyncQueryHandler;
import be.ryan.popularmovies.db.MovieListType;
import be.ryan.popularmovies.domain.TmdbMoviesPage;
import be.ryan.popularmovies.tmdb.TmdbRestClient;
import be.ryan.popularmovies.util.ContentUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static be.ryan.popularmovies.provider.PopularMoviesContract.MovieEntry;

/**
 * Created by ryan on 6/09/15.
 */
public class PopMovSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "PopMovSyncAdapter";

    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    public static final String SYNC_TYPE = "order_type";
    public static final String KEY_LIST_PATH_NAME = "path";
    public static final String KEY_MOVIE = "movie";

    public static final int SYNC_ORDER_TYPE_POPULAR = 1;
    public static final int SYNC_ORDER_TYPE_TOP_RATED = 2;
    public static final int SYNC_ORDER_TYPE_UPCOMING = 3;
    public static final int SYNC_ORDER_TYPE_LATEST = 4;

    public static final int SYNC_MOVIE_LIST = 5;

    public static final int GET_MOVIE_REVIEWS = 5;
    public static final int GET_MOVIE_TRAILERS = 6;


    public PopMovSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, final Bundle extras, String authority, final ContentProviderClient provider, SyncResult syncResult) {
        final int syncType = extras.getInt(SYNC_TYPE);
//        if the app is ran for the first time, populate the database by making all requests
        switch (syncType) {
            case SYNC_MOVIE_LIST:
                final String orderType = extras.getString(KEY_LIST_PATH_NAME);
                Callback<TmdbMoviesPage> listMovieCallBack = new Callback<TmdbMoviesPage>() {
                    @Override
                    public void success(TmdbMoviesPage tmdbMoviesPage, Response response) {
                        final long startTime = SystemClock.elapsedRealtime();

                        AsyncQueryHandler handler = new AsyncQueryHandler(getContext().getContentResolver()) {

                            @Override
                            protected void onBulkInsertComplete(int token, Object cookie, int result) {
                                long elapsed = SystemClock.elapsedRealtime() - startTime;
                                Log.i(TAG, "bulkInsert complete in " + elapsed + " ms");
                            }
                        };

                        final ContentValues[] values = ContentUtils.getValuesFromTmdbPage(tmdbMoviesPage);
                        handler.startBulkInsert(1, null, Uri.withAppendedPath(MovieEntry.CONTENT_URI, orderType), values);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                };
                switch (orderType) {
                    case MovieListType.POPULAR: {
                        TmdbRestClient.getInstance().getService().listPopularMovies(listMovieCallBack);
                        break;
                    }
                    case MovieListType.UPCOMING: {
                        TmdbRestClient.getInstance().getService().listUpcoming(listMovieCallBack);
                        break;
                    }
                    case MovieListType.TOP: {
                        TmdbRestClient.getInstance().getService().listTopRatedMovies(listMovieCallBack);
                        break;
                    }
                    case MovieListType.LATEST: {
                        TmdbRestClient.getInstance().getService().listNowPlayingMovies(listMovieCallBack);
                        break;
                    }
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
        }
        return newAccount;
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
}
