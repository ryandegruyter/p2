package be.ryan.popularmovies.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ryan on 6/09/15.
 */
public class PopMovAuthenticatorService extends Service {

    private PopMovAuthenticator mPopMovAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mPopMovAuthenticator = new PopMovAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mPopMovAuthenticator.getIBinder();
    }
}
