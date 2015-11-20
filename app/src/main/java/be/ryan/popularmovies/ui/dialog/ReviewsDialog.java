package be.ryan.popularmovies.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.internal.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;

import org.parceler.Parcels;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ryan on 14/09/15.
 */
public class ReviewsDialog extends AppCompatDialogFragment {

    private static final String REVIEWS = "reviews";

    @Bind(R.id.list_reviews_dialog)
    ListViewCompat listViewCompat;

    public static ReviewsDialog newInstance(TmdbVideoReviewsResponse reviewsResponse) {
        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(reviewsResponse);
        args.putParcelable(REVIEWS, parcelable);
        ReviewsDialog fragment = new ReviewsDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.dialog_reviews, null);
        ButterKnife.bind(this, view);

        TmdbVideoReviewsResponse reviewsResponse = Parcels.unwrap(getArguments().getParcelable(REVIEWS));

        listViewCompat.setAdapter(new ReviewsListAdapter(getContext(), reviewsResponse));

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                    }
                })
                .setTitle(R.string.title_reviews);

        return builder.create();
    }
}
