package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.talent.CommentBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.BitmapUtils;
import com.sina.weibo.sdk.demo.R;

import java.util.List;

/**
 * Created by Administrator on 2015/7/9.
 */
public class ReputationAdapter extends BaseAdapter {

    private Context mContext;
    private ViewHodle viewHodle;
    private LayoutInflater mInflater;
    private List<CommentBean> commentDate;

    public ReputationAdapter(Context context,List<CommentBean> commentDate) {
        this.mInflater = LayoutInflater.from(context);
        this.commentDate = commentDate;
        mContext = context;
    }

    @Override
    public int getCount() {

        int num=commentDate.size();
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
            convertView = mInflater.inflate(R.layout.reputation_list,null);
            viewHodle = new ViewHodle();

            viewHodle.comment_neme_tv = (TextView) convertView.findViewById(R.id.comment_neme_tv);
            viewHodle.comment_time_tv = (TextView) convertView.findViewById(R.id.comment_time_tv);
            viewHodle.comment_body_tv = (TextView) convertView.findViewById(R.id.comment_body_tv);
            viewHodle.head_portrait_iv = (ImageView) convertView.findViewById(R.id.head_portrait_iv);
            convertView.setTag(viewHodle);
        }
        else{
            viewHodle = (ViewHodle)convertView.getTag();
        }
        viewHodle.comment_neme_tv.setText(commentDate.get(position).getCompanyName());
        viewHodle.comment_time_tv.setText(commentDate.get(position).getTime());
        viewHodle.comment_body_tv.setText(commentDate.get(position).getBody());
        BitmapUtils bitmapUtils=new BitmapUtils(mContext);
        bitmapUtils.display(viewHodle.head_portrait_iv, AppUtilsUrl.ImageBaseUrl + commentDate.get(position).getIcon());


        return convertView;
    }

    public class ViewHodle{
        private TextView comment_neme_tv;
        private TextView comment_time_tv;
        private TextView comment_body_tv;
        private ImageView head_portrait_iv;

    }
}
