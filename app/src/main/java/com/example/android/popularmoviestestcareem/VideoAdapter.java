package com.example.android.popularmoviestestcareem;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Maryline on 3/27/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ImageViewHolder>{


    final private ListItemClickHandler mOnClickHandler;
    public Cursor mCursor;


    List<Video> videos;

    public VideoAdapter(ListItemClickHandler listener) {
        mOnClickHandler = listener;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crear context + layout inflater + create view del movieitem inflando
        // crear imageview holder y pasar el view que acabo de inflar + return imageview


        Context context = parent.getContext();
        int layoutIdForPoster = R.layout.videoitem;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForPoster, parent, shouldAttachToParentImmediately);
        ImageViewHolder viewHolder = new ImageViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, int position) {
        Context context = viewHolder.videoThumbnail.getContext();

        Video video = videos.get(position);
        String key = video.getKey();

        String baseUrl = "https://img.youtube.com/vi/";

        Picasso.with(context).load(baseUrl + key + "/0.jpg").into(viewHolder.videoThumbnail);
    }

    @Override
    public int getItemCount() {
        //return cuantos item en Json array
        return videos == null ? 0 : videos.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView videoThumbnail;

        public ImageViewHolder(View itemView) {
            super(itemView);

            videoThumbnail = (ImageView) itemView.findViewById(R.id.video_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Video video = videos.get(clickedPosition);
            String mVideoId = video.getKey();
            mOnClickHandler.onClick(mVideoId);



        }
    }

    public interface ListItemClickHandler{
        void onClick(String videoKey);
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        //Update the local mCursor to be equal to  newCursor
        mCursor = newCursor;
        //Check if the newCursor is not null, and call this.notifyDataSetChanged() if so
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


}
