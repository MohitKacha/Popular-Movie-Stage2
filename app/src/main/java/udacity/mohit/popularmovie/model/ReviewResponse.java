package udacity.mohit.popularmovie.model;

import java.util.List;

public class ReviewResponse {

    List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
