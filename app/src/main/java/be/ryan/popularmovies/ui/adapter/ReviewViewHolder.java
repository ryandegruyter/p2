package be.ryan.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovieReview;

/**
 * Created by ryan on 14/09/15.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    TextView mName;
    TextView mContent;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        mName = (TextView) itemView.findViewById(R.id.txtReviewerName);
        mContent = (TextView) itemView.findViewById(R.id.txtContent);
    }

    public void bindData(TmdbMovieReview review) {
        mName.setText(review.getAuthor());
        mContent.setText(review.getContent());
    }
}
