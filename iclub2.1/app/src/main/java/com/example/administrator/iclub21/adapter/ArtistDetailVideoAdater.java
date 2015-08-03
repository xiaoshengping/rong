package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.artist.ArtistMovie;
import com.example.administrator.iclub21.url.AppUtilsUrl;

import java.util.List;

import static com.jeremy.Customer.R.id;
import static com.jeremy.Customer.R.layout;

/**
 * Created by Administrator on 2015/6/18.
 */
public class ArtistDetailVideoAdater extends BaseAdapter {

    private Context mContext;
    private ViewHodle viewHodle;
    private LayoutInflater mInflater;
    private List<ArtistMovie> artistMovie;
    private AppUtilsUrl auu = new AppUtilsUrl();

    public ArtistDetailVideoAdater(Context context,List<ArtistMovie> data) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        artistMovie = data;
    }


    @Override
    public int getCount() {
        return artistMovie.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(layout.artist_detail_list_video_layout,null);
            viewHodle = new ViewHodle();
            viewHodle.btn_play_ib = (ImageButton)convertView.findViewById(id.btn_play_ib);
            viewHodle.video_name_tv = (TextView)convertView.findViewById(id.video_name_tv);
            convertView.setTag(viewHodle);
        }
        else{
            viewHodle = (ViewHodle)convertView.getTag();
        }
        viewHodle.video_name_tv.setText("表演视频《"+artistMovie.get(position).getMoviename()+"》");
        viewHodle.btn_play_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(auu.ImageBaseUrl + artistMovie.get(position).getPath()), "video/mp4");
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHodle{
        private ImageButton btn_play_ib;
        private TextView video_name_tv;
    }
}
