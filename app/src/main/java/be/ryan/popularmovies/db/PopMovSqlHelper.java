package be.ryan.popularmovies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

/**
 * Created by ryan on 5/09/15.
 */
public class PopMovSqlHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "popmov.db";
    static final int DB_VERSION = 2;

    public PopMovSqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableMovies(db);
    }


    private void createTableMovies(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + Tables.Movie + " (" +
                MovieColumns._ID + " INTEGER PRIMARY KEY," +
                MovieColumns.BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieColumns.MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                MovieColumns.ORIGINAL_TITLE  + " TEXT NOT NULL, " +
                MovieColumns.OVERVIEW + " TEXT, " +
                MovieColumns.POSTER_PATH + " TEXT NOT NULL, " +
                MovieColumns.RELEASE_DATE + " TEXT NOT NULL, " +
                MovieColumns.VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieColumns.VOTE_COUNT + " REAL NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.Movie);
        onCreate(db);
    }
}
