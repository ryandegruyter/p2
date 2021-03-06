package be.ryan.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.FavoriteColumns;
import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;
import be.ryan.popularmovies.domain.TmdbVideosResponse;
import be.ryan.popularmovies.event.FavoriteEvent;
import be.ryan.popularmovies.event.FetchReviewsEvent;
import be.ryan.popularmovies.event.FetchTrailerEvent;
import be.ryan.popularmovies.event.MovieSelectedEvent;
import be.ryan.popularmovies.event.PageSelectedEvent;
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
    private static final String TAG_MOVIE_LIST_PAGER_FRAGMENT = "fragment_movie_pager";
    private static final String TAG = "MainActivity";
    private static final String KEY_ACTIONBAR_SUBTITLE = "actionbar_subtitle";
    public static final String TAG_MOVIE_DETAIL_FRAGMENT = "detail_movie";
    private static final String KEY_HOME_AS_UP_ENABLED = "key_home_as_up_enabled";

    @Bind(R.id.fragment_list_movies_container)
    FrameLayout movieListFragmentContainer;

    //Might be null because we only use this view for a tablet
    @Nullable
    @Bind(R.id.fragment_movie_detail)
    FrameLayout movieDetailFragmentContainer;
    
    private boolean isHomeAsUpEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        Fragment movieListFragment = new MovieListPagerFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list_movies_container, movieListFragment, TAG_MOVIE_LIST_PAGER_FRAGMENT)
                    .commit();
        }else {
            //on screen rotate the displayHomeAsUp is set to false, set it back to true only if the detail view is active and were not on a tablet
            isHomeAsUpEnabled = savedInstanceState.getBoolean(KEY_HOME_AS_UP_ENABLED);

            if (!Compatibility.isTablet(this) && isHomeAsUpEnabled) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUpEnabled);
            }

            getSupportActionBar().setSubtitle(savedInstanceState.getCharSequence(KEY_ACTIONBAR_SUBTITLE));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                isHomeAsUpEnabled = false;
                getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUpEnabled);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
     * @param movieEvent MovieSelectedEvent
     */
    public void onEvent(MovieSelectedEvent movieEvent) {
        //Only show the Up button on phone layouts
        if (!Compatibility.isTablet(this)) {
            isHomeAsUpEnabled = true;
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUpEnabled);
        }

        DetailMovieFragment detailMovieFragment = DetailMovieFragment.newInstance(movieEvent.mMovie, movieEvent.mIsFav);
        int detailViewResId = R.id.fragment_list_movies_container;

        if (Compatibility.isTablet(this)) {
            detailViewResId = R.id.fragment_movie_detail;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(detailViewResId, detailMovieFragment, TAG_MOVIE_DETAIL_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    public void onEvent(FetchTrailerEvent fetchTrailerEvent) {
        TmdbRestClient.getInstance().getService().listYoutubeTrailers(fetchTrailerEvent.movieId, new Callback<TmdbVideosResponse>() {
            @Override
            public void success(TmdbVideosResponse tmdbVideosResponse, Response response) {
                if (tmdbVideosResponse.getVideoList().size() > 0) {
                    TrailerDialog.newInstance(tmdbVideosResponse).show(getSupportFragmentManager(),TAG_TRAILERS_MOVIE);
                } else {
                    Snackbar.make(movieListFragmentContainer, "No trailers, sorry!", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(movieListFragmentContainer, "You need an internet connection to fetch movies", Snackbar.LENGTH_SHORT).show();
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
                    Snackbar.make(movieListFragmentContainer, "Sorry! No Reviews", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(movieListFragmentContainer, "You need an internet connection to fetch reviews", Snackbar.LENGTH_SHORT).show();
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
        outState.putBoolean(KEY_HOME_AS_UP_ENABLED, isHomeAsUpEnabled);
        super.onSaveInstanceState(outState);
    }
}
