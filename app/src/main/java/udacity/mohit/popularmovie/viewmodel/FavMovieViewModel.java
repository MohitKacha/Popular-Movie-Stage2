package udacity.mohit.popularmovie.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import udacity.mohit.popularmovie.database.MovieDatabase;
import udacity.mohit.popularmovie.model.Movie;

public class FavMovieViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> favMovieList;

    public FavMovieViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase database = MovieDatabase.getInstance(this.getApplication());
        favMovieList = database.movieDao().getFavMovies();

    }

    public LiveData<List<Movie>> getFavoriteMovies(){
        return favMovieList;
    }
}
