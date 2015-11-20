package be.ryan.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.internal.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.domain.TmdbVideo;
import be.ryan.popularmovies.domain.TmdbVideoReviewsResponse;
import be.ryan.popularmovies.domain.TmdbVideosResponse;
import be.ryan.popularmovies.event.FavoriteEvent;
import be.ryan.popularmovies.tmdb.TmdbRestClient;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.ui.adapter.TrailerListAdapter;
import be.ryan.popularmovies.ui.dialog.ReviewsListAdapter;
import be.ryan.popularmovies.ui.util.Utility;
import be.ryan.popularmovies.util.IntentUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailMovieFragment extends android.support.v4.app.Fragment {

    private static final String ARG_MOVIE = "movie";
    private static final String ARG_IS_FAVORITE = "is_fav";

    private TmdbMovie mMovie;

    @Bind(R.id.movie_detail_backdrop)
    ImageView mBackdropView;

    //Depending on orientation / device might be null
    @Nullable
    @Bind(R.id.movie_detail_poster)
    ImageView mPosterImageView;

    @Bind(R.id.movie_title)
    TextView mTitleView;

    @Bind(R.id.release_date)
    TextView mReleaseDateView;

    @Bind(R.id.synopsis)
    TextView mSynopsisView;

    @Bind(R.id.vote_average)
    RatingBar mRatingBar;

    @Bind(R.id.btnFav)
    ToggleButton mFavButton;

    @Bind(R.id.trailerList)
    ListViewCompat trailerList;

    @Bind(R.id.reviewsList)
    ListViewCompat reviewsList;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(Menu.NONE, R.id.action_share_first_trailer, Menu.NONE, R.string.action_share_trailer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share_first_trailer:
                if (trailerList.getAdapter().isEmpty()) {
                    Snackbar.make(getView(), "Still fetching trailers please wait", Snackbar.LENGTH_SHORT).show();
                } else {
                    TmdbVideo firstTrailer = (TmdbVideo) trailerList.getAdapter().getItem(0);
                    IntentUtil.shareYoutubeVideo(getContext(), firstTrailer.getKey(), mMovie.getOriginal_title());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static DetailMovieFragment newInstance(TmdbMovie tmdbMovie, boolean isFavorite) {
        DetailMovieFragment fragment = new DetailMovieFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_MOVIE, Parcels.wrap(tmdbMovie));
        args.putBoolean(ARG_IS_FAVORITE, isFavorite);

        fragment.setArguments(args);

        return fragment;
    }

    public DetailMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mMovie = Parcels.unwrap(getArguments().getParcelable(ARG_MOVIE));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ButterKnife.bind(this, view);

        if (mPosterImageView != null) {
            Picasso.with(getContext()).load(getPosterImgUri()).into(mPosterImageView);
        }

        Picasso.with(getContext()).load(getBackDropImgUri()).into(mBackdropView);

        mTitleView.setText(mMovie.getOriginal_title());
        mReleaseDateView.setText(Utility.convertToMovieDate(mMovie.getReleaseDate()));
        mSynopsisView.setText(mMovie.getOverView());
        mRatingBar.setRating((float) mMovie.getVoteAverage()/2);

        final boolean isFavorite = getArguments().getBoolean(ARG_IS_FAVORITE);
        if (isFavorite) {
            mFavButton.setChecked(true);
        }
        mFavButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EventBus.getDefault().post(new FavoriteEvent(mMovie.getId(), !isChecked));
            }
        });

        TmdbRestClient.getInstance().getService().listMovieReviews(mMovie.getId(), new Callback<TmdbVideoReviewsResponse>() {
            @Override
            public void success(TmdbVideoReviewsResponse tmdbVideoReviewsResponse, Response response) {
                reviewsList.setAdapter(new ReviewsListAdapter(getContext(), tmdbVideoReviewsResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(getView(), "Cannot fetch reviews", Snackbar.LENGTH_SHORT).show();
            }
        });

        TmdbRestClient.getInstance().getService().listYoutubeTrailers(mMovie.getId(), new Callback<TmdbVideosResponse>() {
            @Override
            public void success(TmdbVideosResponse tmdbVideosResponse, Response response) {
                trailerList.setAdapter(new TrailerListAdapter(getContext(), tmdbVideosResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(getView(), "Cannot fetch trailers", Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private String getBackDropImgUri() {
            return TmdbWebServiceContract.BASE_BACKDROP_IMG_URL + mMovie.getBackdropImgPath();
    }

    public String getPosterImgUri() {
        return TmdbWebServiceContract.BASE_POSTER_IMG_URL + mMovie.getPosterImgPath();
    }
}
