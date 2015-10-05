package be.ryan.popularmovies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import be.ryan.popularmovies.db.ListColumns;
import be.ryan.popularmovies.db.MovieColumns;
import be.ryan.popularmovies.db.MovieListType;
import be.ryan.popularmovies.db.PopMovSqlHelper;
import be.ryan.popularmovies.db.Tables;
import be.ryan.popularmovies.util.ContentUtils;
import be.ryan.popularmovies.util.ErrorMessages;

/**
 * Created by ryan on 6/09/15.
 */
public class PopularMoviesProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int MOVIE = 100;
    private static final int MOVIE_LIST_POPULAR = 101;
    private static final int MOVIE_LIST_TOP_RATED = 102;
    private static final int MOVIE_LIST_UPCOMING = 103;
    private static final int MOVIE_LIST_LATEST = 104;
    private static final int MOVIE_LIST_POPULAR_REMOTE = 105;
    private static final String TAG = "PopularMoviesProvider";
    private static final int FAVORITES = 106;
    private static final int UPDATE_FAVORITE = 107;

    private PopMovSqlHelper dbHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PopularMoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE + "/" + PopularMoviesContract.MovieEntry.PATH_POPULAR, MOVIE_LIST_POPULAR);
        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE + "/" + PopularMoviesContract.MovieEntry.PATH_LATEST, MOVIE_LIST_LATEST);
        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE + "/" + PopularMoviesContract.MovieEntry.PATH_UPCOMING, MOVIE_LIST_UPCOMING);
        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE + "/" + PopularMoviesContract.MovieEntry.PATH_TOP, MOVIE_LIST_TOP_RATED);
        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE + "/" + PopularMoviesContract.MovieEntry.PATH_FAVORITE, FAVORITES);
        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE + "/" + PopularMoviesContract.MovieEntry.PATH_POPULAR + "/" + PopularMoviesContract.PATH_FLAG_REMOTE, MOVIE_LIST_POPULAR_REMOTE);

        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE + "/#/*", UPDATE_FAVORITE);
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
            case MOVIE_LIST_POPULAR:
                cursor = PopMovSqlHelper.getMoviePerListAndIsFavoriteQueryBuilder().query(
                        dbHelper.getReadableDatabase(),
                        projection, ListColumns.ORDER_TYPE + " = ?", new String[]{MovieListType.POPULAR}, null, null, sortOrder
                );
                break;
            case MOVIE_LIST_UPCOMING:
                cursor = PopMovSqlHelper.getMoviePerListAndIsFavoriteQueryBuilder().query(
                        dbHelper.getReadableDatabase(),
                        projection, ListColumns.ORDER_TYPE + " = ?", new String[]{MovieListType.UPCOMING}, null, null, sortOrder
                );
                break;
            case MOVIE_LIST_TOP_RATED:
                cursor = PopMovSqlHelper.getMoviePerListAndIsFavoriteQueryBuilder().query(
                        dbHelper.getReadableDatabase(),
                        projection, ListColumns.ORDER_TYPE + " = ?", new String[]{MovieListType.TOP}, null, null, sortOrder
                );
                break;
            case MOVIE_LIST_LATEST:
                cursor = PopMovSqlHelper.getMoviePerListAndIsFavoriteQueryBuilder().query(
                        dbHelper.getReadableDatabase(),
                        projection, ListColumns.ORDER_TYPE + " = ?", new String[]{MovieListType.LATEST}, null, null, sortOrder
                );
                break;
            case FAVORITES:
                cursor = PopMovSqlHelper.getFavoritesQueryBuilder().query(
                        dbHelper.getReadableDatabase(),
                        projection, null, null, null, null, null
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
            case MOVIE_LIST_POPULAR:
                return PopularMoviesContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_LIST_POPULAR_REMOTE:
                return PopularMoviesContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_LIST_UPCOMING:
                return PopularMoviesContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_LIST_LATEST:
                return PopularMoviesContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_LIST_TOP_RATED:
                return PopularMoviesContract.MovieEntry.CONTENT_TYPE;
            case FAVORITES:
                return PopularMoviesContract.MovieEntry.CONTENT_TYPE;
            case UPDATE_FAVORITE:
                return PopularMoviesContract.MovieEntry.CONTENT_ITEM_TYPE;
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

        int returnCount = 0;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                db.beginTransaction();
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
            case MOVIE_LIST_POPULAR: case MOVIE_LIST_LATEST:case MOVIE_LIST_TOP_RATED: case MOVIE_LIST_UPCOMING:
                final String movieListOrder = uri.getLastPathSegment();

                StringBuilder sql = new StringBuilder();
                sql
                        .append("SELECT ")
                        .append(ListColumns._ID)
                        .append(" FROM ")
                        .append(Tables.List)
                        .append(" WHERE ")
                        .append(ListColumns.ORDER_TYPE)
                        .append(" = ?;");

                Cursor cursor = db.rawQuery(sql.toString(), new String[]{movieListOrder});

                if (cursor.moveToFirst()) {
                    int movieRank = 0;
                    int movieListOrderId = cursor.getInt(cursor.getColumnIndex(ListColumns._ID));

                    db.beginTransaction();
                    try {
                        for (ContentValues value : values) {
//                            insert movie: might be a new movie, if not ignore
                            db.insertWithOnConflict(Tables.Movie, null, value, SQLiteDatabase.CONFLICT_IGNORE);

//                            insert or replace into our many to many table, binds a movie to a list type with rank, should always replace, might have a different rank
                            int movieId = (int) value.get(MovieColumns._ID);
                            ContentValues movieToListValues = ContentUtils.prepareMoviePerListValues(movieListOrderId, movieId, movieRank++);
                            long _id = db.replace(
                                    Tables.MoviePerList,
                                    null,movieToListValues
                            );
                            if (_id != -1) {
                                returnCount++;
                            }
                        }
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }

    }
}
