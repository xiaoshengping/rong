package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.MerchantInviteValueBean;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/7/10.
 */
public class MerchantInviteListAdapter extends AppBaseAdapter<MerchantInviteValueBean>{

       private ViewHodle viewHodle;



    public MerchantInviteListAdapter(List<MerchantInviteValueBean> data, Context context) {
        super(data, context);


    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.merchant_invite_list_layout,parent,false);
            viewHodle=new ViewHodle(convertView);
            convertView.setTag(viewHodle);
        }else {
            viewHodle= (ViewHodle) convertView.getTag();
        }

          inti(position);
        return convertView;
    }

    private void inti(int position) {
        MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + data.get(position).getInviteResume().getUsericon(), viewHodle.talentImageIv, MyAppliction.RoundedOptions);
        viewHodle.talentNameTv.setText(data.get(position).getInviteResume().getResumeZhName());
        viewHodle.talentTime.setText(data.get(position).getTripTime());
        viewHodle.talentJobTv.setText(data.get(position).getInviteResume().getResumeJobName());
        String status=data.get(position).getBeStatus();
        if (status.equals("0")){
            viewHodle.talentStateTv.setText("new");
            viewHodle.talentStateTv.setTextColor(context.getResources().getColor(R.color.textColor9a4274));
            viewHodle.talentStateTv.setBackgroundResource(R.mipmap.new_icon);

        }else if (status.equals("2")){
            viewHodle.talentStateTv.setText("拒绝");
            viewHodle.talentStateTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHodle.talentStateTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("6")){
            viewHodle.talentStateTv.setText("过期");
            viewHodle.talentStateTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHodle.talentStateTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("5")){
            viewHodle.talentStateTv.setText("完成");
            viewHodle.talentStateTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHodle.talentStateTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("1")){
            viewHodle.talentStateTv.setText("接受");
            viewHodle.talentStateTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHodle.talentStateTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("3")){
            viewHodle.talentStateTv.setText("成功");
            viewHodle.talentStateTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHodle.talentStateTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("4")){
            viewHodle.talentStateTv.setText("失败");
            viewHodle.talentStateTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHodle.talentStateTv.setBackgroundResource(R.mipmap.new_one_icon);
        }

        if ((data.get(position).getInviteResume().getResumeSex())==0){
            viewHodle.talentSexIv.setBackgroundResource(R.mipmap.man_icon);
        }else if ((data.get(position).getInviteResume().getResumeSex())==1){
            viewHodle.talentSexIv.setBackgroundResource(R.mipmap.girl_icon);

        }
        viewHodle.talentAgeTv.setText(data.get(position).getInviteResume().getResumeAge()+"岁");



    }

    private class  ViewHodle{
        @ViewInject(R.id.talent_list_image)
        private ImageView talentImageIv;
        @ViewInject(R.id.talent_name)
        private TextView talentNameTv;
        @ViewInject(R.id.talent_sex)
        private ImageView talentSexIv;
        @ViewInject(R.id.talent_age_list)
        private TextView talentAgeTv;
        @ViewInject(R.id.talent_job)
        private TextView talentJobTv;
        @ViewInject(R.id.talent_time)
        private TextView talentTime;
        @ViewInject(R.id.talent_state_tv)
        private TextView talentStateTv;

        public ViewHodle(View view) {
            ViewUtils.inject(this,view);

        }
    }

}
