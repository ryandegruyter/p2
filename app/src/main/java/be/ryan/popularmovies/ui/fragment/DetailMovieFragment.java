package be.ryan.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
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
import be.ryan.popularmovies.db.FavoriteColumns;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.event.FavoriteEvent;
import be.ryan.popularmovies.provider.PopularMoviesContract;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.ui.util.Utility;
import be.ryan.popularmovies.util.Compatibility;
import be.ryan.popularmovies.util.ContentUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class DetailMovieFragment extends android.support.v4.app.Fragment {

    private static final String ARG_MOVIE = "movie";
    private static final String TAG = "DetailMovieFragment";
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

    public static DetailMovieFragment newInstance(TmdbMovie tmdbMovie) {
        DetailMovieFragment fragment = new DetailMovieFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_MOVIE, Parcels.wrap(tmdbMovie));

        fragment.setArguments(args);

        return fragment;
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

        return view;
    }

    private String getBackDropImgUri() {
            return TmdbWebServiceContract.BASE_BACKDROP_IMG_URL + mMovie.getBackdropImgPath();
    }

    public String getPosterImgUri() {
        return TmdbWebServiceContract.BASE_POSTER_IMG_URL + mMovie.getPosterImgPath();
    }
}
