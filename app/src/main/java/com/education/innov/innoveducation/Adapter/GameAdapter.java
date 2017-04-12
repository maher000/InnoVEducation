package com.education.innov.innoveducation.Adapter;

/**
 * Created by maher on 12/04/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.education.innov.innoveducation.R;

public class GameAdapter extends BaseAdapter {
    private Context mContext;
    private String[] title = {
            "Video",
            "Image",
            "Video",
            "Video",
            "Image",
            "Texte"
    };

    // Constructor
    public GameAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return title.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.row_item_game, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.game_cover);
        final TextView tvName = (TextView)convertView.findViewById(R.id.tvNameGame);
        final TextView tvDescription = (TextView)convertView.findViewById(R.id.tvDescriptionGame);

        return convertView;

    }

    // Keep all Images in array
}