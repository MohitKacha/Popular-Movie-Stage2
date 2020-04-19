package udacity.mohit.popularmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.mohit.popularmovie.R;

import udacity.mohit.popularmovie.adapter.ReviewListAdapter;
import udacity.mohit.popularmovie.adapter.VideoListAdapter;
import udacity.mohit.popularmovie.database.AppExecutors;
import udacity.mohit.popularmovie.database.MovieDatabase;
import udacity.mohit.popularmovie.model.Movie;
import udacity.mohit.popularmovie.model.Review;
import udacity.mohit.popularmovie.model.ReviewResponse;
import udacity.mohit.popularmovie.model.Video;
import udacity.mohit.popularmovie.model.VideoResponse;
import udacity.mohit.popularmovie.utils.Constant;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.mohit.popularmovie.utils.NetworkClients;
import udacity.mohit.popularmovie.utils.NetworkResponse;

public class MovieDetailsActivity extends AppCompatActivity implements NetworkResponse, VideoListAdapter.VideoItemClickListener {

    @BindView(R.id.movie_backdrop_img)
    ImageView movieBackgroundImg;
    @BindView(R.id.movie_poster_img)
    ImageView moviePosterImg;
    @BindView(R.id.tv_overView)
    TextView overviewTV;
    @BindView(R.id.movie_name_tv)
    TextView movieNameTV;
    @BindView(R.id.release_date_tv)
    TextView releaseDateTV;
    @BindView(R.id.vote_tv)
    TextView voteAverageTV;
    @BindView(R.id.language_tv)
    TextView languageTV;
    @BindView(R.id.rv_trailers)
    RecyclerView trailersRecycler;
    @BindView(R.id.rv_reviews)
    RecyclerView reviewRecycler;

    private Movie movie;
    private static final String VIDEO_RESPONSE = "video-response";
    private static final String REVIEW_RESPONSE = "review-response";
    private NetworkClients networkClients;
    private List<Video> videoList;
    private List<Review> reviewList;
    private MovieDatabase movieDatabase;
    public static  String MOVIE_OBJECT = "movieObject";
    public static String FAVORITE_MOVIE = "favoriteMovie";
    private boolean isFavMovieDetails = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        networkClients = new NetworkClients(this,this);
        movieDatabase = MovieDatabase.getInstance(this);
        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_OBJECT)) {
            movie = (Movie) intent.getParcelableExtra("movieObject");

        }
        if(intent.hasExtra(FAVORITE_MOVIE)){
            isFavMovieDetails = true;
        }
        setData();
        getTrailerDetails();
        getReviewDetails();
    }

    private void setData() {
        setImage(movie.getBackdrop_path(), movieBackgroundImg);
        setImage(movie.getPoster_path(), moviePosterImg);
        overviewTV.setText(movie.getOverview());
        movieNameTV.setText(movie.getTitle());
        releaseDateTV.setText(movie.getRelease_date());
        voteAverageTV.setText(movie.getVote_average() + getResources().getString(R.string.rating_from));
        languageTV.setText(movie.getOriginal_language());

    }

    private void setImage(String url, ImageView imageView) {
        Glide.with(this)
                .load(Constant.IMAGE_BASE_URL + Constant.IMAGE_SIZE_780 + url)
                .placeholder(android.R.color.darker_gray)
                .into(imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.moviedetails_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            onBackPressed();
            return  true;
        }else if(itemId == R.id.action_favorite){
            Log.d("fav","Clicked");
            item.setIcon(getResources().getDrawable(R.drawable.ic_action_favorite_selected));
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    movieDatabase.movieDao().insertMovie(movie);
                }
            });


        }
        return super.onOptionsItemSelected(item);
    }

    private void getTrailerDetails(){
        networkClients.makeNetworkCall(Constant.BASE_URL
                 + Constant.MOVIE
                 + movie.getId() +Constant.VIDEOS + Constant.API_KEY, VIDEO_RESPONSE);
    }

    private void getReviewDetails(){
        networkClients.makeNetworkCall(Constant.BASE_URL
                + Constant.MOVIE
                + movie.getId() +Constant.REVIEWS + Constant.API_KEY, REVIEW_RESPONSE);
    }

    @Override
    public void responseFromNetwork(String data, String id) {
        Gson gson = new Gson();
        if(id.equals(VIDEO_RESPONSE)){
            Log.d("response",id);
            Log.d("response",data);
            VideoResponse videoResponse = gson.fromJson(data,VideoResponse.class);
            videoList = videoResponse.getResults();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setUpTrailerAdapter();
                }
            });

        }else if(id.equals(REVIEW_RESPONSE)){
            Log.d("response",id);
            Log.d("response",data);
            ReviewResponse reviewResponse = gson.fromJson(data,ReviewResponse.class);
            reviewList = reviewResponse.getResults();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setUpReviewAdapter();
                }
            });
        }

    }

    private void setUpTrailerAdapter(){

        VideoListAdapter videoListAdapter = new VideoListAdapter(this,videoList,this);
        trailersRecycler.setAdapter(videoListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        trailersRecycler.setLayoutManager(linearLayoutManager);
        trailersRecycler.setHasFixedSize(true);

    }

    private void setUpReviewAdapter(){
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this,reviewList);
        reviewRecycler.setAdapter(reviewListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviewRecycler.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onTrailerItemClicked(int position) {
        watchYoutubeVideo(videoList.get(position).getKey());

    }

    private void watchYoutubeVideo(String id){
        Intent youtubeAppintent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        try {
            startActivity(youtubeAppintent);
        }catch (ActivityNotFoundException e){
            Intent webAppIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(webAppIntent);
        }
    }
}