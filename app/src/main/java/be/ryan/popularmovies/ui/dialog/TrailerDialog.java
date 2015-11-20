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
import be.ryan.popularmovies.domain.TmdbVideosResponse;
import be.ryan.popularmovies.ui.adapter.TrailerListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ryan on 10/09/15.
 */
public class TrailerDialog extends AppCompatDialogFragment {

    private static final String LIST_VIDEOS = "list_videos";

    @Bind(R.id.list_trailers_dialog)
    ListViewCompat listViewCompat;

    public static TrailerDialog newInstance(TmdbVideosResponse tmdbVideosResponse) {
        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(tmdbVideosResponse);
        args.putParcelable(LIST_VIDEOS, parcelable);
        TrailerDialog fragment = new TrailerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.dialog_trailers, null);
        ButterKnife.bind(this, view);

        listViewCompat.setAdapter(new TrailerListAdapter(getContext(),getTrailers()));

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                    }
                })
        .setTitle(R.string.title_trailers);

        return builder.create();
    }

    private TmdbVideosResponse getTrailers() {
        return Parcels.unwrap(getArguments().getParcelable(LIST_VIDEOS));
    }
}
