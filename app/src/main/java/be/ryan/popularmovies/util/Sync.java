package be.ryan.popularmovies.util;

import android.content.Context;
import android.os.Bundle;

import be.ryan.popularmovies.sync.PopMovSyncAdapter;

/**
 * Created by Ryan on 02/11/2015.
 */
public class Sync {
    private Sync(){}

    public static void syncMovieList(String orderType, Context context) {
        Bundle syncInfo = new Bundle();

        syncInfo.putInt(PopMovSyncAdapter.SYNC_TYPE, PopMovSyncAdapter.SYNC_MOVIE_LIST);
        syncInfo.putString(PopMovSyncAdapter.KEY_LIST_PATH_NAME, orderType);

        PopMovSyncAdapter.syncImmediately(context, syncInfo);
    }
}
