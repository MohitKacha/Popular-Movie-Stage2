package udacity.mohit.popularmovie.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity (tableName = "movie")
public class Movie implements Parcelable {

    @PrimaryKey @NotNull
    String id;
    String popularity;
    String poster_path;
    String adult;
    String backdrop_path;
    String original_language;
    String original_title;
    String overview;
    String release_date;
    String title;
    String video;
    String vote_average;
    String vote_count;


    public Movie(String id, String popularity, String poster_path, String adult, String backdrop_path, String original_language, String original_title, String overview, String release_date, String title, String video, String vote_average, String vote_count) {
        this.id = id;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.popularity);
        dest.writeString(this.poster_path);
        dest.writeString(this.adult);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.id);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.title);
        dest.writeString(this.video);
        dest.writeString(this.vote_average);
        dest.writeString(this.vote_count);
    }
    @Ignore
    public Movie() {
    }


    @Ignore
    protected Movie(Parcel in) {
        this.popularity = in.readString();
        this.poster_path = in.readString();
        this.adult = in.readString();
        this.backdrop_path = in.readString();
        this.id = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.title = in.readString();
        this.video = in.readString();
        this.vote_average = in.readString();
        this.vote_count = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }
}
