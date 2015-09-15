package be.ryan.popularmovies.domain;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ryan on 8/09/15.
 */
@Parcel
public class TmdbVideo {
    public TmdbVideo() {
    }

    @SerializedName("id")
    String id;

    @SerializedName("iso_639")
    String languageCode;

    @SerializedName("key")
    String key;

    @SerializedName("name")
    String name;

    @SerializedName("site")
    String site;

    @SerializedName("size")
    int size;

    @SerializedName("type")
    String type;

    @Override
    public String toString() {
        return "TmdbVideo{" +
                "id='" + id + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
