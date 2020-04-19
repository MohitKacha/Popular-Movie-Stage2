package udacity.mohit.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MovieResponse implements Parcelable {
    private String total_results;
    private String  total_pages ;




    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    private List<Movie> results;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.total_results);
        dest.writeString(this.total_pages);
        dest.writeTypedList(this.results);
    }

    public MovieResponse() {
    }

    protected MovieResponse(Parcel in) {
        this.total_results = in.readString();
        this.total_pages = in.readString();
        this.results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Parcelable.Creator<MovieResponse> CREATOR = new Parcelable.Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel source) {
            return new MovieResponse(source);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };
}
