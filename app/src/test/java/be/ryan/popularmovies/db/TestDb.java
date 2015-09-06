package be.ryan.popularmovies.db;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import org.junit.Test;

/**
 * Created by ryan on 6/09/15.
 */
public class TestDb extends AndroidTestCase {

    @Test
    public void testDbExists() {
        assertNotNull("Context cant be null",getContext());
        SQLiteDatabase database = new PopMovSqlHelper(getContext()).getReadableDatabase();
        assertTrue(database.isOpen());
    }

}
