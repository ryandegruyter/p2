package be.ryan.popularmovies.ui.activity;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.Toolbar;

/**
 * Created by ryan on 16.10.15.
 */
public class ToolbarDelegate {
    boolean isMovieDetailViewActive;

    @DrawableRes int mToolbarNavigationIcon;

    final Toolbar mToolbar;

    public ToolbarDelegate(Toolbar toolbar) {
        mToolbar = toolbar;
    }

    public void setBackIcon(@DrawableRes int drawableId){
        mToolbar.setNavigationIcon(drawableId);
    }

    public void syncState() {
        if (isMovieDetailViewActive) {
            mToolbar.setNavigationIcon(mToolbarNavigationIcon);
        }else {
            mToolbar.setNavigationIcon(null);
        }
    }


}
