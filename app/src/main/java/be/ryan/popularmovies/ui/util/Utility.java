package be.ryan.popularmovies.ui.util;

import android.content.Context;
import android.content.res.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ryan on 02/09/2015.
 */
public class Utility {
    public static boolean IsLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static String convertToMovieDate(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = simpleDateFormat.parse(dateString);

        simpleDateFormat = new SimpleDateFormat("MMMM yyyy");
        return simpleDateFormat.format(date);
    }
}
