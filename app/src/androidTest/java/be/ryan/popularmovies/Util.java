package be.ryan.popularmovies;

import be.ryan.popularmovies.domain.TmdbMovie;

/**
 * Created by ryan on 6/09/15.
 */
public class Util {
    static TmdbMovie getMovieForTesting() {
        TmdbMovie tmdbMovie = new TmdbMovie();
        tmdbMovie.setBackdropImgPath("url");
        tmdbMovie.setId(22);
        tmdbMovie.setOriginal_title("batman");
        tmdbMovie.setOverView("dude in wings en shit");
        tmdbMovie.setPosterImgPath("posterurl");
        tmdbMovie.setReleaseDate("16/06/1921");
        tmdbMovie.setVoteAverage(7);
        tmdbMovie.setVoteCount(100);

        return tmdbMovie;
    }
}
