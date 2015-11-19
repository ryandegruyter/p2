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

import static be.ryan.popularmovies.event.BackPressedEvent.*;

/**
 * Created by ryan on 16.10.15.
 */
public class ToolbarDelegate implements View.OnClickListener {

    @DrawableRes int mToolbarNavigationIcon = android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha;;
    final Toolbar mToolbar;
    boolean detailViewIsActive;
    static ToolbarDelegate instance;

    public ToolbarDelegate(Toolbar toolbar) {
        mToolbar = toolbar;
        toolbar.setNavigationOnClickListener(this);
    }

    public void syncState(boolean movieDetailViewIsActive) {
        this.detailViewIsActive = movieDetailViewIsActive;
        syncState();
    }

    public void syncState(){
        if (this.detailViewIsActive) {
            mToolbar.setNavigationIcon(mToolbarNavigationIcon);
        } else {
            mToolbar.setNavigationIcon(null);
        }
    }

    public void setSubtitle(CharSequence subtitle) {
        mToolbar.setSubtitle(subtitle);
    }


    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new BackPressedEvent(ACTION_DETAIL_VIEW_EXIT));
    }
}
