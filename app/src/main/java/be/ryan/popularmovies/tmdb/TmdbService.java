package be.ryan.popularmovies.tmdb;

import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.domain.TmdbMoviesPage;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Ryan on 27/08/2015.
 */
public interface TmdbService {

    //TODO: Check type of JSON object gets returned
    @GET("/discover/movies")
    void listMovies(Callback<TmdbMoviesPage> callback);

    //TODO: create get reviews interface

    //TODO: create get details and trailers interface


    @GET("/movie/popular")
    void listPopularMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/top_rated")
    void listTopRatedMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/now_playing")
    void listNowPlayingMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/upcoming")
    void listUpcoming(Callback<TmdbMoviesPage> callback);
}
