package be.ryan.popularmovies.domain;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Ryan on 27/08/2015.
 */
@Parcel
public class TmdbMoviesPage {

    @SerializedName("page")
    public int pageNr;

    @SerializedName("results")
    private List<TmdbMovie> tmdbMovieList;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("total_results")
    private int total_results;

    public int getPageNr() {
        return pageNr;
    }

    public List<TmdbMovie> getTmdbMovieList() {
        return tmdbMovieList;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    @Override
    public String toString() {
        return "TmdbMoviesPage{" +
                "pageNr=" + pageNr +
                ", tmdbMovieList=" + tmdbMovieList +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
