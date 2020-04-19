package udacity.mohit.popularmovie.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import udacity.mohit.popularmovie.model.Movie;

@Database(entities = {Movie.class}, version = 1 , exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String LOG_TAG = MovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoriteMovie";
    private static MovieDatabase instance;

    public static MovieDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                Log.d("db","Creating Database");
                instance = Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class,MovieDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d("db","Getting database instance");
        return instance;
    }

    public abstract MovieDao movieDao();
}
