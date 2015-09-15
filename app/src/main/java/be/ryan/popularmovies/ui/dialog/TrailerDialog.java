package be.ryan.popularmovies.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import org.parceler.Parcels;

import be.ryan.popularmovies.domain.TmdbVideosResponse;

/**
 * Created by ryan on 10/09/15.
 */
public class TrailerDialog extends AppCompatDialogFragment {

    private static final String LIST_VIDEOS = "list_videos";

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TmdbVideosResponse videoLinks = Parcels.unwrap(getArguments().getParcelable(LIST_VIDEOS));
        builder.setMessage(videoLinks.toString());
        return builder.create();
    }
}
