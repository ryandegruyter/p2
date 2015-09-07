package be.ryan.popularmovies.ui.activity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbMovie;
import be.ryan.popularmovies.sync.PopMovSyncAdapter;
import be.ryan.popularmovies.ui.fragment.DetailMovieFragment;
import be.ryan.popularmovies.ui.fragment.MovieListPagerFragment;
import be.ryan.popularmovies.util.Preferences;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MOVIE_LIST_PAGER_FRAGMENT = "fragment_movie_pager";
    public String TAG_MOVIE_DETAIL_FRAGMENT = "detail_movie";

    private LinearLayout mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: 7/09/15 fix repopulate list when on back from detail view
        if (savedInstanceState == null) {
            PopMovSyncAdapter.syncImmediately(this, new Bundle());
        }

        mContainerView = (LinearLayout) findViewById(R.id.container_main);

        //TODO: Make sure on orientation change fragment is saved
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
        getSupportFragmentManager().saveFragmentInstanceState(getSupportFragmentManager().findFragmentByTag(TAG_MOVIE_LIST_PAGER_FRAGMENT));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(mContainerView.getId(), DetailMovieFragment.newInstance(movie), TAG_MOVIE_DETAIL_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}
