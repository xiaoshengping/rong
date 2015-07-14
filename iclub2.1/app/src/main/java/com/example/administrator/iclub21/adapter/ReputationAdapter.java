package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 2015/7/9.
 */
public class ReputationAdapter extends BaseAdapter {

    private Context mContext;
    private ViewHodle viewHodle;
    private LayoutInflater mInflater;

    public ReputationAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {

        int num=0;
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
//            convertView = mInflater.inflate(R.layout.city_list_layout,null);
//            viewHodle = new ViewHodle();


//            holder.text = (TextView) convertView.findViewById(R.id.ItemText);
//            holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
//            convertView.setTag(viewHodle);
        }
        else{
//            viewHodle = (ViewHodle)convertView.getTag();
        }


        return convertView;
    }

    public class ViewHodle{

    }
}
