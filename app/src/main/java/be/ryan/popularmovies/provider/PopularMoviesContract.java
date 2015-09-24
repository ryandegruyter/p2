package be.ryan.popularmovies.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.List;

import be.ryan.popularmovies.db.ListType;

/**
 * Created by ryan on 6/09/15.
 */
public class PopularMoviesContract {
    public static final String CONTENT_AUTHORITY = "be.ryan.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    //    when you add this path to an uri the content provider will try and make a request to the REST service
    public static final String PATH_FLAG_REMOTE = "remote";

    public static final class MovieEntry {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String PATH_POPULAR = "popular";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String PATH_LATEST = ListType.LATEST;
        public static final String PATH_UPCOMING = ListType.UPCOMING;
        public static final String PATH_TOP = ListType.TOP;
        public static final String PATH_FAVORITE = ListType.Favorites;


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri getPopularMoviesUri() {
            return Uri.withAppendedPath(CONTENT_URI, PATH_POPULAR);
        }

        public static Uri buildMovieListUri(String orderType) {
            return Uri.withAppendedPath(CONTENT_URI, orderType);
        }
    }

}
