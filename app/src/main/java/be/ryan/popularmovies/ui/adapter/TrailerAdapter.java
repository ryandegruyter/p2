package be.ryan.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbVideo;
import be.ryan.popularmovies.domain.TmdbVideosResponse;

/**
 * Created by ryan on 15/09/15.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder>{

    private Context mContext = null;
    private List<TmdbVideo> mTmdbVideos = null;

    public TrailerAdapter(Context context, TmdbVideosResponse tmdbVideosResponse) {
        mTmdbVideos = tmdbVideosResponse.getVideoList();
        mContext = context;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.trailer_recycler_linear_item, parent);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bindData(mTmdbVideos.get(position));
    }

    @Override
    public int getItemCount() {
        return mTmdbVideos == null ? 0 : mTmdbVideos.size();
    }
}
