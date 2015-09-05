package be.ryan.popularmovies.domain;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ryan on 29/08/2015.
 */
@Parcel
public class TmdbMovie {

    public TmdbMovie() {
    }

    @SerializedName("adult")
    boolean isAdult;

    @SerializedName("backdrop_path")
    String backdropImgPath;

    @SerializedName("genre_ids")
     int[] genreIds;

     int id;

    @SerializedName("original_language")
     String originalLanguage;

    @SerializedName("original_title")
     String original_title;

    @SerializedName("overview")
     String overView;

    @SerializedName("release_date")
     String releaseDate;

    @SerializedName("poster_path")
     String posterImgPath;

    @SerializedName("popularity")
     Double popularity;

     String title;

    @SerializedName("video")
     boolean isVideo;

    @SerializedName("vote_average")
     double voteAverage;

    @SerializedName("vote_count")
    int voteCount;

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    public void setBackdropImgPath(String backdropImgPath) {
        this.backdropImgPath = backdropImgPath;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterImgPath(String posterImgPath) {
        this.posterImgPath = posterImgPath;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getBackdropImgPath() {
        return backdropImgPath;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverView() {
        return overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterImgPath() {
        return posterImgPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        return "TmdbMovie{" +
                "isAdult=" + isAdult +
                ", backdropImgPath='" + backdropImgPath + '\'' +
                ", genreIds=" + Arrays.toString(genreIds) +
                ", id=" + id +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overView='" + overView + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", posterImgPath='" + posterImgPath + '\'' +
                ", popularity=" + popularity +
                ", title='" + title + '\'' +
                ", isVideo=" + isVideo +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                '}';
    }
}
