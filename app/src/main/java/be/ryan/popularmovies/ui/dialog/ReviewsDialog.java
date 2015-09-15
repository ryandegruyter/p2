package be.ryan.popularmovies.ui.dialog;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;
import be.ryan.popularmovies.ui.adapter.ReviewsAdapter;

/**
 * Created by ryan on 14/09/15.
 */
public class ReviewsDialog extends AppCompatDialogFragment {

    private static final String REVIEWS = "reviews";

    public static ReviewsDialog newInstance(TmdbVideoReviewsResponse reviewsResponse) {

        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(reviewsResponse);
        args.putParcelable(REVIEWS, parcelable);
        ReviewsDialog fragment = new ReviewsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reviews, container, false);
        TmdbVideoReviewsResponse reviewsResponse = Parcels.unwrap(getArguments().getParcelable(REVIEWS));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_reviews_dialog);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ReviewsAdapter(reviewsResponse, getActivity()));
        return view;
    }

}
