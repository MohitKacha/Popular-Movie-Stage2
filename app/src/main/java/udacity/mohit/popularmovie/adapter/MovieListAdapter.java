package udacity.mohit.popularmovie.adapter;

import android.content.Context;
import android.mohit.popularmovie.R;
import udacity.mohit.popularmovie.model.Movie;
import udacity.mohit.popularmovie.utils.Constant;
import udacity.mohit.popularmovie.utils.MovieItemClickListner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {


  private Context context;
  final private MovieItemClickListner itemClickListener;
    private List<Movie> movieList;
    public MovieListAdapter(Context context,List<Movie> movieList, MovieItemClickListner itemClickListener){
        this.context = context;
        this.movieList = movieList;
        this.itemClickListener = itemClickListener;
    }



    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_item,parent,false);
        MovieListViewHolder viewHolder = new MovieListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView movieNameTV;
        public MovieListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.movie_img);
            movieNameTV = itemView.findViewById(R.id.movieName_tv);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie){
            String imgUrl = Constant.IMAGE_BASE_URL+Constant.IMAGE_SIZE_342+movie.getPoster_path();
            Log.d("res",imgUrl);
           Glide.with(context)
                    .load(imgUrl)
                    .placeholder(android.R.color.darker_gray)
                    .into(imageView);
           movieNameTV.setText(movie.getTitle());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d("click","on Click "+pos);
            itemClickListener.onItemClicked(getAdapterPosition());
        }
    }
}
