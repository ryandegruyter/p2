package be.ryan.debug;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ryan on 23/09/15.
 */
public class Toaster {
    public static void Show (Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
