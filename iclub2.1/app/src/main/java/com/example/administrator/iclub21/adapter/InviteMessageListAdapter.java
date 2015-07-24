package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.InviteMessgaeListValueBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/7/2.
 */
public class InviteMessageListAdapter extends  AppBaseAdapter<InviteMessgaeListValueBean> {
    private ViewHold viewHold;

    public InviteMessageListAdapter(List<InviteMessgaeListValueBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.invite_message_list_adapter_layout,parent,false);
            viewHold=new ViewHold(convertView) ;
           convertView.setTag(viewHold);

        }else {
            viewHold= (ViewHold) convertView.getTag();

        }
        inti(position);
        return convertView;
    }

    private void inti(int position) {
        viewHold.InviteResumeJobNameTv.setText(data.get(position).getInviteResume().getResumeJobName());
       String status=data.get(position).getStatus();
        if (status.equals("0")){
            viewHold.InviteStatusTv.setText("new");
            viewHold.InviteStatusTv.setTextColor(context.getResources().getColor(R.color.textColor9a4274));
            viewHold.InviteStatusTv.setBackgroundResource(R.mipmap.new_icon);

        }else if (status.equals("2")){
            viewHold.InviteStatusTv.setText("拒绝");
            viewHold.InviteStatusTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHold.InviteStatusTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("6")){
            viewHold.InviteStatusTv.setText("过期");
            viewHold.InviteStatusTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHold.InviteStatusTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("5")){
            viewHold.InviteStatusTv.setText("完成");
            viewHold.InviteStatusTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHold.InviteStatusTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("1")){
            viewHold.InviteStatusTv.setText("接受");
            viewHold.InviteStatusTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHold.InviteStatusTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("3")){
            viewHold.InviteStatusTv.setText("成功");
            viewHold.InviteStatusTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHold.InviteStatusTv.setBackgroundResource(R.mipmap.new_one_icon);
        }else if (status.equals("4")){
            viewHold.InviteStatusTv.setText("失败");
            viewHold.InviteStatusTv.setTextColor(context.getResources().getColor(R.color.textColor2f967a));
            viewHold.InviteStatusTv.setBackgroundResource(R.mipmap.new_one_icon);
        }
        viewHold.inviteTripTimeTv.setText(data.get(position).getTripTime());
        viewHold.InviteJobCategoryTv.setText(data.get(position).getInviteResume().getResumeWorkPlace());
        viewHold.InviteCompanyNameTv.setText(data.get(position).getInvitePerson().getBEcompanyName());
    }

    private class ViewHold {
        @ViewInject(R.id.resumeJobName_invite_tv)
        private TextView InviteResumeJobNameTv;
        @ViewInject(R.id.invite_status_tv)
        private TextView InviteStatusTv;
        @ViewInject(R.id.invite_tripTime_tv)
        private TextView inviteTripTimeTv;
        @ViewInject(R.id.resumeJobCategory_invite_tv)
        private TextView InviteJobCategoryTv;
        @ViewInject(R.id.BEcompanyName_tv)
        private TextView InviteCompanyNameTv;

        public ViewHold(View view) {
            ViewUtils.inject(this,view);


        }
    }

}
