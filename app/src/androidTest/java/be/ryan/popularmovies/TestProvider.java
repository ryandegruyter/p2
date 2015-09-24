package be.ryan.popularmovies;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.ProviderTestCase2;

import be.ryan.popularmovies.db.MovieColumns;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.provider.PopularMoviesContract;
import be.ryan.popularmovies.provider.PopularMoviesProvider;
import be.ryan.popularmovies.util.DbUtil;

/**
 * Created by ryan on 6/09/15.
 */
public class TestProvider extends AndroidTestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getContext().getContentResolver().delete(
                PopularMoviesContract.MovieEntry.CONTENT_URI, null, null
        );
    }

    public void testInsertMovie() {
        TmdbMovie movie = Util.getMovieForTesting();
        Uri uri = getContext().getContentResolver().insert(PopularMoviesContract.MovieEntry.CONTENT_URI, DbUtil.getTmdbMovieContentValues(movie));
        long id = ContentUris.parseId(uri);
        assertFalse(id == -1);
    }

    public void testQueryPopularMovies() {
        Cursor qryCursor = getContext().getContentResolver().query(PopularMoviesContract.MovieEntry.getPopularMoviesUri(),
                new String[]{MovieColumns.MOVIE_ID}, null, null, null, null);
        int count = qryCursor.getCount();
        assertTrue(count==10);
    }

}
