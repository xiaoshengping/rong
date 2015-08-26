package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.recruitment.AreaBean;
import com.jeremy.Customer.R;

/**
 * Created by Administrator on 2015/5/27.
 */
public class SelectedCityOrPositionAdapter extends BaseAdapter{

    private Context mContext;
    private ViewHodle viewHodle;
    private AreaBean areaBean = new AreaBean();
    private LayoutInflater mInflater;
    private int selected;
    private String cityArea;
    private boolean ba = false;

    public SelectedCityOrPositionAdapter(Context context, int sel) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        selected = sel;
    }
    public SelectedCityOrPositionAdapter(Context context, int sel,boolean b) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        selected = sel;
        ba=b;
    }
    public SelectedCityOrPositionAdapter(Context context, int sel, String str) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        selected = sel;
        cityArea = str;
    }

    @Override
    public int getCount() {

        int num=0;
        if(selected == areaBean.PROVINCE){
            num=areaBean.getAreaCount(mContext);
        }else if(selected == areaBean.CITY){
            num=areaBean.getsCityCount(cityArea);
        }else if(selected == areaBean.POSITION){
            num=areaBean.getPositionCount(mContext);
        }

        return num;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.city_list_layout,null);
            viewHodle = new ViewHodle();

            viewHodle.province_ll = (LinearLayout) convertView.findViewById(R.id.province_ll);
            viewHodle.city_ll = (LinearLayout) convertView.findViewById(R.id.city_ll);
            viewHodle.position_ll = (LinearLayout) convertView.findViewById(R.id.position_ll);
            viewHodle.position_tv = (TextView) convertView.findViewById(R.id.position_tv);
            viewHodle.province_tv = (TextView) convertView.findViewById(R.id.province_tv);
            viewHodle.city_tv = (TextView) convertView.findViewById(R.id.city_tv);

//            holder.text = (TextView) convertView.findViewById(R.id.ItemText);
//            holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
            convertView.setTag(viewHodle);
        }
        else{
            viewHodle = (ViewHodle)convertView.getTag();
        }

        if(selected == areaBean.PROVINCE) {
            viewHodle.city_ll.setVisibility(View.GONE);
            viewHodle.province_ll.setVisibility(View.VISIBLE);
            viewHodle.position_ll.setVisibility(View.GONE);
            String s = areaBean.getProvince(position,1);
            viewHodle.province_tv.setText(s);
            if(ba){
                if(position==0) {
                    viewHodle.province_ll.setVisibility(View.GONE);
                }
            }
        }else if(selected == areaBean.CITY){
            viewHodle.province_ll.setVisibility(View.GONE);
            viewHodle.city_ll.setVisibility(View.VISIBLE);
            viewHodle.position_ll.setVisibility(View.GONE);
            String s = areaBean.getCityName(position);
            viewHodle.city_tv.setText(s);
        }else if(selected == areaBean.POSITION){
            String s = areaBean.getPosition(position);
            viewHodle.city_ll.setVisibility(View.GONE);
            if(areaBean.getPositionNum(position)!=-1) {
                viewHodle.province_ll.setVisibility(View.VISIBLE);
                viewHodle.position_ll.setVisibility(View.GONE);
                viewHodle.province_tv.setText(s);
            }else{
                viewHodle.province_ll.setVisibility(View.GONE);
                viewHodle.position_ll.setVisibility(View.VISIBLE);
                viewHodle.position_tv.setText(s);
            }
            if(ba){
                if(position==0) {
                    viewHodle.province_ll.setVisibility(View.GONE);
                }
            }
        }

        return convertView;
    }

    public class ViewHodle{
        private TextView province_tv;
        private LinearLayout province_ll;
        private LinearLayout city_ll;
        private TextView city_tv;
        private LinearLayout position_ll;
        private TextView position_tv;
    }
}
