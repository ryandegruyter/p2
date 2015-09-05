package be.ryan.popularmovies.tmdb;

import be.ryan.popularmovies.domain.TmdbMoviesPage;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Ryan on 27/08/2015.
 */
public interface TmdbService {

    @GET("/movie/popular")
    void listPopularMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/top_rated")
    void listTopRatedMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/now_playing")
    void listNowPlayingMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/upcoming")
    void listUpcoming(Callback<TmdbMoviesPage> callback);
}
