package be.ryan.popularmovies.ui.activity;

import android.support.v7.widget.Toolbar;

/**
 * Created by Ryan on 02/11/2015.
 */
public class MainToolbar extends ToolbarDelegate {

    static CharSequence subTitle;
    static boolean isDetailViewActive;

    public static MainToolbar getMainToolbarDelegate(Toolbar toolbar) {
        if (instance == null) {
            instance = new MainToolbar(toolbar);
        }
        return (MainToolbar) instance;
    }

    private MainToolbar() {
        super(null);
    }

    private MainToolbar(Toolbar toolbar) {
        super(toolbar);
    }

    @Override
    public void syncState(boolean movieDetailViewIsActive) {
        super.syncState(movieDetailViewIsActive);
        isDetailViewActive = movieDetailViewIsActive;
    }

    @Override
    public void syncState() {
        if (isDetailViewActive) {
            mToolbar.setNavigationIcon(mToolbarNavigationIcon);
        } else {
            mToolbar.setNavigationIcon(null);
        }
        setSubtitle(subTitle);

    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        super.setSubtitle(subtitle);
        subTitle = subtitle;
    }
}
