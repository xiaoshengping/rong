package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.RecruitmentHistoryValueBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/6/17.
 */
public class RecruitmentHistoryAdapter extends AppBaseAdapter<RecruitmentHistoryValueBean> {
    private ViewHole viewHole;


    public RecruitmentHistoryAdapter(List<RecruitmentHistoryValueBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
         if (convertView==null) {
             convertView = LayoutInflater.from(context).inflate(R.layout.recruitment_history_adapter_layout, parent, false);
              viewHole=new ViewHole(convertView);
             convertView.setTag(viewHole);

         }else {
             viewHole= (ViewHole) convertView.getTag();

         }
        intiData(position);
        return convertView;
    }

    private void intiData(int position) {
        viewHole.recuitmentPositionTv.setText(data.get(position).getPosition());
        viewHole.recruitmentTimeTv.setText(data.get(position).getPuttime());
        viewHole.recruitmentCompanyNameTv.setText(data.get(position).getCompanyName());
        viewHole.recuitmentAddressTv.setText(data.get(position).getWorkPlace());



    }

    private class ViewHole{
        @ViewInject(R.id.recruiment_time_tv)
        private TextView recruitmentTimeTv;
        @ViewInject(R.id.recruitment_company_name)
        private TextView recruitmentCompanyNameTv;
        @ViewInject(R.id.recruiment_address_tv)
        private TextView recuitmentAddressTv;
        @ViewInject(R.id.recruiment_position_tv)
        private TextView recuitmentPositionTv;

        public ViewHole(View view) {

            ViewUtils.inject(this,view);
        }
    }
}
