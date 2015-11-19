package be.ryan.popularmovies.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbVideosResponse;
import be.ryan.popularmovies.ui.adapter.TrailerAdapter;

/**
 * Created by ryan on 10/09/15.
 */
public class TrailerDialog extends AppCompatDialogFragment {

    private static final String LIST_VIDEOS = "list_videos";
    private RecyclerView mRecyclerView = null;

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_trailers_dialog);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TrailerAdapter adapter = new TrailerAdapter(getActivity(), getTrailers());
        mRecyclerView.setAdapter(adapter);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                    }
                });

        return builder.create();
    }

    private TmdbVideosResponse getTrailers() {
        return Parcels.unwrap(getArguments().getParcelable(LIST_VIDEOS));
    }
}
