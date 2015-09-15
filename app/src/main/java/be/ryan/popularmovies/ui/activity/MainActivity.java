package be.ryan.popularmovies.ui.activity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.domain.TmdbMoviesPage;
import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;
import be.ryan.popularmovies.domain.TmdbVideosResponse;
import be.ryan.popularmovies.event.FetchReviewsEvent;
import be.ryan.popularmovies.event.FetchTrailerEvent;
import be.ryan.popularmovies.sync.PopMovSyncAdapter;
import be.ryan.popularmovies.tmdb.TmdbService;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.ui.dialog.ReviewsDialog;
import be.ryan.popularmovies.ui.dialog.TrailerDialog;
import be.ryan.popularmovies.ui.fragment.DetailMovieFragment;
import be.ryan.popularmovies.ui.fragment.MovieListPagerFragment;
import be.ryan.popularmovies.util.Preferences;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.support.v7.app.AlertDialog.*;

public class MainActivity extends AppCompatActivity implements RequestInterceptor{

    private static final String TAG_MOVIE_LIST_PAGER_FRAGMENT = "fragment_movie_pager";
    public String TAG_MOVIE_DETAIL_FRAGMENT = "detail_movie";

    private LinearLayout mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainerView = (LinearLayout) findViewById(R.id.container_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mContainerView.getId(), new MovieListPagerFragment(), TAG_MOVIE_LIST_PAGER_FRAGMENT)
                    .commit();
        }

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

    public void onEvent(TmdbMovie movie) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(mContainerView.getId(), DetailMovieFragment.newInstance(movie), TAG_MOVIE_DETAIL_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    public void onEvent(FetchTrailerEvent event) {
        getTmdbService().listYoutubeTrailers(event.movieId, new Callback<TmdbVideosResponse>() {
            @Override
            public void success(TmdbVideosResponse tmdbVideosResponse, Response response) {
                TrailerDialog trailerDialog = TrailerDialog.newInstance(tmdbVideosResponse);
                trailerDialog.show(getSupportFragmentManager(), "dialog_frag_tmdb_trailers");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void onEvent(FetchReviewsEvent event) {
        getTmdbService().listMovieReviews(event.movieId, new Callback<TmdbVideoReviewsResponse>() {
            @Override
            public void success(TmdbVideoReviewsResponse tmdbVideoReviewsResponse, Response response) {
                ReviewsDialog reviewsDialog = ReviewsDialog.newInstance(tmdbVideoReviewsResponse);
                reviewsDialog.show(getSupportFragmentManager(), "dialog_frag_tmdb_reviews");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addQueryParam(TmdbWebServiceContract.QUERY_PARAM_API_KEY, TmdbWebServiceContract.API_KEY);
    }

    private TmdbService getTmdbService(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(TmdbWebServiceContract.BASE_URL)
                .setRequestInterceptor(this)
                .build();

        return  restAdapter.create(TmdbService.class);
    }

}
