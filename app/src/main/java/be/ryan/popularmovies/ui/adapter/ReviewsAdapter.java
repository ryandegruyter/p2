package be.ryan.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;

/**
 * Created by ryan on 14/09/15.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private final TmdbVideoReviewsResponse mTmdbVideoReviews;
    private final Context mContext;

    public ReviewsAdapter(TmdbVideoReviewsResponse reviews, Context context) {
        mTmdbVideoReviews = reviews;
        mContext = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.review_recycler_linear_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bindData(mTmdbVideoReviews.getReviews().get(position));
    }

    @Override
    public int getItemCount() {
        return mTmdbVideoReviews.getReviews() == null ? 0 : mTmdbVideoReviews.getReviews().size();
    }
}
