package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.iclub21.gif.CommonUtil;
import com.example.administrator.iclub21.gif.GifHelper;
import com.jeremy.Customer.R;

import java.io.FileInputStream;
import java.io.InputStream;


public class AboutFunctionActivity extends ActionBarActivity {


    private PlayGifTask mGifTask;
    ImageView iv;
    GifHelper.GifFrame[] frames;
    FileInputStream fis = null;

    private TextView nextTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // setContentView(R.layout.activity_about_function);
         nextTv=new TextView(this);
        nextTv.setText("下一步");
        nextTv.setTextSize(18);
        iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER);
        setContentView(iv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //对Gif图片进行解码
        final InputStream fis = getResources().openRawResource(R.mipmap.iclub_ydao_icon_a);
        frames = CommonUtil.getGif(fis);
        mGifTask = new PlayGifTask(iv, frames);
        mGifTask.startTask();
        Thread th=new Thread(mGifTask);
        th.start();
        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AboutFunctionActivity.this,IclubAboutActivity.class);
                startActivityForResult(intent,19);
            }
        });











    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mGifTask) mGifTask.stopTask();

    }


    //用来循环播放Gif每帧图片
    private class PlayGifTask implements Runnable {
        int i = 0;
        ImageView iv;
        GifHelper.GifFrame[] frames;
        int framelen,oncePlayTime=0;

        public PlayGifTask(ImageView iv, GifHelper.GifFrame[] frames) {
            this.iv = iv;
            this.frames = frames;

            int n=0;
            framelen=frames.length;
            while(n<framelen){
                oncePlayTime+=frames[n].delay;
                n++;
            }
            Log.d("msg", "playTime= " + oncePlayTime);  //Gif图片单次播放时长

        }

        Handler h2=new Handler(){
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case 1:
                        iv.setImageBitmap((Bitmap)msg.obj);
                        break;
                }
            };
        };
        @Override
        public void run() {
            if (!frames[i].image.isRecycled()) {
                //      iv.setImageBitmap(frames[i].image);
                Message m=Message.obtain(h2, 1, frames[i].image);
                m.sendToTarget();
            }
            iv.postDelayed(this, frames[i++].delay);
            i %= framelen;
        }

        public void startTask() {
            iv.post(this);
        }

        public void stopTask() {
            if(null != iv) iv.removeCallbacks(this);
            iv = null;
            if(null != frames) {
                for(GifHelper.GifFrame frame : frames) {
                    if(frame.image != null && !frame.image.isRecycled()) {
                        frame.image.recycle();
                        frame.image = null;
                    }
                }
                frames = null;
                //      mGifTask=null;
            }
        }
    }





















}
