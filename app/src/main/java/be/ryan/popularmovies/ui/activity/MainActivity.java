package be.ryan.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import be.ryan.popularmovies.App;
import be.ryan.popularmovies.R;
import be.ryan.popularmovies.ui.fragment.MovieListPagerFragment;

public class MainActivity extends AppCompatActivity{

    private static final String TAG_MOVIE_LIST_PAGER_FRAGMENT = "fragment_movie_pager";
    private static final String TAG = "MainActivity";
    public String TAG_MOVIE_DETAIL_FRAGMENT = "detail_movie";

    private LinearLayout mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainerView = (LinearLayout) findViewById(R.id.container_main);

        if (App.runsOnTablet) {

        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mContainerView.getId(), new MovieListPagerFragment(), TAG_MOVIE_LIST_PAGER_FRAGMENT)
                    .commit();
        }
    }

}
