package udacity.mohit.popularmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.mohit.popularmovie.R;

import udacity.mohit.popularmovie.adapter.MovieListAdapter;
import udacity.mohit.popularmovie.model.Movie;
import udacity.mohit.popularmovie.model.MovieResponse;
import udacity.mohit.popularmovie.utils.Constant;
import udacity.mohit.popularmovie.utils.JsonParsing;
import udacity.mohit.popularmovie.utils.MovieItemClickListner;
import udacity.mohit.popularmovie.utils.NetworkClients;
import udacity.mohit.popularmovie.utils.NetworkResponse;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NetworkResponse, MovieItemClickListner {


    @BindView(R.id.rv_movie_list)  RecyclerView movieListRecyclerView;
    @BindView(R.id.no_data_tv) TextView noDataTV;
    private List<Movie> movieList;
    private NetworkClients networkClients;
    private static final String DATA_RESPONSE_ID = "save-data";
    private static final String MOVIE_RESPONSE = "movie-response";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        networkClients = new NetworkClients(this, this);
        getPopularMovieList();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void responseFromNetwork(String data,String id) {
        Log.d("response", data);
        JsonParsing jsonParsing = new JsonParsing();
        MovieResponse movieData = jsonParsing.parseMovieResponse(data);
        movieList = movieData.getResults();
        if(movieList != null && movieList.size() > 0){

            Log.d("res", movieList.size() + "");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setUpMovieListAdapter();
                }
            });
        }else {
            Log.e("Error","Received null data ");
        }

    }

    private void setUpMovieListAdapter() {
        MovieListAdapter movieListAdapter = new MovieListAdapter(this, movieList, this);
        movieListRecyclerView.setAdapter(movieListAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        movieListRecyclerView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void onItemClicked(int itemClicked) {
        Log.d("click", "onTem called");
        Movie movie = movieList.get(itemClicked);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movieObject", movie);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movieoption,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.action_most_popular){
            getPopularMovieList();
        }else if(itemId == R.id.action_top_rated){
            getTopRatedMovieList();
        }else if(itemId == R.id.action_favorite_movie){
                moveToFavoriteMovie();
        }


        return super.onOptionsItemSelected(item);
    }

    private void getPopularMovieList(){
        Log.d("moviedatadata","geting polular movie");
        if (networkClients.isInternetAvailable()) {
            networkClients.makeNetworkCall(Constant.BASE_URL + Constant.POPULAR_MOVIE + Constant.API_KEY,MOVIE_RESPONSE);
        } else {
            noDataTV.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Phone is not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void getTopRatedMovieList(){
        if (networkClients.isInternetAvailable()) {
            networkClients.makeNetworkCall(Constant.BASE_URL + Constant.TOP_RATED_MOVIE + Constant.API_KEY,MOVIE_RESPONSE);
        } else {
            noDataTV.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Phone is not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToFavoriteMovie(){
        Intent intent = new Intent(this, FavoriteMovieActivity.class);
        startActivity(intent);
    }


}
