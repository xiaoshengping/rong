package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.talent.ResumeMovie;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xiaoshengping on 2015/7/16.
 */
public class ResumeVideoAdapter extends AppBaseAdapter<ResumeMovie> {
    private ViewHodle viewHodle;

    public ResumeVideoAdapter(List<ResumeMovie> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.resume_video_adapter_layout,null);
            viewHodle=new ViewHodle(convertView) ;
            convertView.setTag(viewHodle);

        }else {
            viewHodle= (ViewHodle) convertView.getTag();

        }
      inti(position);

        return convertView;
    }

    private void inti(int position) {
        String imagpath= AppUtilsUrl.ImageBaseUrl+data.get(position).getPath();
        Bitmap bitmap=createVideoThumbnail(imagpath, 10, 10);
          viewHodle.showVideoImage.setImageBitmap(bitmap);



    }
    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }




    private class  ViewHodle{
        @ViewInject(R.id.show_video_button_tv)
        private TextView showVideoTextView;
        @ViewInject(R.id.show_video_imageView)
        private ImageView showVideoImage;

        public ViewHodle(View view) {
            ViewUtils.inject(this,view);
        }
    }


}
