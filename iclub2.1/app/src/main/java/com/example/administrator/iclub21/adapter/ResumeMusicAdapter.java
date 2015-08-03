package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.talent.ResumeMusic;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import static com.jeremy.Customer.R.id;
import static com.jeremy.Customer.R.layout;

/**
 * Created by xiaoshengping on 2015/7/16.
 */
public class ResumeMusicAdapter extends AppBaseAdapter<ResumeMusic> {
    private ViewHodle viewHodle;

    public ResumeMusicAdapter(List<ResumeMusic> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(layout.resume_music_adapter_layout,null);
            viewHodle=new ViewHodle(convertView) ;
            convertView.setTag(viewHodle);

        }else {
            viewHodle= (ViewHodle) convertView.getTag();

        }
      inti(position);

        return convertView;
    }

    private void inti(int position) {
        viewHodle.showMusicTextView.setText(data.get(position).getTitle());



    }





    private class  ViewHodle{
        @ViewInject(id.show_music_tv)
        private TextView showMusicTextView;


        public ViewHodle(View view) {
            ViewUtils.inject(this,view);
        }
    }


}
