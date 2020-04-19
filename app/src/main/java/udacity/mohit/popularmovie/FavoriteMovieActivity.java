package udacity.mohit.popularmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.mohit.popularmovie.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.mohit.popularmovie.adapter.MovieListAdapter;
import udacity.mohit.popularmovie.database.MovieDatabase;
import udacity.mohit.popularmovie.model.Movie;
import udacity.mohit.popularmovie.utils.MovieItemClickListner;
import udacity.mohit.popularmovie.viewmodel.FavMovieViewModel;

public class FavoriteMovieActivity extends AppCompatActivity implements MovieItemClickListner {

    private MovieDatabase movieDatabase;
    @BindView(R.id.rv_favorite_movie)
    RecyclerView favoriteMovieRecycler;
    @BindView(R.id.no_data_tv)
    TextView noDataTV;
    List<Movie> favoriteListMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        ButterKnife.bind(this);
        setUpActionBar();
        movieDatabase = MovieDatabase.getInstance(this);
        getFavoriteMovie();
    }

    private void getFavoriteMovie(){

         final FavMovieViewModel mainViewModel = ViewModelProviders.of(this).get(FavMovieViewModel.class);
        mainViewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                if(movieList != null && movieList.size() > 0 ){
                    noDataTV.setVisibility(View.GONE);
                    initFavoriteMovieAdapter(movieList);
                }else {
                    noDataTV.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void initFavoriteMovieAdapter(List<Movie> movieList){
        MovieListAdapter movieListAdapter = new MovieListAdapter(this,movieList,this);
        favoriteMovieRecycler.setAdapter(movieListAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        favoriteMovieRecycler.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClicked(int itemClicked) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
