package be.ryan.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import de.greenrobot.event.EventBus;

/**
 * Created by Ryan on 31/08/2015.
 */
public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView poster = null;
    TmdbMovie mTmdbMovie;

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(mTmdbMovie);
    }


    public MovieHolder(View itemView) {
        super(itemView);
        poster = (ImageView) itemView.findViewById(R.id.poster);
        poster.setOnClickListener(this);

    }

    void bindData(TmdbMovie movie) {
        final String posterImgPath = movie.getPosterImgPath();
        String uri = TmdbWebServiceContract.BASE_POSTER_IMG_URL + "/" + posterImgPath;
        //TODO: In the future cache the image and get it locally, or does picasso do that automatically?
        //TODO: Set Error Picture
        //TODO: PlaceholdeR?
        mTmdbMovie = movie;
        Picasso.with(poster.getContext()).load(uri).into(poster);
    }
}
