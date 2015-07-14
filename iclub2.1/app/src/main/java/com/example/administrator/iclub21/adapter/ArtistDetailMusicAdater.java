package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.artist.ArtistAlbum;
import com.example.administrator.iclub21.bean.talent.MusicActivity;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/6/18.
 */
public class ArtistDetailMusicAdater extends BaseAdapter {

    private Context mContext;
    private ViewHodle viewHodle;
    private LayoutInflater mInflater;
    private List<ArtistAlbum> artistAlbum;
    private AppUtilsUrl auu = new AppUtilsUrl();
    private int num=0;
    private int oldsize=0;


    public ArtistDetailMusicAdater(Context context, List<ArtistAlbum> data) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        artistAlbum = data;
    }


    @Override
    public int getCount() {
        int a = 0;
        for (int i= 0;i<artistAlbum.size();i++){
            a=a+artistAlbum.get(i).getArtistMusic().size();
        }
        return a;
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
            convertView = mInflater.inflate(R.layout.artist_detail_list_music_layout,null);
            viewHodle = new ViewHodle();
            viewHodle.artistalbum_ll = (LinearLayout)convertView.findViewById(R.id.artistalbum_ll);
            viewHodle.albumdate_tv = (TextView)convertView.findViewById(R.id.albumdate_tv);
            viewHodle.albumicon_iv = (ImageView)convertView.findViewById(R.id.albumicon_iv);
            viewHodle.albumname_tv = (TextView)convertView.findViewById(R.id.albumname_tv);
            viewHodle.tips = (TextView)convertView.findViewById(R.id.tips);
            convertView.setTag(viewHodle);
        }
        else{
            viewHodle = (ViewHodle)convertView.getTag();

        }

        num = 0;
        oldsize = 0;

        for (int i = 0;(position-oldsize)>=artistAlbum.get(i).getArtistMusic().size();i++){
            num=i+1;
            oldsize = artistAlbum.get(i).getArtistMusic().size()+oldsize;
        }
//        if(position<(artistAlbum.get(num).getArtistMusic().size())){
////            num = 0;
//        }else{
////            oldsize = oldsize+artistAlbum.get(num).getArtistMusic().size();
//            num ++;
//        }
        if(position-oldsize==0) {
            viewHodle.artistalbum_ll.setVisibility(View.VISIBLE);
            viewHodle.albumdate_tv.setText(artistAlbum.get(num).getAlbumdate());
            viewHodle.albumname_tv.setText(artistAlbum.get(num).getAlbumname());
            BitmapUtils bitmapUtils=new BitmapUtils(mContext);
            bitmapUtils.display(viewHodle.albumicon_iv, AppUtilsUrl.ImageBaseUrl + artistAlbum.get(num).getAlbumicon());
        }else {
            viewHodle.artistalbum_ll.setVisibility(View.GONE);
        }
//        viewHodle.albumname_tv.setText(num + "");
        viewHodle.tips.setText(artistAlbum.get(num).getArtistMusic().get(position-oldsize).getMusicname());
        viewHodle.tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 0;
                oldsize = 0;

                for (int i = 0;(position-oldsize)>=artistAlbum.get(i).getArtistMusic().size();i++){
                    num=i+1;
                    oldsize = artistAlbum.get(i).getArtistMusic().size()+oldsize;
                }
                Intent intent = new Intent(mContext, MusicActivity.class);  //方法1
                intent.putExtra("url",auu.ImageBaseUrl + artistAlbum.get(num).getArtistMusic().get(position-oldsize).getPath());
                intent.putExtra("musicName",artistAlbum.get(num).getArtistMusic().get(position - oldsize).getMusicname());
                intent.putExtras(intent);
                mContext.startActivity(intent);
            }
        });

//            viewHodle.albumicon_iv.setText(artistAlbum.get(position).get);


        return convertView;
    }

    public class ViewHodle{
        private LinearLayout artistalbum_ll;
        private TextView albumdate_tv;
        private ImageView albumicon_iv;
        private TextView albumname_tv;
        private TextView tips;
    }
}
