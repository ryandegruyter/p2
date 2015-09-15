package be.ryan.popularmovies.tmdb;

import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.domain.TmdbMoviesPage;
import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;
import be.ryan.popularmovies.domain.TmdbVideosResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Ryan on 27/08/2015.
 */
public interface TmdbService {

    @GET("/discover/movies")
    void listMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/{id}/reviews")
    void listMovieReviews(@Path("id") int movieId,Callback<TmdbVideoReviewsResponse> callback);

    @GET("/movie/{id}/videos")
    void listYoutubeTrailers(@Path("id") int movieId,Callback<TmdbVideosResponse> callback);

    @GET("/movie/popular")
    void listPopularMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/top_rated")
    void listTopRatedMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/now_playing")
    void listNowPlayingMovies(Callback<TmdbMoviesPage> callback);

    @GET("/movie/upcoming")
    void listUpcoming(Callback<TmdbMoviesPage> callback);
}
