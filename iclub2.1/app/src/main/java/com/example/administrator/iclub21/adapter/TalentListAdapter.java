package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.talent.TalentValueBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import java.util.List;

/**
 * Created by Administrator on 2015/5/20.
 */
public class TalentListAdapter extends AppBaseAdapter<TalentValueBean> {
    private ViewHodle viewHodle;
    public TalentListAdapter(List<TalentValueBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.talent_list_layout,parent,false);
            viewHodle=new ViewHodle(convertView);
            convertView.setTag(viewHodle);
        }else {
            viewHodle= (ViewHodle) convertView.getTag();
        }
        init(position);

        return convertView;
    }

    private void init(int position) {
//        MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl+data.get(position).getUsericon(),viewHodle.imageIcon,MyAppliction.RoundedOptions);
        BitmapUtils bitmapUtils=new BitmapUtils(context);
        bitmapUtils.display(viewHodle.imageIcon, AppUtilsUrl.ImageBaseUrl + data.get(position).getUsericon());
        viewHodle.talentName.setText(data.get(position).getResumeZhName());
        viewHodle.talentAge.setText(data.get(position).getResumeAge()+"岁");
        if (data.get(position).getResumeSex()==0){
            viewHodle.imageSex.setBackgroundResource(R.mipmap.man_icon);

        }else if (data.get(position).getResumeSex()==1){
            viewHodle.imageSex.setBackgroundResource(R.mipmap.girl_icon);

        }
        viewHodle.talentCity.setText(data.get(position).getResumeWorkPlace());
        viewHodle.talentJob.setText(data.get(position).getResumeName() + "师");
        if(data.get(position).getResumeLabel().equals("HOT")) {
            viewHodle.talent_hot.setVisibility(View.VISIBLE);
        }else {
            viewHodle.talent_hot.setVisibility(View.GONE);
        }


    }

    public class ViewHodle{
        @ViewInject(R.id.talent_list_image)
        private ImageView imageIcon;
        @ViewInject(R.id.talent_name)
        private TextView talentName;
        @ViewInject(R.id.talent_age_list)
        private TextView talentAge;
        @ViewInject(R.id.talent_sex)
        private ImageView imageSex;
        @ViewInject(R.id.talent_city)
        private TextView talentCity;
        @ViewInject(R.id.talent_job)
        private TextView talentJob;
        @ViewInject(R.id.talent_hot)
        private ImageView talent_hot;

        public ViewHodle(View view) {
            super();
            ViewUtils.inject(this,view);
        }
    }
}
