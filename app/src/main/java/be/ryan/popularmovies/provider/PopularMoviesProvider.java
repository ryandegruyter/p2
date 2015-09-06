package be.ryan.popularmovies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import be.ryan.popularmovies.db.PopMovSqlHelper;
import be.ryan.popularmovies.db.Tables;
import be.ryan.popularmovies.util.ErrorMessages;

/**
 * Created by ryan on 6/09/15.
 */
public class PopularMoviesProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int MOVIE = 100;

    private PopMovSqlHelper dbHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PopularMoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE, MOVIE);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new PopMovSqlHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = dbHelper.getReadableDatabase().query(
                        Tables.Movie,
                        projection,
                        selection,
                        selectionArgs, null, null, sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException(ErrorMessages.getUnknownUriMsg(uri));
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return PopularMoviesContract.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException(ErrorMessages.getUnknownUriMsg(uri));
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                long _id = db.insert(Tables.Movie, null, values);
                if (_id > 0)
                    returnUri = PopularMoviesContract.MovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException(ErrorMessages.getFailedToInsertRowMsg(uri));
                break;
            }
            default:
                throw new UnsupportedOperationException(ErrorMessages.getUnknownUriMsg(uri));
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case MOVIE:
                rowsDeleted = db.delete(
                        Tables.Movie, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(ErrorMessages.getUnknownUriMsg(uri));
        }

        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                rowsUpdated = db.update(Tables.Movie, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(ErrorMessages.getUnknownUriMsg(uri));
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(Tables.Movie, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }

    }
}
