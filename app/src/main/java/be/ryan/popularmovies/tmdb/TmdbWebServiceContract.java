package be.ryan.popularmovies.tmdb;

import be.ryan.popularmovies.BuildConfig;

/**
 * Created by Ryan on 27/08/2015.
 */
public class TmdbWebServiceContract {
    public static String API_KEY = BuildConfig.TMDB_API_KEY;
    public static String QUERY_PARAM_API_KEY = "api_key";

    public static String BASE_URL = "https://api.themoviedb.org/3/";
    public static String BASE_IMG_URL = "http://image.tmdb.org/t/p/";

    public static String BACKDROP_IMG_SIZE_300 = "w300";
    public static String BACKDROP_IMG_SIZE_780 = "w780";
    public static String BASE_BACKDROP_IMG_URL = BASE_IMG_URL + BACKDROP_IMG_SIZE_780;

    public static String IMG_SIZE_500 = "w500";
    public static String IMG_SIZE_154 = "w154";
    public static String IMG_SIZE_185 = "w185";
    public static String IMG_SIZE_342 = "w342";
    public static String BASE_POSTER_IMG_URL = BASE_IMG_URL + IMG_SIZE_342;

    public static String SECURE_BASE_URL = "https://image.tmdb.org/t/p/";
}
