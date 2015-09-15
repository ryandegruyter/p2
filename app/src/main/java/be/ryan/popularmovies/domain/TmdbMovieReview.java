package be.ryan.popularmovies.domain;

import org.parceler.Parcel;

/**
 * Created by ryan on 14/09/15.
 */
@Parcel
public class TmdbMovieReview {

    String id;

    String author;

    String content;

    String url;

    @Override
    public String toString() {
        return "TmdbMovieReview{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
