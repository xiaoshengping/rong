package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.talent.ResumeMusic;
import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/8/19.
 */
public class ResumeDeleteMusicAdapter extends AppBaseAdapter<ResumeMusic>{
    private ViewHodle viewHodle;
    public ResumeDeleteMusicAdapter(List<ResumeMusic> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.resume_dalete_music_layout,null);
            viewHodle=new ViewHodle(convertView) ;
            convertView.setTag(viewHodle);

        }else {
            viewHodle= (ViewHodle) convertView.getTag();

        }
        inti(position);

        return convertView;
    }

    private void inti(int position) {

        viewHodle.showMusicTv.setText(data.get(position).getTitle());

        /*String imagpath= AppUtilsUrl.ImageBaseUrl+data.get(position).getPath();
        Bitmap bitmap=createVideoThumbnail(imagpath, 10, 10);
          viewHodle.showVideoImage.setImageBitmap(bitmap);*/



    }
    private class  ViewHodle{
        @ViewInject(R.id.show_music_tv)
        private TextView showMusicTv;

        public ViewHodle(View view) {
            ViewUtils.inject(this, view);
        }
    }
}
