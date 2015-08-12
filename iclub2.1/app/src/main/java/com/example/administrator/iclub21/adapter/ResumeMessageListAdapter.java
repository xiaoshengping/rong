package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.ResumeMessageValueBean;
import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/8/12.
 */
public class ResumeMessageListAdapter extends AppBaseAdapter<ResumeMessageValueBean> {

    private ViewHedle viewHedle;
    public ResumeMessageListAdapter(List<ResumeMessageValueBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.resume_message_list_layout,parent,false);
            viewHedle=new ViewHedle(convertView);
            convertView.setTag(viewHedle);

        }else {
            viewHedle= (ViewHedle) convertView.getTag();
        }
        intiView(position);
        return convertView;
    }

    private void intiView(int position) {

        viewHedle.nameTextView.setText("投递通知");
        viewHedle.jodTextView.setText(data.get(position).getPosition());
        viewHedle.dayTextView.setText(data.get(position).getPuttime());


    }


    private class  ViewHedle{
        @ViewInject(R.id.name_text_view)
        private TextView nameTextView;
        @ViewInject(R.id.jod_text_view)
        private TextView jodTextView;
        @ViewInject(R.id.day_text_view)
        private TextView dayTextView;

        public ViewHedle(View view) {
            ViewUtils.inject(this, view);
        }
    }


}
