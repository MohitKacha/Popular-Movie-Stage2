package udacity.mohit.popularmovie.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import udacity.mohit.popularmovie.model.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie where id = :id")
    Movie getMovieById(String id);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getFavMovies();

}
