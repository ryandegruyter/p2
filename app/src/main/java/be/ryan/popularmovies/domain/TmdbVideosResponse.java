package be.ryan.popularmovies.domain;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by ryan on 8/09/15.
 */
@Parcel
public class TmdbVideosResponse {

    @SerializedName("id")
    public int movieId;

    @SerializedName("results")
    public List<TmdbVideo> videoList;

    @Override
    public String toString() {
        return "TmdbVideosResponse{" +
                "movieId=" + movieId +
                ", videoList=" + videoList +
                '}';
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<TmdbVideo> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<TmdbVideo> videoList) {
        this.videoList = videoList;
    }
}
