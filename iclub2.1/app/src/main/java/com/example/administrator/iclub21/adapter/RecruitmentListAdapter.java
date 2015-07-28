package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.RatingBarStar;
import com.example.administrator.iclub21.bean.recruitment.RecruitmentListBean;
import com.example.administrator.iclub21.fragment.RecruitmentFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/5/26.
 */
public class RecruitmentListAdapter extends AppBaseAdapter<RecruitmentListBean> {
    private ViewHodle viewHodle;
    public boolean floatingCollar = false;
    public List<RecruitmentListBean> dataes;
    private RatingBarStar rbs;
    public RecruitmentListAdapter(List<RecruitmentListBean> data, Context context) {
        super(data, context);
        dataes = data;
        rbs = new RatingBarStar();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    private RecruitmentFragment rf = new RecruitmentFragment();

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.recruitment_list_layout, parent, false);
                viewHodle = new ViewHodle(convertView);
                convertView.setTag(viewHodle);
            } else {
                viewHodle = (ViewHodle) convertView.getTag();
            }
//        if(position==0) {
////
//                viewHodle.recruiment_list_title.setVisibility(View.VISIBLE);
////            }else {
////            if(floatingCollar)
////                viewHodle.recruiment_list_title.setVisibility(View.INVISIBLE);
//////            }
//            viewHodle.recruitment_list_ll.setVisibility(View.GONE);

//            rf.abc(viewHodle.selected_city);

//            //选择城市点击事件
//        viewHodle.selected_city.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "sasdfa", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(rf.getActivity(), SelectedCityOrPositionActivity.class);  //方法1
//                rf.startActivity(intent);
//                rf.getActivity().overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_not);
//            }
//
//
//        });

//        }else{
//            viewHodle.recruiment_list_title.setVisibility(View.GONE);
//            viewHodle.recruitment_list_ll.setVisibility(View.VISIBLE);
            init(position);
//        }


        return convertView;
    }

    private void init(int position) {

        String pattern=" ";
        Pattern pat=Pattern.compile(pattern);
        final String[] rs = pat.split(dataes.get(position).getPuttime());

        viewHodle.recruitment_position.setText(dataes.get(position).getPosition());
        viewHodle.recruitment_puttime.setText(rs[0]);
        viewHodle.recruitment_companyName.setText(dataes.get(position).getCompanyName());
        if(data.get(position).getLabel().equals("")) viewHodle.recruitment_label.setVisibility(View.INVISIBLE);
        viewHodle.recruitment_workPlace.setText(dataes.get(position).getWorkPlace());
//        viewHodle.recruitment_star.setRating(dataes.get(position).getStar());
        rbs.getRatingBarStar(context,viewHodle.star_ll,dataes.get(position).getStar());

//

    }

    public class ViewHodle{
        @ViewInject(R.id.recruitment_position)
        private TextView recruitment_position;
        @ViewInject(R.id.recruitment_puttime)
        private TextView recruitment_puttime;
        @ViewInject(R.id.recruitment_companyName)
        private TextView recruitment_companyName;
        @ViewInject(R.id.recruitment_label)
        private TextView recruitment_label;
        @ViewInject(R.id.recruitment_workPlace)
        private TextView recruitment_workPlace;
        @ViewInject(R.id.star_ll)
        private LinearLayout star_ll;
//        @ViewInject(R.id.recruitment_star)
//        private RatingBar recruitment_star;
//        @ViewInject(R.id.recruiment_list_title)
//        private LinearLayout recruiment_list_title;
        @ViewInject(R.id.recruitment_list_ll)
        private LinearLayout recruitment_list_ll;
        @ViewInject(R.id.selected_city)
        private Button selected_city;

        public ViewHodle(View view) {
            super();
            ViewUtils.inject(this, view);
        }
    }
}
