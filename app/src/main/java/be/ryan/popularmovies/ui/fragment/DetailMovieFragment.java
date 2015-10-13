package be.ryan.popularmovies.ui.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.ParseException;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.MovieColumns;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.event.FavoriteEvent;
import be.ryan.popularmovies.provider.PopularMoviesContract;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.ui.util.Utility;
import be.ryan.popularmovies.util.DbUtil;
import de.greenrobot.event.EventBus;

public class DetailMovieFragment extends android.support.v4.app.Fragment {

    private static final String ARG_MOVIE = "movie";
    private static final String TAG = "DetailMovieFragment";
    private static final String ARG_IS_FAVORITE = "is_fav";

    private TmdbMovie mMovie;
    private ImageView mBackdropView;
    private TextView mTitleView;
    private TextView mReleaseDateView;
    private RatingBar mVoteAverageView;
    private TextView mSynopsisView;
    private RatingBar mRatingBar;
    private ToggleButton mFavButton;

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
        mBackdropView = (ImageView) view.findViewById(R.id.movie_detail_backdrop);
        String uri = getMovieImgUri();

        Picasso.with(view.getContext()).load(uri).into(mBackdropView);
        mTitleView = (TextView) view.findViewById(R.id.movie_title);
        mTitleView.setText(mMovie.getTitle());

        mReleaseDateView = (TextView) view.findViewById(R.id.release_date);
        mReleaseDateView.setText(Utility.convertToMovieDate(mMovie.getReleaseDate()));

        mVoteAverageView = (RatingBar) view.findViewById(R.id.vote_average);
        mVoteAverageView.setRating((float) mMovie.getVoteAverage());

        mSynopsisView = (TextView) view.findViewById(R.id.synopsis);
        mSynopsisView.setText(mMovie.getOverView());

        mRatingBar = (RatingBar) view.findViewById(R.id.vote_average);
        mRatingBar.setRating((float) mMovie.getVoteAverage()/2);

        mFavButton = (ToggleButton) view.findViewById(R.id.btnFav);
        boolean isFavorite = getArguments().getBoolean(ARG_IS_FAVORITE);
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

    private String getMovieImgUri() {
        if (Utility.IsLandscape(getActivity())) {
            return TmdbWebServiceContract.BASE_POSTER_IMG_URL + mMovie.getPosterImgPath();
        }else {
            return TmdbWebServiceContract.BASE_BACKDROP_IMG_URL + mMovie.getBackdropImgPath();
        }
    }
}
