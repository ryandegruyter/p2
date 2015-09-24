package be.ryan.popularmovies.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.provider.PopularMoviesContract;

/**
 * Created by ryan on 22/09/15.
 */
public class TestActivity extends AppCompatActivity {
    FrameLayout mFrameLayout = null;
    Button mButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        mFrameLayout = (FrameLayout) findViewById(R.id.container_test_activity);
        mButton = (Button) findViewById(R.id.btnSync);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().query(Uri.withAppendedPath(PopularMoviesContract.MovieEntry.getPopularMoviesUri(), PopularMoviesContract.PATH_FLAG_REMOTE), null, null, null, null);
            }
        });
//        getSupportFragmentManager().beginTransaction()
//                .replace(mFrameLayout.getId(), ListMovieFragment.newInstance()).commit();
    }

}
