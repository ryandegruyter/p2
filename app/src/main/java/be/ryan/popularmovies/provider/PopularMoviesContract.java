package be.ryan.popularmovies.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

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
        public static final String PATH_LATEST = "latest";
        public static final String PATH_UPCOMING = "upcoming";
        public static final String PATH_TOP = "top_rated";
        public static final String PATH_FAVORITE = "favorites";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;


        public static Uri buildFavoriteUri() {
            return Uri.withAppendedPath(CONTENT_URI, PATH_FAVORITE);
        }

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
