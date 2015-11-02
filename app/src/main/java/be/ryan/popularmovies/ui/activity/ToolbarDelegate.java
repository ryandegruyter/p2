package be.ryan.popularmovies.ui.activity;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.event.BackPressedEvent;
import be.ryan.popularmovies.event.SyncEvent;
import be.ryan.popularmovies.sync.PopMovSyncAdapter;
import de.greenrobot.event.EventBus;

/**
 * Created by ryan on 16.10.15.
 */
public class ToolbarDelegate implements View.OnClickListener {

    boolean isMovieDetailViewActive;
    @DrawableRes int mToolbarNavigationIcon = android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha;;
    final Toolbar mToolbar;
    static ToolbarDelegate instance;

    public static ToolbarDelegate getMainToolbarDelegate(Toolbar toolbar) {
        if (instance == null) {
            instance = new ToolbarDelegate(toolbar);
        }
        return instance;
    }

    public ToolbarDelegate(Toolbar toolbar) {
        mToolbar = toolbar;
        mToolbar.inflateMenu(R.menu.main);
        toolbar.setNavigationOnClickListener(this);
    }

    public void syncState() {
        if (isMovieDetailViewActive) {
            mToolbar.setNavigationIcon(mToolbarNavigationIcon);
        }else {
            mToolbar.setNavigationIcon(null);
        }
    }

    /**
     * Flag to check if the movie detail view is active, if true show a back icon or else
     * dont show any any navigation icon.
     *
     * @param movieDetailViewIsActive
     */
    public void setMovieDetailViewIsActive(boolean movieDetailViewIsActive) {
        this.isMovieDetailViewActive = movieDetailViewIsActive;
    }

    public void setSubtitle(CharSequence subtitle) {
        mToolbar.setSubtitle(subtitle);
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new BackPressedEvent("Toolbar Delegate"));
    }
}
