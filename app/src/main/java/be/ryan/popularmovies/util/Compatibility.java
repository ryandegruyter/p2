package be.ryan.popularmovies.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by Ryan on 09/11/2015.
 */
public class Compatibility {

    private Compatibility(){

    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
    public static boolean isPortait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static boolean isTablet(Context context){
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
