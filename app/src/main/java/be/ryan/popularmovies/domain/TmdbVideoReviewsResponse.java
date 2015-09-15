package be.ryan.popularmovies.domain;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by ryan on 14/09/15.
 */
@Parcel
public class TmdbVideoReviewsResponse {

    @SerializedName("id")
    int movieId;

    int page;

    @SerializedName("results")
    List<TmdbMovieReview> reviews;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("total_results")
    int totalResults;

    @Override
    public String toString() {
        return "TmdbVideoReviewsResponse{" +
                "movieId=" + movieId +
                ", page=" + page +
                ", reviews=" + reviews +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TmdbMovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<TmdbMovieReview> reviews) {
        this.reviews = reviews;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
