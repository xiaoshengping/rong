package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.artist.ArtistListBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.jeremy.Customer.R;

import java.util.List;

/**
 * Created by Administrator on 2015/5/19.
 */
public class ArtistListAdapter extends AppBaseAdapter<ArtistListBean> {
    private ViewHolde viewHolde;

    public ArtistListAdapter(List<ArtistListBean> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {


         if (convertView==null){
             convertView = LayoutInflater.from(context).inflate(R.layout.artist_list_adapter,null);
             viewHolde=new ViewHolde(convertView);
             convertView.setTag(viewHolde);
         }else {

            viewHolde= (ViewHolde) convertView.getTag();
         }

            initData(position);
        return convertView;
    }

    private void initData(int position) {

//        BitmapDisplayConfig bigPicDisplayConfig = new BitmapDisplayConfig(); // 显示原始图片,不压缩, 尽量不要使用, 图片太大时容易 OOM。
//        bigPicDisplayConfig.setShowOriginal(false);
//        bigPicDisplayConfig.setBitmapConfig(); //设置图片的最大尺寸, 不设置时更具控件属性自适应
//        bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(context));

        BitmapUtils bitmapUtils=new BitmapUtils(context);
        if(data.get(position).getArtistPicture().size()==0) {
            bitmapUtils.display(viewHolde.personPicture, AppUtilsUrl.ImageBaseUrl + data.get(position).getArtistPicture().get(0).getName());
        }
        viewHolde.nameTextV.setText(data.get(position).getName());

    }

    public class ViewHolde{
        @ViewInject(R.id.artist_person_picture)
        private ImageView personPicture;
        @ViewInject(R.id.artist_person_name)
        private TextView nameTextV;

        public ViewHolde(View view) {
            ViewUtils.inject(this,view);
        }
    }
}
