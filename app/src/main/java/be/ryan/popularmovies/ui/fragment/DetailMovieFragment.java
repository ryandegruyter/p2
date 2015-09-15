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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.ParseException;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.db.MovieColumns;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.provider.PopularMoviesContract;
import be.ryan.popularmovies.tmdb.TmdbWebServiceContract;
import be.ryan.popularmovies.ui.util.Utility;
import be.ryan.popularmovies.util.DbUtil;

public class DetailMovieFragment extends android.support.v4.app.Fragment {

    private static final String ARG_MOVIE = "movie";
    private static final String TAG = "DetailMovieFragment";

    private TmdbMovie mMovie;
    private ImageView mBackdropView;
    private TextView mTitleView;
    private TextView mReleaseDateView;
    private RatingBar mVoteAverageView;
    private TextView mSynopsisView;
    private RatingBar mRatingBar;
    private Button mFavButton;

    public static DetailMovieFragment newInstance(TmdbMovie tmdbMovie) {
        DetailMovieFragment fragment = new DetailMovieFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, Parcels.wrap(tmdbMovie));
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
        try {
            mReleaseDateView.setText(Utility.convertToMovieDate(mMovie.getReleaseDate()));
        } catch (ParseException e) {
            mReleaseDateView.setText(mMovie.getReleaseDate());
        }

        mVoteAverageView = (RatingBar) view.findViewById(R.id.vote_average);
        mVoteAverageView.setRating((float) mMovie.getVoteAverage());

        mSynopsisView = (TextView) view.findViewById(R.id.synopsis);
        mSynopsisView.setText(mMovie.getOverView());

        mRatingBar = (RatingBar) view.findViewById(R.id.vote_average);
        mRatingBar.setRating((float) mMovie.getVoteAverage()/2);

        mFavButton = (Button) view.findViewById(R.id.btnFav);
        mFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parcelable parcelable = getArguments().getParcelable(ARG_MOVIE);
                TmdbMovie movie = Parcels.unwrap(parcelable);
                DbUtil.getTmdbMovieContentValues(movie);

                Cursor query = getActivity().getContentResolver().query(PopularMoviesContract.MovieEntry.CONTENT_URI,
                        new String[]{MovieColumns.MOVIE_ID},
                        "id = ?",
                        new String[]{String.valueOf(movie.getId())},
                        null, null);
                if (query.getCount() > 0) {
                    getActivity().getContentResolver().delete(PopularMoviesContract.MovieEntry.CONTENT_URI, "id = ?", new String[]{String.valueOf(movie.getId())});
                }else {
                    getActivity().getContentResolver().insert(PopularMoviesContract.MovieEntry.CONTENT_URI, DbUtil.getTmdbMovieContentValues(movie));
                }
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
