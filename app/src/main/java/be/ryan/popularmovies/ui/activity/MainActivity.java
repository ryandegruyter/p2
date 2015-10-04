package be.ryan.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import be.ryan.popularmovies.App;
import be.ryan.popularmovies.R;
import be.ryan.popularmovies.event.FavoriteEvent;
import be.ryan.popularmovies.event.MovieListEvent;
import be.ryan.popularmovies.event.PopularMovieEvent;
import be.ryan.popularmovies.ui.fragment.DetailMovieFragment;
import be.ryan.popularmovies.ui.fragment.MovieListPagerFragment;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity{

    private static final String TAG_MOVIE_LIST_PAGER_FRAGMENT = "fragment_movie_pager";
    private static final String TAG = "MainActivity";
    public String TAG_MOVIE_DETAIL_FRAGMENT = "detail_movie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (App.runsOnTablet) {
            // TODO: 26/09/15 init tablet layout
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list_movies_container, new MovieListPagerFragment(), TAG_MOVIE_LIST_PAGER_FRAGMENT)
                    .commit();
        }
    }

    /**
     * Called when a user clicks on a favorite button so he can add or remove a movie from its favorites
     *
     * @param favoriteEvent
     */
    public void onEvent(FavoriteEvent favoriteEvent) {


    }
    /**
     * Called when a user clicks a movie poster from the list
     * @param movieEvent PopularMovieEvent
     */
    public void onEvent(PopularMovieEvent movieEvent) {
        int containerViewToReplaceId;
        if (App.runsOnTablet) {
            // TODO: 26/09/15 init
//            containerViewToReplaceId = R.id.fragment_detail;
        } else {
//            containerViewToReplaceId = R.id.fragment_list_movies_container;
        }

        containerViewToReplaceId = R.id.fragment_list_movies_container;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewToReplaceId, DetailMovieFragment.newInstance(movieEvent.mMovie), TAG_MOVIE_DETAIL_FRAGMENT)
                .addToBackStack(null)
                .commit();
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
}
