package be.ryan.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.event.FavoriteEvent;
import be.ryan.popularmovies.event.FetchReviewsEvent;
import be.ryan.popularmovies.event.FetchTrailerEvent;
import be.ryan.popularmovies.event.PopularMovieEvent;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import de.greenrobot.event.EventBus;

/**
 * Created by Ryan on 31/08/2015.
 */
public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    MenuItem mFavoriteMenuItem;
    ImageView mPosterView = null;
    Toolbar mMediaToolbar = null;
    TmdbMovie mTmdbMovie;
    private boolean isFavorite;

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new PopularMovieEvent(mTmdbMovie, isFavorite));
    }


    public MovieHolder(View itemView) {
        super(itemView);
        mPosterView = (ImageView) itemView.findViewById(R.id.poster);
        mPosterView.setOnClickListener(this);
        mMediaToolbar = (Toolbar) itemView.findViewById(R.id.media_toolbar);
        mMediaToolbar.inflateMenu(R.menu.menu_media_toolbar);
        mMediaToolbar.setOnMenuItemClickListener(this);
        mFavoriteMenuItem = mMediaToolbar.getMenu().findItem(R.id.menu_item_favorite);
    }

    void bindData(TmdbMovie movie, boolean isFavorite) {
        final String posterImgPath = movie.getPosterImgPath();
        String uri = TmdbWebServiceContract.BASE_POSTER_IMG_URL + "/" + posterImgPath;
        setIsFavorite(isFavorite);
        //TODO: Set Error Picture
        //TODO: PlaceholdeR?
        mTmdbMovie = movie;
        Picasso.with(mPosterView.getContext())
                .load(uri)
                .fit()
                .centerCrop()
                .into(mPosterView);
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
                EventBus.getDefault().post(new FavoriteEvent(mTmdbMovie.getId(), item.isChecked()));
                setIsFavorite(!item.isChecked());
                break;
        }
        return true;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            mFavoriteMenuItem.setChecked(isFavorite);
            mFavoriteMenuItem.setIcon(android.support.design.R.drawable.abc_btn_rating_star_on_mtrl_alpha);
        } else {
            mFavoriteMenuItem.setChecked(isFavorite);
            mFavoriteMenuItem.setIcon(android.support.design.R.drawable.abc_btn_rating_star_off_mtrl_alpha);
        }
    }
}
