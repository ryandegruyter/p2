package be.ryan.popularmovies.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import be.ryan.popularmovies.R;
import be.ryan.popularmovies.domain.TmdbVideosResponse;
import be.ryan.popularmovies.util.IntentUtil;

/**
 * Created by Ryan on 19/11/2015.
 */
public class TrailerListAdapter extends BaseAdapter implements View.OnClickListener {
    private final Context context;
    TmdbVideosResponse tmdbVideosResponse;

    public TrailerListAdapter(Context context, TmdbVideosResponse tmdbVideosResponse) {
        this.tmdbVideosResponse = tmdbVideosResponse;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tmdbVideosResponse.videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return tmdbVideosResponse.videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_selectable_list_item, null);
            convertView.setOnClickListener(this);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(tmdbVideosResponse.getVideoList().get(position).getName());
        convertView.setTag(R.string.video_key, tmdbVideosResponse.getVideoList().get(position).getKey());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag(R.string.video_key) != null) {
            IntentUtil.watchYoutubeVideo((String) v.getTag(R.string.video_key), v.getContext());
        }
    }
}
