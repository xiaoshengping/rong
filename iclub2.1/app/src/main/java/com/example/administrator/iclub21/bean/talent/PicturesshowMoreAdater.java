package com.example.administrator.iclub21.bean.talent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.BitmapUtils;
import com.sina.weibo.sdk.demo.R;

import java.util.List;

/**
 * Created by Administrator on 2015/6/15.
 */
public class PicturesshowMoreAdater extends BaseAdapter {

    private Context mContext;
    private ViewHodle viewHodle;
    private LayoutInflater mInflater;
    private List<ResumePicture> listImage;

    PicturesshowMoreAdater(Context context, List<ResumePicture> list){
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        listImage = list;
    }

    @Override
    public int getCount() {
        return listImage.size();
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
            convertView = mInflater.inflate(R.layout.picturesshowmore_list_layout,null);
            viewHodle = new ViewHodle();

            viewHodle.picturesshowmore1_ib = (ImageView) convertView.findViewById(R.id.picturesshowmore1_ib);
//            viewHodle.picturesshowmore2_ib = (ImageButton) convertView.findViewById(R.id.picturesshowmore2_ib);
//            viewHodle.picturesshowmore3_ib = (ImageButton) convertView.findViewById(R.id.picturesshowmore3_ib);

//            ViewTreeObserver vto2 = viewHodle.picturesshowmore1_ib.getViewTreeObserver();
//            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    viewHodle.picturesshowmore1_ib.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    convertView.setLayoutParams(new List.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,viewHodle.picturesshowmore1_ib.getWidth()));
////                    viewHodle.picturesshowmore1_ib.setLayoutParams(new LinearLayout.LayoutParams(viewHodle.picturesshowmore1_ib.getWidth(), viewHodle.picturesshowmore1_ib.getWidth()));
////                    viewHodle.picturesshowmore2_ib.setLayoutParams(new LinearLayout.LayoutParams(viewHodle.picturesshowmore1_ib.getWidth(), viewHodle.picturesshowmore1_ib.getWidth()));
////                    viewHodle.picturesshowmore3_ib.setLayoutParams(new LinearLayout.LayoutParams(viewHodle.picturesshowmore1_ib.getWidth(), viewHodle.picturesshowmore1_ib.getWidth()));
//                }
//            });


            convertView.setTag(viewHodle);
        }
        else{
            viewHodle = (ViewHodle)convertView.getTag();
        }

        BitmapUtils bitmapUtils=new BitmapUtils(mContext);
//        viewHodle.picturesshowmore1_ib.setVisibility(View.INVISIBLE);
//        viewHodle.picturesshowmore2_ib.setVisibility(View.INVISIBLE);
//        viewHodle.picturesshowmore3_ib.setVisibility(View.INVISIBLE);
//        if((listImage.size()-position*3)>3) {
//            if (listImage.size() % 3 == 1) {
                bitmapUtils.display(viewHodle.picturesshowmore1_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(position).getPath());
//                viewHodle.picturesshowmore1_ib.setVisibility(View.VISIBLE);
//
//            }
//            if (listImage.size() % 3 == 2) {
//                bitmapUtils.display(viewHodle.picturesshowmore1_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(0).getPath());
//                viewHodle.picturesshowmore1_ib.setVisibility(View.VISIBLE);
//                bitmapUtils.display(viewHodle.picturesshowmore2_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(1).getPath());
//                viewHodle.picturesshowmore2_ib.setVisibility(View.VISIBLE);
//            }
//            if (listImage.size() % 3 == 0) {
//                bitmapUtils.display(viewHodle.picturesshowmore1_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(position*3+0).getPath());
//                viewHodle.picturesshowmore1_ib.setVisibility(View.VISIBLE);
//                bitmapUtils.display(viewHodle.picturesshowmore2_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(position*3+1).getPath());
//                viewHodle.picturesshowmore2_ib.setVisibility(View.VISIBLE);
//                bitmapUtils.display(viewHodle.picturesshowmore3_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(position*3+2).getPath());
//                viewHodle.picturesshowmore3_ib.setVisibility(View.VISIBLE);
//            }
//        }else {
//            bitmapUtils.display(viewHodle.picturesshowmore1_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(position*3+0).getPath());
//            viewHodle.picturesshowmore1_ib.setVisibility(View.VISIBLE);
//            bitmapUtils.display(viewHodle.picturesshowmore2_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(1).getPath());
//            viewHodle.picturesshowmore2_ib.setVisibility(View.VISIBLE);
//            bitmapUtils.display(viewHodle.picturesshowmore3_ib, AppUtilsUrl.ImageBaseUrl + listImage.get(2).getPath());
//            viewHodle.picturesshowmore3_ib.setVisibility(View.VISIBLE);
//        }

        return convertView;
    }

    public class ViewHodle{
        private ImageView picturesshowmore1_ib;//,picturesshowmore2_ib,picturesshowmore3_ib;
    }
}
