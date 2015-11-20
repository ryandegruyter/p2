package be.ryan.popularmovies.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;

/**
 * Created by Ryan on 20/11/2015.
 */
public class ReviewsListAdapter extends BaseAdapter {
    Context context;
    TmdbVideoReviewsResponse reviewsResponse;

    public ReviewsListAdapter(Context context, TmdbVideoReviewsResponse reviewsResponse) {
        this.context = context;
        this.reviewsResponse = reviewsResponse;
    }

    @Override
    public int getCount() {
        return reviewsResponse.getReviews().size();
    }

    @Override
    public Object getItem(int position) {
        return reviewsResponse.getReviews().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_selectable_list_item, null);
        }
        TextView authorView = (TextView) convertView.findViewById(android.R.id.text1);
        TextView contentView = (TextView) convertView.findViewById(android.R.id.text1);
        authorView.setText(reviewsResponse.getReviews().get(position).getAuthor());
        contentView.setText(reviewsResponse.getReviews().get(position).getContent());

        return convertView;
    }
}
