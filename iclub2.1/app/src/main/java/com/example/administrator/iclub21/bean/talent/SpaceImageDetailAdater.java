package com.example.administrator.iclub21.bean.talent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/6/15.
 */
public class SpaceImageDetailAdater extends BaseAdapter {

    private Context mContext;
    private ViewHodle viewHodle;
    private LayoutInflater mInflater;
    private List<ResumePicture> listImage;
    private int width=0;
    private int height=0;
    private int maxNum=0;

    public SpaceImageDetailAdater(Context context ,List<ResumePicture> list , int width, int height,int maxNum){
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        listImage = list;
        this.width = width;
        this.height = height;
        this.maxNum = maxNum;
    }

    @Override
    public int getCount() {
        if(listImage.size()<4||maxNum!=4){
            maxNum = listImage.size();
        }
        return maxNum;
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
            convertView = mInflater.inflate(R.layout.spacelmage_detail_layout,null);
            viewHodle = new ViewHodle();
            viewHodle.spaceimge_iv = (ImageView)convertView.findViewById(R.id.spaceimge_iv);
            convertView.setTag(viewHodle);
        }
        else{
            viewHodle = (ViewHodle)convertView.getTag();
        }
        viewHodle.spaceimge_iv.setLayoutParams(new LinearLayout.LayoutParams(width,height));
        BitmapUtils bitmapUtils=new BitmapUtils(mContext);
        bitmapUtils.display(viewHodle.spaceimge_iv, AppUtilsUrl.ImageBaseUrl + listImage.get(position).getPath());

        return convertView;
    }

    public class ViewHodle{
        private ImageView spaceimge_iv;
    }
}
