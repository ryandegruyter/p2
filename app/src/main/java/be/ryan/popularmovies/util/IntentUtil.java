package be.ryan.popularmovies.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by ryan on 15/09/15.
 */
public class IntentUtil {
    public static void watchYoutubeVideo(String id, Context context){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            context.startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            context.startActivity(intent);
        }
    }
}
