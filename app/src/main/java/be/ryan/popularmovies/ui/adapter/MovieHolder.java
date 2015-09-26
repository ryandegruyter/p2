package be.ryan.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.event.FetchReviewsEvent;
import be.ryan.popularmovies.event.FetchTrailerEvent;
import be.ryan.popularmovies.event.PopularMovieEvent;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import de.greenrobot.event.EventBus;

/**
 * Created by Ryan on 31/08/2015.
 */
public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    ImageView mPosterView = null;
    Toolbar mMediaToolbar = null;
    TmdbMovie mTmdbMovie;

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(mTmdbMovie);
    }


    public MovieHolder(View itemView) {
        super(itemView);
        mPosterView = (ImageView) itemView.findViewById(R.id.poster);
        mPosterView.setOnClickListener(this);
        mMediaToolbar = (Toolbar) itemView.findViewById(R.id.media_toolbar);
        mMediaToolbar.inflateMenu(R.menu.menu_media_toolbar);
        mMediaToolbar.setOnMenuItemClickListener(this);
    }

    void bindData(TmdbMovie movie) {
        final String posterImgPath = movie.getPosterImgPath();
        String uri = TmdbWebServiceContract.BASE_POSTER_IMG_URL + "/" + posterImgPath;
        //TODO: Set Error Picture
        //TODO: PlaceholdeR?
        mTmdbMovie = movie;
        Picasso.with(mPosterView.getContext()).load(uri).fit().centerCrop().into(mPosterView);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_trailers:
                EventBus.getDefault().post(new FetchTrailerEvent(mTmdbMovie.getId()));
                break;
            case R.id.menu_item_reviews:
                EventBus.getDefault().post(new FetchReviewsEvent(mTmdbMovie.getId()));
                break;
            case R.id.menu_item_favorite:
                EventBus.getDefault().post(new PopularMovieEvent(mTmdbMovie.getId()));
                break;
        }
        return true;
    }
}
