package com.bigappcompany.gstindia.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.model.VideoModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
        private static final String YOUTUBE_API_KEY = "AIzaSyCEVYt1v_LfRtTt4mjpnkt1FIT6qi7Up-w";
        private final ArrayList<VideoModel> mItemList;

        public VideoAdapter() {
                mItemList = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
                return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
                holder.titleTV.setText(mItemList.get(position).getTitle());
                final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                youTubeThumbnailView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                        }
                };

                holder.youTubeThumbnailView.initialize(YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                                youTubeThumbnailLoader.setVideo(mItemList.get(holder.getAdapterPosition()).getVideoId());
                                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                        }

                        @Override
                        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                                //write something for failure
                        }
                });
        }

        @Override
        public int getItemCount() {
                return mItemList.size();
        }

        public void addItem(VideoModel item) {
                mItemList.add(item);
                notifyItemInserted(mItemList.size() - 1);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                YouTubeThumbnailView youTubeThumbnailView;
                ImageView playButton;
                TextView titleTV;

                ViewHolder(View itemView) {
                        super(itemView);

                        titleTV = (TextView) itemView.findViewById(R.id.tv_title);
                        playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
                        youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);

                        playButton.setOnClickListener(this);
                }

                @Override
                public void onClick(View v) {
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                            (Activity) playButton.getContext(),
                            YOUTUBE_API_KEY,
                            mItemList.get(getAdapterPosition()).getVideoId(),
                            0, true, false
                        );
                        playButton.getContext().startActivity(intent);
                }
        }
}