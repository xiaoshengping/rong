package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.InformationValueBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/6/14.
 */
public class InfomationAdapter extends AppBaseAdapter<InformationValueBean> {

    private ViewHodle viewHodle;
    public InfomationAdapter(List<InformationValueBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.infomation_list_adapter,parent,false);
            viewHodle=new ViewHodle(convertView);
            convertView.setTag(viewHodle);

        }else {
            viewHodle = (ViewHodle) convertView.getTag();
        }

                intiInfomationAdapter(position);

        return convertView;
    }

    private void intiInfomationAdapter(int position) {
        viewHodle.titleTv.setText(data.get(position).getTitle());
        viewHodle.contentTv.setText(data.get(position).getContent());
        viewHodle.timeTv.setText(data.get(position).getPutdate());



    }

    private class  ViewHodle{
        @ViewInject(R.id.infomation_title_tv)
        private TextView titleTv;
        @ViewInject(R.id.infomation_content_tv)
        private TextView contentTv;
        @ViewInject(R.id.infomation_time_tv)
        private TextView  timeTv;

        public ViewHodle(View view) {
            ViewUtils.inject(this,view);


        }
    }
}


