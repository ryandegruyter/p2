package be.ryan.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.test.AndroidTestCase;

import junit.framework.Assert;

import be.ryan.popularmovies.db.ListColumns;
import be.ryan.popularmovies.db.MovieListType;
import be.ryan.popularmovies.db.MoviePerListColumns;
import be.ryan.popularmovies.db.PopMovSqlHelper;
import be.ryan.popularmovies.db.Tables;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.util.DbUtil;

/**
 * Created by ryan on 6/09/15.
 */
public class TestDb extends AndroidTestCase {

    PopMovSqlHelper popMovSqlHelper;

    @Override
    protected void setUp() throws Exception {
        getContext().deleteDatabase(PopMovSqlHelper.DB_NAME);

        popMovSqlHelper = new PopMovSqlHelper(getContext());
    }

    public void testDbIsOpen() {
        boolean isOpen = popMovSqlHelper.getReadableDatabase().isOpen();
        assertTrue("db is niet open", isOpen);
    }

    public void testDbInsertMovie() {
        TmdbMovie tmdbMovie = Util.getMovieForTesting();
        ContentValues values = DbUtil.getTmdbMovieContentValues(tmdbMovie);
        SQLiteDatabase database = popMovSqlHelper.getWritableDatabase();
        long insertId = database.insert(Tables.Movie, null, values);
        assertFalse("error occured, id is -1", insertId == -1);
    }

    public void testDbDefaultListTypes() {
        Cursor qryCursor = popMovSqlHelper.getReadableDatabase().query(Tables.List, null, null, null, null, null, null);
        int count = qryCursor.getCount();
        assertEquals(4, count);
    }

    public void testGetMovieAndRankByType() {
        TmdbMovie movieForTesting = Util.getMovieForTesting();
        ContentValues tmdbMovieContentValues = DbUtil.getTmdbMovieContentValues(movieForTesting);
        popMovSqlHelper.getWritableDatabase().insert(Tables.Movie, null, tmdbMovieContentValues);

        Cursor query = popMovSqlHelper.getReadableDatabase().query(Tables.List, new String[]{ListColumns._ID}, ListColumns.ORDER_TYPE + " = ?", new String[]{MovieListType.POPULAR}, null, null, null);
        Assert.assertTrue(query.moveToFirst());

        int id = query.getInt(query.getColumnIndex(ListColumns._ID));
        ContentValues moviePerListCv = DbUtil.getMoviePerListCv(movieForTesting, id, 2);
        long insert = popMovSqlHelper.getWritableDatabase().insert(Tables.MoviePerList, null, moviePerListCv);
        Assert.assertTrue(insert != -1);

        SQLiteQueryBuilder sqLiteQueryBuilder = PopMovSqlHelper.getMoviePerListQueryBuilder();

        Cursor qry = sqLiteQueryBuilder.query(
                popMovSqlHelper.getReadableDatabase(),
                new String[]{ListColumns.ORDER_TYPE, MoviePerListColumns.RANK},
                null,
                null, null, null, null
        );

        qry.moveToFirst();
        int rank = qry.getInt(qry.getColumnIndex(MoviePerListColumns.RANK));
        String orderType = qry.getString(qry.getColumnIndex(ListColumns.ORDER_TYPE));

        assertEquals(2, rank);
        assertEquals(MovieListType.POPULAR, orderType);
    }
}
