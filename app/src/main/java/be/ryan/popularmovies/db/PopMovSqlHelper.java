package be.ryan.popularmovies.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import org.parceler.guava.collect.Table;

import be.ryan.popularmovies.event.FavoriteEvent;

/**
 * Created by ryan on 5/09/15.
 */
public class PopMovSqlHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "popmov.db";
    static final int DB_VERSION = 11;
    private static final String TAG = "PopMovSqlHelper";

    public PopMovSqlHelper(Context context) {
        super(context, DB_NAME, new SQLiteDatabase.CursorFactory() {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                Log.d(TAG, query.toString());
                return new SQLiteCursor(masterQuery, editTable, query);
            }
        }, DB_VERSION);
    }

    public static String getRawQueryMovieList() {
        return "SELECT *, case when (select movie_id from favorite where movieperlist.movie_id = favorite.movie_id) is not null then 'true' else 'false' end as fav FROM movieperlist INNER JOIN list ON movieperlist.list_id = list._id INNER JOIN movie ON movieperlist.movie_id = movie._id WHERE (type = ?)";
    }
    public static String getRawQueryFavorites() {
        return "SELECT *, case when movie_id is not null then 'true' else 'false' end as fav FROM favorite INNER JOIN movie ON favorite.movie_id = movie._id";
    }

    public static SQLiteQueryBuilder getMoviePerListQueryBuilder(){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(
                Tables.MoviePerList +
                        " INNER JOIN " + Tables.List + " ON " + Tables.MoviePerList + "." + MoviePerListColumns.LIST_ID +
                        " = " + Tables.List + "." + ListColumns._ID +
                        " INNER JOIN " + Tables.Movie + " ON " + Tables.MoviePerList + "." + MoviePerListColumns.MOVIE_ID +
                        " = " + Tables.Movie + "." + MovieColumns._ID
        );

        return queryBuilder;
    }

    public static SQLiteQueryBuilder getMoviePerListAndIsFavoriteQueryBuilder() {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(
                Tables.Favorite + ", " + Tables.MoviePerList +
                        " INNER JOIN " + Tables.List + " ON " + Tables.MoviePerList + "." + MoviePerListColumns.LIST_ID +
                        " = " + Tables.List + "." + ListColumns._ID +
                        " INNER JOIN " + Tables.Movie + " ON " + Tables.MoviePerList + "." + MoviePerListColumns.MOVIE_ID +
                        " = " + Tables.Movie + "." + MovieColumns._ID
        );
        return queryBuilder;
    }

    public static SQLiteQueryBuilder getFavoritesQueryBuilder() {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(
                Tables.Favorite +
                        " INNER JOIN " + Tables.Movie + " ON " + Tables.Favorite + "." + FavoriteColumns.MOVIE_ID +
                        " = " + Tables.Movie + "." + MovieColumns._ID
        );
        return queryBuilder;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableMovies(db);
        createTableMoviePerList(db);
        createTableList(db);
        createTableFavorites(db);
    }

    private void createTableFavorites(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + Tables.Favorite + " (" +
                FavoriteColumns._ID + " INTEGER PRIMARY KEY," +
                FavoriteColumns.MOVIE_ID + " INT NOT NULL UNIQUE, " +
                "FOREIGN KEY (" + FavoriteColumns.MOVIE_ID + ")" + " REFERENCES " + Tables.Movie + "(" + MovieColumns._ID +"));";
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }


    private void createTableList(SQLiteDatabase db) {
        final String SQL_CREATE_LIST_TABLE = "CREATE TABLE " + Tables.List + " (" +
                ListColumns._ID + " INTEGER PRIMARY KEY," +
                ListColumns.ORDER_TYPE + " TEXT NOT NULL UNIQUE);";

        db.execSQL(SQL_CREATE_LIST_TABLE);
        final String[] createDefaultListTypeQueries = new String[]{
                "INSERT INTO " + Tables.List + " ( " + ListColumns.ORDER_TYPE + " ) VALUES ('" + MovieListType.POPULAR + "')",
                "INSERT INTO " + Tables.List + " ( " + ListColumns.ORDER_TYPE + " ) VALUES ('" + MovieListType.LATEST+ "')",
                "INSERT INTO " + Tables.List + " ( " + ListColumns.ORDER_TYPE + " ) VALUES ('" + MovieListType.TOP + "')",
                "INSERT INTO " + Tables.List + " ( " + ListColumns.ORDER_TYPE + " ) VALUES ('" + MovieListType.UPCOMING + "')"
        };
        for (String insertQry :
                createDefaultListTypeQueries) {
            db.execSQL(insertQry);
        }
    }

    private void createTableMoviePerList(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_PER_LIST_TABLE = "CREATE TABLE " + Tables.MoviePerList + " (" +
                MoviePerListColumns._ID + " INTEGER PRIMARY KEY," +
                MoviePerListColumns.MOVIE_ID + " INTEGER NOT NULL," +
                MoviePerListColumns.RANK + " INTEGER NOT NULL," +
                MoviePerListColumns.LIST_ID + " INTEGER NOT NULL," +
                "UNIQUE (" + MoviePerListColumns.MOVIE_ID + "," + MoviePerListColumns.LIST_ID + ")," +
                "FOREIGN KEY (" + MoviePerListColumns.MOVIE_ID + ")" + " REFERENCES " + Tables.Movie + "(" + MovieColumns._ID +")," +
                "FOREIGN KEY (" + MoviePerListColumns.LIST_ID + ")" +  " REFERENCES " + Tables.List + "(" + ListColumns._ID +")" +
                ");";

        db.execSQL(SQL_CREATE_MOVIE_PER_LIST_TABLE);
    }

    private void createTableMovies(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + Tables.Movie + " (" +
                MovieColumns._ID + " INTEGER PRIMARY KEY," +
                MovieColumns.BACKDROP_PATH + " TEXT, " +
                MovieColumns.ORIGINAL_TITLE  + " TEXT, " +
                MovieColumns.OVERVIEW + " TEXT, " +
                MovieColumns.POSTER_PATH + " TEXT, " +
                MovieColumns.RELEASE_DATE + " TEXT, " +
                MovieColumns.VOTE_AVERAGE + " REAL, " +
                MovieColumns.VOTE_COUNT + " REAL" +
                " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.Movie);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.List);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.MoviePerList);
        onCreate(db);
    }
}
