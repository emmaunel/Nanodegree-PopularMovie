package com.wordpress.ayo218.popularmovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.Interface.OnItemClickListener;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.model.Trailers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private static final String TAG = "TrailersAdapter";
    public static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";
    private Context context;
    private List<Trailers> trailersList;
    private OnItemClickListener listener;


    public TrailersAdapter(Context context, List<Trailers> trailersList, OnItemClickListener listener) {
        this.context = context;
        this.trailersList = trailersList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.trailer_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(view1 -> listener.onItemClick(view1, viewHolder.getAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (trailersList == null){
            return;
        }

        Trailers trailers = trailersList.get(position);
        holder.txt_trailerName.setText(trailers.getTrailer_name());

        Picasso.get()
                .load(String.format(YOUTUBE_THUMBNAIL, trailers.getTrailer_key()))
                .fit()
                .centerCrop()
                .into(holder.trailer_thumbnail);

    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    public Trailers getItem(int position){
        if (trailersList == null || position < 0 || position > trailersList.size()){
            return null;
        }

        return trailersList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.video_thumbnail)
        ImageView trailer_thumbnail;
        @BindView(R.id.txt_trailerName)
        TextView txt_trailerName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
