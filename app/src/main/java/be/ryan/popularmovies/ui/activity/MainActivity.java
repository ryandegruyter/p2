package be.ryan.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ViewUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import be.ryan.popularmovies.App;
import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.FavoriteColumns;
import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;
import be.ryan.popularmovies.domain.TmdbVideosResponse;
import be.ryan.popularmovies.event.BackPressedEvent;
import be.ryan.popularmovies.event.FavoriteEvent;
import be.ryan.popularmovies.event.FetchReviewsEvent;
import be.ryan.popularmovies.event.FetchTrailerEvent;
import be.ryan.popularmovies.event.PageSelectedEvent;
import be.ryan.popularmovies.event.PopularMovieEvent;
import be.ryan.popularmovies.provider.PopularMoviesContract;
import be.ryan.popularmovies.tmdb.TmdbRestClient;
import be.ryan.popularmovies.ui.dialog.ReviewsDialog;
import be.ryan.popularmovies.ui.dialog.TrailerDialog;
import be.ryan.popularmovies.ui.fragment.DetailMovieFragment;
import be.ryan.popularmovies.ui.fragment.MovieListPagerFragment;
import be.ryan.popularmovies.util.Compatibility;
import be.ryan.popularmovies.util.ContentUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_REVIEWS_FRAGMENT = "vers";
    private static final String TAG_TRAILERS_MOVIE = "trailers";

    @Bind(R.id.fragment_list_movies_container)
    FrameLayout containerView;

    private static final String TAG_MOVIE_LIST_PAGER_FRAGMENT = "fragment_movie_pager";
    private static final String TAG = "MainActivity";
    private static final String KEY_ACTIONBAR_SUBTITLE = "actionbar_subtitle";
    public String TAG_MOVIE_DETAIL_FRAGMENT = "detail_movie";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Compatibility.isTablet(this)) {
            // TODO: 26/09/15 init tablet layout
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list_movies_container, new MovieListPagerFragment(), TAG_MOVIE_LIST_PAGER_FRAGMENT)
                    .commit();
        }else {
            //on screen rotate the displayhomeasup is set to false, have to set it to true again but only if the detail view is active
            if (getSupportFragmentManager().findFragmentByTag(TAG_MOVIE_DETAIL_FRAGMENT) != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            getSupportActionBar().setSubtitle(savedInstanceState.getCharSequence(KEY_ACTIONBAR_SUBTITLE));
        }
    }

    /**
     * Called when a tab is selected
     *
     * @param pageSelectedEvent
     */
    public void onEvent(PageSelectedEvent pageSelectedEvent) {
        getSupportActionBar().setSubtitle(pageSelectedEvent.pageTitle);
    }

    /**
     * Called when a user toggles the favorite button
     *
     * @param favoriteEvent
     */
    public void onEvent(FavoriteEvent favoriteEvent) {
        if (favoriteEvent.isFavorite) {
            int rowsDeleted = getContentResolver().delete(
                    PopularMoviesContract.MovieEntry.buildFavoriteUri(),
                    FavoriteColumns.MOVIE_ID + " = ?",
                    new String[]{String.valueOf(favoriteEvent.movieId)}
            );

            // TODO: 04.10.15  rowsdeleted should be 1 at all times, handle if   rowsDeleted >= 1 or <= 0

        }else {
            // content resolve handles insert or update
            getContentResolver().insert(
                    PopularMoviesContract.MovieEntry.buildFavoriteUri(),
                    ContentUtils.prepareFavoriteValues(favoriteEvent.movieId)
            );
        }
    }

    /**
     * Called when a user clicks a movie poster from the list
     *
     * @param movieEvent PopularMovieEvent
     */
    public void onEvent(PopularMovieEvent movieEvent) {
        if (Compatibility.isTablet(this)) {
            // TODO: 26/09/15 init tablet layout for detail view
        } else {
//            Intent startDetailActivityIntent = new Intent(this, DetailActivity.class);

//            ActivityCompat.startActivity(this, );

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list_movies_container, DetailMovieFragment.newInstance(movieEvent.mMovie, movieEvent.mIsFav), TAG_MOVIE_DETAIL_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

        }
    }

    /**
     * called when a user navigates back from a detailmovieview
     *
     * @param backPressedEvent
     */
    public void onEvent(BackPressedEvent backPressedEvent) {
        onBackPressed();
    }

    public void onEvent(FetchTrailerEvent fetchTrailerEvent) {
        TmdbRestClient.getInstance().getService().listYoutubeTrailers(fetchTrailerEvent.movieId, new Callback<TmdbVideosResponse>() {
            @Override
            public void success(TmdbVideosResponse tmdbVideosResponse, Response response) {
                if (tmdbVideosResponse.getVideoList().size() > 0) {
                    TrailerDialog.newInstance(tmdbVideosResponse).show(getSupportFragmentManager(),TAG_TRAILERS_MOVIE);
                } else {
                    Snackbar.make(containerView, "No trailers, sorry!", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(containerView, "You need an internet connection to fetch movies", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void onEvent(FetchReviewsEvent fetchReviewsEvent) {
        TmdbRestClient.getInstance().getService().listMovieReviews(fetchReviewsEvent.movieId, new Callback<TmdbVideoReviewsResponse>() {
            @Override
            public void success(TmdbVideoReviewsResponse tmdbVideoReviewsResponse, Response response) {
                if (tmdbVideoReviewsResponse.getTotalResults()>0) {
                    // TODO: 02/11/2015 show dialogbox with reviews about the movie
                    ReviewsDialog.newInstance(tmdbVideoReviewsResponse).show(getSupportFragmentManager(),TAG_REVIEWS_FRAGMENT);
                }else {
                    Snackbar.make(containerView, "Sorry! No Reviews", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(containerView, "You need an internet connection to fetch reviews", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(KEY_ACTIONBAR_SUBTITLE, getSupportActionBar().getSubtitle());
        super.onSaveInstanceState(outState);
    }


}
