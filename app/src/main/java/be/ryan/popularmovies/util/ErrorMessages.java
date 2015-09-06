package be.ryan.popularmovies.util;

import android.net.Uri;

/**
 * Created by ryan on 6/09/15.
 */
public class ErrorMessages {
    public static String getUnknownUriMsg(Uri uri) {
        return "Unknown Uri: " + uri;
    }

    public static String getFailedToInsertRowMsg(Uri uri) {
        return "Failed to insert row into " + uri;
    }
}
