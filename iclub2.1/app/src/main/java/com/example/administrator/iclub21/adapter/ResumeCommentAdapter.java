package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.ResumeCommentValueBean;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/7/23.
 */
public class ResumeCommentAdapter extends AppBaseAdapter<ResumeCommentValueBean> {
    private ViewHodle viewHodle;

    public ResumeCommentAdapter(List<ResumeCommentValueBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.resume_comment_adapter_layout,null);
           viewHodle=new ViewHodle(convertView);
            convertView.setTag(viewHodle);

        }else {
            viewHodle= (ViewHodle) convertView.getTag();

        }
          intiView(position);


        return convertView;
    }

    private void intiView(int position) {
        MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl+data.get(position).getIcon(),viewHodle.commentIcon,MyAppliction.RoundedOptionsOne);
        viewHodle.commentCompanyName.setText(data.get(position).getNickname());
        viewHodle.timeTv.setText(data.get(position).getTime());
        viewHodle.bodyTv.setText(data.get(position).getBody());


    }

    private class  ViewHodle {
       @ViewInject(R.id.comment_icon_iv)
       private ImageView commentIcon;
       @ViewInject(R.id.comment_company_name_tv)
       private TextView commentCompanyName;
       @ViewInject(R.id.time_tv)
       private TextView timeTv;
       @ViewInject(R.id.body_tv)
       private TextView bodyTv;

       public ViewHodle(View view) {
           ViewUtils.inject(this,view);



       }
   }



}
