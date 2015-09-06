package be.ryan.popularmovies;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

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
}
