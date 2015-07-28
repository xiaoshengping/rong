package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.ResumeValueBean;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/6/11.
 */
public class ResumeListAdapter  extends AppBaseAdapter<ResumeValueBean>{

    private  ViewHolde viewHolde;

    public ResumeListAdapter(List<ResumeValueBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.resume_list_adapter, parent, false);
             viewHolde=new ViewHolde(convertView);
            convertView.setTag(viewHolde);
        }else {
          viewHolde= (ViewHolde) convertView.getTag();

        }
        inti(position);

        return convertView;
    }

    private void inti(int position) {
        MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl+data.get(position).getUsericon(),viewHolde.usericonIv,MyAppliction.RoundedOptions);
        viewHolde.resumeJobNameTv.setText(data.get(position).getResumeJobName());
        viewHolde.createTimeTv.setText("创建日期: "+data.get(position).getCreateTime());
        viewHolde.updateTimeTv.setText("更新日期: "+data.get(position).getUpdateTime());



    }

    private class  ViewHolde{
        @ViewInject(R.id.resume_list_iv)
        private ImageView usericonIv;
        @ViewInject(R.id.resume_JobName_tv)
        private TextView resumeJobNameTv;
         @ViewInject(R.id.createTime_tv)
        private TextView createTimeTv;
         @ViewInject(R.id.updateTime_tv)
        private TextView updateTimeTv;

        public ViewHolde(View view) {
            ViewUtils.inject(this,view);
        }
    }

}
