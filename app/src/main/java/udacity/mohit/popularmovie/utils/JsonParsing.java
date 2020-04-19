package udacity.mohit.popularmovie.utils;

import udacity.mohit.popularmovie.model.Movie;
import udacity.mohit.popularmovie.model.MovieResponse;

import com.google.gson.Gson;

import java.util.List;

public class JsonParsing {

    public MovieResponse parseMovieResponse(String response){

        Gson gson = new Gson();
        MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);

        List<Movie> movieList = movieResponse.getResults();

        return  movieResponse;

    }

    public void parseTrailerResponse(String response){

    }

}
