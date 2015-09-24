package be.ryan.popularmovies.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeIntents;
import com.squareup.picasso.Picasso;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbVideo;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.util.IntentUtil;

/**
 * Created by ryan on 15/09/15.
 */
public class TrailerViewHolder extends RecyclerView.ViewHolder{
    TextView mType = null;
    ImageView mVideoImage = null;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        mType = (TextView) itemView.findViewById(R.id.type);
        mVideoImage = (ImageView) itemView.findViewById(R.id.img_video);
    }

    public void bindData(final TmdbVideo video) {
        mType.setText(video.getName());
        mType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.watchYoutubeVideo(video.getKey(),v.getContext());
            }
        });
    }
}
