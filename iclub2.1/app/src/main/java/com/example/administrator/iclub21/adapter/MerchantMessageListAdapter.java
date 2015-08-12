package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.MerchantMessageValueBean;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/8/12.
 */
public class MerchantMessageListAdapter extends AppBaseAdapter<MerchantMessageValueBean> {
          private ViewHedle viewHodle;

    public MerchantMessageListAdapter(List<MerchantMessageValueBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.merchant_message_list_adapter,parent,false);
            viewHodle=new ViewHedle(convertView);
            convertView.setTag(viewHodle);

        }else {
            viewHodle= (ViewHedle) convertView.getTag();
        }
       intiView(position);

        return convertView;
    }

    private void intiView(int position) {
        MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + data.get(position).getPath(), viewHodle.touXiangIv, MyAppliction.RoundedOptions);
        //Log.e("1111111", data.get(0).getPath());
        viewHodle.nameTextView.setText(data.get(position).getName());
        viewHodle.jodTextView.setText(data.get(position).getPosition());
        viewHodle.dayTextView.setText(data.get(position).getPuttime());

    }

    private class  ViewHedle{
        @ViewInject(R.id.touxiang_iv)
        private ImageView touXiangIv;
        @ViewInject(R.id.name_text_view)
        private TextView nameTextView;
        @ViewInject(R.id.jod_text_view)
        private TextView jodTextView;
        @ViewInject(R.id.day_text_view)
        private TextView dayTextView;

        public ViewHedle(View view) {
            ViewUtils.inject(this,view);
        }
    }

}
