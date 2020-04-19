package udacity.mohit.popularmovie.adapter;

import android.content.Context;
import android.mohit.popularmovie.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import udacity.mohit.popularmovie.model.Review;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    private List<Review> reviewList;
    private  Context contex;
    public ReviewListAdapter(Context context, List<Review> reviewList){
        this.contex = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewListAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_item,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.ReviewViewHolder holder, int position) {
        holder.bind(reviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName,tvUserReview;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_user_title);
            tvUserReview = itemView.findViewById(R.id.tv_user_review);

        }
        void bind(Review review){
            tvUserName.setText(review.getAuthor());
            tvUserReview.setText(review.getContent());
        }
    }
}
