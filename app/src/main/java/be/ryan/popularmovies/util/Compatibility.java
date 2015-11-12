package be.ryan.popularmovies.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Ryan on 09/11/2015.
 */
public class Compatibility {

    private Compatibility(){

    }

    public static boolean isTablet(Context context){
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
