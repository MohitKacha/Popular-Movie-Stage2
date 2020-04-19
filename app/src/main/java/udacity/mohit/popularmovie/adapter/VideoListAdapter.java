package udacity.mohit.popularmovie.adapter;

import android.content.Context;
import android.mohit.popularmovie.R;

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

import udacity.mohit.popularmovie.model.Video;
import udacity.mohit.popularmovie.utils.Constant;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {

    private List<Video> videoList;
    private Context context;
    private VideoItemClickListener itemClickListener;
    public VideoListAdapter(Context context, List<Video> videoList, VideoItemClickListener itemClickListener){
        this.context = context;
        this.videoList = videoList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public VideoListAdapter.VideoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.video_item,parent,false);
        //View view = inflater.inflate(R.layout.,parent,false);
        return new VideoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListAdapter.VideoListViewHolder holder, int position) {
        holder.bind(videoList.get(position));

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public interface VideoItemClickListener{
        void onTrailerItemClicked(int position);
    }

    public class VideoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTrailer;
        TextView tvTrailer;
        public VideoListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTrailer =  itemView.findViewById(R.id.iv_trailer_pic);
            tvTrailer =  itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
        }
        void bind(Video video){
            String url = Constant.VIDEO_BASE_URL+ video.getKey() + Constant.QUALITY;
            tvTrailer.setText(video.getName());
            Glide.with(context)
                    .load(url)
                    .placeholder(android.R.color.darker_gray)
                    .into(ivTrailer);


        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            itemClickListener.onTrailerItemClicked(pos);

        }
    }

}
