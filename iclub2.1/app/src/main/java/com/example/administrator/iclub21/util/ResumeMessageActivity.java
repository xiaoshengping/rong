package com.example.administrator.iclub21.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.ResumeValueBean;
import com.example.administrator.iclub21.http.ImageUtil;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.view.WordWrapView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ResumeMessageActivity extends ActionBarActivity implements View.OnClickListener {
    //头部
    @ViewInject(R.id.message_return_tv)
    private TextView messsageReturnTv;
    @ViewInject(R.id.message_comment_tv)
    private TextView messageCommentTv;
    @ViewInject(R.id.message_save_tv)
    private TextView messageSaveTv;

    //自我介绍
    @ViewInject(R.id.epenrience_particular)
    private LinearLayout particularLayout;
    @ViewInject(R.id.onself_base)
    private TextView onselfBase;
    @ViewInject(R.id.oneself_experience)
    private TextView oneselfExperience;
    @ViewInject(R.id.resume_info_et)
    private EditText resumeInfo;

    //工作经历
    @ViewInject(R.id.work_experience_particular)
    private  LinearLayout workExperLayout;
    @ViewInject(R.id.base_work)
    private  TextView baseWork;
    @ViewInject(R.id.work_performance)
    private TextView workPerformance;
    @ViewInject(R.id.resumeWorkExperience_et)
    private EditText resumeWork;

    //图片介绍
    @ViewInject(R.id.add_image_layout)
    private LinearLayout addImageLayout;
    @ViewInject(R.id.add_uplaod_image_layout)
    private RelativeLayout addUplaodImageLayout;
    private String userPicturePath=null;
    @ViewInject(R.id.show_add_layout)
    private LinearLayout showAddPictureLayout;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());
    private File screenshotFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());
    private  RelativeLayout relativeLayout;

    //视频展示
    @ViewInject(R.id.add_video_layout)
    private LinearLayout addVideoLayout;
    @ViewInject(R.id.upload_video_layout)
    private RelativeLayout uploadVideo;
    @ViewInject(R.id.video_view)
    private VideoView videoView;
    @ViewInject(R.id.action_video_bt)
    private Button actionVideo;
    @ViewInject(R.id.thumbnail_Iv)
    private  ImageView videoThumbnail;
    @ViewInject(R.id.delete_video_bt)
    private Button deleteBt;
    private File videoFile;
    @ViewInject(R.id.video_xshi_layout)
    private RelativeLayout showVideoImage;
    private  String videoPath=null;

    //音乐
    @ViewInject(R.id.add_music_layout)
    private LinearLayout addMusicLayout;
    @ViewInject(R.id.add_uplaod_music_layout)
    private RelativeLayout addUplaodMusicTv;
    private String musicPath;
    @ViewInject(R.id.music_view_wordwrap)
    private WordWrapView musicWordWrapView;

    //数据
    private Intent intent;
    private String data;
    private  ResumeValueBean resumeValueBean;
    private Display display;
    private WordWrapView wordWrapView;
    private WordWrapView videowordWrapView;
    private HttpUtils httpUtils;
    private RequestParams requestParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_message);
        ViewUtils.inject(this);
        inti();
       

    }

    private void inti() {
        initView();
        intiInfoData();


        
    }
    private void initView() {
        wordWrapView = (WordWrapView) this.findViewById(R.id.view_wordwrap);
       // videowordWrapView=(WordWrapView) this.findViewById(R.id.video_view_wordwrap);
        messsageReturnTv.setOnClickListener(this);
        messageSaveTv.setOnClickListener(this);
        addUplaodImageLayout.setOnClickListener(this);
        uploadVideo.setOnClickListener(this);
        actionVideo.setOnClickListener(this);
        addUplaodMusicTv.setOnClickListener(this);
        httpUtils=new HttpUtils();
        requestParams=new RequestParams();


    }

    private void intiInfoData() {
         intent=getIntent();
        data= intent.getStringExtra("caseData");
        resumeValueBean= (ResumeValueBean) intent.getSerializableExtra("resumeInfoData");

        if (data.equals("oneself")){
           particularLayout.setVisibility(View.VISIBLE);
            onselfBase.setText("1.可填写个人的基本介绍");
            oneselfExperience.setText("2.可填写个人职业的介绍");
            messageCommentTv.setText("自我介绍");
            resumeInfo.setText(resumeValueBean.getResumeInfo());


        }else if (data.equals("work")){
            workExperLayout.setVisibility(View.VISIBLE);
            baseWork.setText("1.可填写个人的工作经历");
            workPerformance.setText("2.可填写个人在工作中取得的成绩和成就");
            messageCommentTv.setText("工作经历");
            resumeWork.setText(resumeValueBean.getResumeWorkExperience());



        }else if (data.equals("picture")){
            addImageLayout.setVisibility(View.VISIBLE);
            messageCommentTv.setText("图片介绍");
            if (resumeValueBean.getResumePicture()!=null&&resumeValueBean.getResumePicture().size()!=0){
                for (int i = 0; i < resumeValueBean.getResumePicture().size(); i++) {
                    RelativeLayout relativeLayout=new RelativeLayout(ResumeMessageActivity.this);
                    final ImageView imageView=new ImageView(ResumeMessageActivity.this);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setMinimumWidth(200);
                    imageView.setMinimumHeight(200);
                    ImageView deleteImageView=new ImageView(ResumeMessageActivity.this);
                    RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    lp1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    lp1.addRule(RelativeLayout.CENTER_VERTICAL);
                    deleteImageView.setBackgroundResource(R.mipmap.delete_button_icon);
                    deleteImageView.setLayoutParams(lp1);
                    relativeLayout.addView(imageView);
                    relativeLayout.addView(deleteImageView);
                    wordWrapView.addView(relativeLayout);
                    final int finalI = i;
                    deleteImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletePictureData(imageView,finalI);

                            //Toast.makeText(ResumeMessageActivity.this,"hsdjdskak",Toast.LENGTH_LONG).show();
                        }
                    });
                    MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl+resumeValueBean.getResumePicture().get(i).getPath(), imageView, MyAppliction.RoundedOptionsOne);
                }

            }


        }else if (data.equals("video")){
            addVideoLayout.setVisibility(View.VISIBLE);
            messageCommentTv.setText("视频展示");
            /*if (resumeValueBean.getResumeMovie()!=null&&resumeValueBean.getResumeMovie().size()!=0) {

                for (int i = 0; i < resumeValueBean.getResumeMovie().size(); i++) {
                   // RelativeLayout videoRelativeLayout = new RelativeLayout(ResumeMessageActivity.this);
                    final ImageView videoImageView = new ImageView(ResumeMessageActivity.this);
                    videoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    videoImageView.setMinimumWidth(400);
                    videoImageView.setMinimumHeight(400);
                   *//* ImageView deleteVideoIv = new ImageView(ResumeMessageActivity.this);
                    RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    lp1.addRule(RelativeLayout.CENTER_VERTICAL);
                    deleteVideoIv.setBackgroundResource(R.mipmap.delete_button_icon);
                    deleteVideoIv.setLayoutParams(lp1);
                    videoRelativeLayout.addView(videoRelativeLayout);
                    videoRelativeLayout.addView(deleteVideoIv);*//*
                    videowordWrapView.addView(videoImageView);
                    String imagpath = AppUtilsUrl.ImageBaseUrl + resumeValueBean.getResumeMovie().get(i).getPath();
                    Bitmap bitmap = createVideoThumbnail(imagpath, 10, 10);
                    videoImageView.setImageBitmap(bitmap);

                }
            }*/


        }else if(data.equals("music")){
            addMusicLayout.setVisibility(View.VISIBLE);
            messageCommentTv.setText("音乐分享");
            if (resumeValueBean.getResumeMusic()!=null&&resumeValueBean.getResumeMusic().size()!=0){
                for (int i = 0; i < resumeValueBean.getResumeMusic().size(); i++) {
                    relativeLayout=new RelativeLayout(ResumeMessageActivity.this);
                    TextView musicTextView=new TextView(ResumeMessageActivity.this);
                    musicTextView.setText(resumeValueBean.getResumeMusic().get(i).getTitle());
                    Drawable drawable= getResources().getDrawable(R.mipmap.music_button_icon);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    musicTextView.setCompoundDrawables(null, null, drawable, null);
                    musicTextView.setTextColor(Color.WHITE);
                    musicTextView.setCompoundDrawablePadding(400);
                    ImageView deleteMusicIv = new ImageView(ResumeMessageActivity.this);
                    RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    lp1.addRule(RelativeLayout.CENTER_VERTICAL);
                    deleteMusicIv.setBackgroundResource(R.mipmap.delete_button_icon);
                    deleteMusicIv.setLayoutParams(lp1);
                    relativeLayout.addView(musicTextView);
                    relativeLayout.addView(deleteMusicIv);
                    musicWordWrapView.addView(relativeLayout);
                    final int finalI = i;
                    deleteMusicIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteMusicData(finalI);
                        }


                    });



                }


            }



        }


    }

    private void deleteMusicData(int i) {
        requestParams.addBodyParameter("resumeid",resumeValueBean.getResumeid()+"");
        requestParams.addBodyParameter("musicid",resumeValueBean.getResumeMusic().get(i).getResumemusicid()+"");
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getDeleteMusic(), requestParams,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                   Log.e("deleteMusicData",responseInfo.result) ;
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });



    }

    private void deletePictureData(final ImageView imageView,int i) {
        requestParams.addBodyParameter("resumeid",resumeValueBean.getResumeid()+"");
        requestParams.addBodyParameter("id",resumeValueBean.getResumePicture().get(i).getResumepictureid()+"");
        Log.e("Resumepictureid", resumeValueBean.getResumePicture().get(i).getResumepictureid()+"");
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getDeletePicture(), requestParams,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               String result= responseInfo.result;
                Log.e("deletePictureData",result);
                wordWrapView.removeView(relativeLayout);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message_save_tv:
                if (data.equals("oneself")){
                  String userInfo=  resumeInfo.getText().toString();
                    if (!TextUtils.isEmpty(userInfo)){
                        intent.putExtra("userInfo", userInfo);
                        setResult(7, intent);
                        finish();

                    }else {
                        intent.putExtra("userInfo", "");
                        setResult(7, intent);
                        alert("你还没有输入内容");
                    }

                }else if (data.equals("work")){
                    String userWork=resumeWork.getText().toString();
                    if (!TextUtils.isEmpty(userWork)) {
                        intent.putExtra("userWork", userWork);
                        setResult(8, intent);
                        finish();
                    }else {
                        intent.putExtra("userWork", "");
                        setResult(8, intent);
                        alert("你还没有输入内容");
                    }
                }else if (data.equals("picture")){
                   userPicturePath=screenshotFile.getAbsolutePath();
                    if (!TextUtils.isEmpty(userPicturePath)) {
                        intent.putExtra("userPicturePath", userPicturePath);
                        setResult(9, intent);
                        finish();
                    }else {
                        intent.putExtra("userPicturePath", "");
                        setResult(9, intent);
                        alert("你还没有选择图片文件");
                    }


                }else if (data.equals("video")){

                    //String userVideoPath=videoFile.getAbsolutePath();
                    if (!TextUtils.isEmpty(videoPath)) {
                        intent.putExtra("userVideoPath", videoPath);
                        setResult(10, intent);
                        finish();
                    }else {
                        intent.putExtra("userVideoPath", "");
                        setResult(10, intent);
                        alert("你还没有选择视频文件");
                    }


                }else if(data.equals("music")){

                    //String usermusicPath=musicFile.getAbsolutePath();
                    if (!TextUtils.isEmpty(musicPath)) {
                        intent.putExtra("usermusicPath", musicPath);
                        setResult(11, intent);
                        finish();
                    }else {
                        intent.putExtra("usermusicPath", "");
                        setResult(11, intent);
                        alert("你还没有选择音乐文件");
                    }

                }

                break;
            case R.id.message_return_tv:
                if (data.equals("oneself")){
                    intent.putExtra("userInfo", "");
                    setResult(7, intent);

                }else if(data.equals("work")){
                    intent.putExtra("userWork", "");
                    setResult(8, intent);

                }else if (data.equals("picture")){
                    intent.putExtra("userPicturePath", "");
                    setResult(9, intent);

                }else if (data.equals("video")){
                    intent.putExtra("userVideoPath", "");
                    setResult(10, intent);

                }else if(data.equals("music")){
                    intent.putExtra("usermusicPath", "");
                    setResult(11, intent);

                }
                 finish();
                break;
            case R.id.add_uplaod_image_layout:

                showExitPictureAlert();

                break;
            case R.id.action_video_bt:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(videoPath), "video/mp4");
                startActivity(intent);
                break;
            case R.id.upload_video_layout:
                showExitVideoAlert();
                break;
            case R.id.add_uplaod_music_layout:
                showExitMusicAlert();
                break;


        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (data.equals("oneself")){
                intent.putExtra("userInfo", "");
                setResult(7, intent);

            }else if(data.equals("work")){
                intent.putExtra("userWork", "");
                setResult(8, intent);

            }else if (data.equals("picture")){
                intent.putExtra("userPicturePath", "");
                setResult(9, intent);
                return false;

            }else if (data.equals("video")){
                intent.putExtra("userVideoPath", "");
                setResult(10, intent);

            }else if(data.equals("music")){
                intent.putExtra("usermusicPath", "");
                setResult(11, intent);

            }

        }
        return super.onKeyDown(keyCode, event);

    }



    //拍照对话框
    private void showExitPictureAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.upload_image_layout);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        //tailte.setText(photograph);
        tailte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ResumeActivity.this, CramaActivity.class);
//                startActivity(intent);
                Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(tempFile));
                startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
                dlg.cancel();

            }
        });

        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        //ok.setText(selectFile);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 退出应用...
                Intent intent = new Intent();
                intent.setType("image/*");
                //intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        //cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }
    //视频
    private void showExitVideoAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.upload_video_layout);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        //tailte.setText(photograph);
        tailte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ResumeActivity.this, CramaActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, 4);
                dlg.cancel();

            }
        });

        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        //ok.setText(selectFile);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 退出应用...
                Intent intent = new Intent();
                intent.setType("video/*");
                //intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 4);
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        //cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }

    //音乐对话框
    private void showExitMusicAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.upload_music_layout);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        //tailte.setText(photograph);
        tailte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ResumeActivity.this, CramaActivity.class);
//                startActivity(intent);
//                Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                // 指定调用相机拍照后照片的储存路径
//                cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(tempFile));
//                startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, 5);

                dlg.cancel();

            }
        });

        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        //ok.setText(selectFile);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 退出应用...
                Intent intentFromRecord=new Intent();
                intentFromRecord.setType("audio/*");
                intentFromRecord.setAction(Intent.ACTION_GET_CONTENT);
                intentFromRecord.putExtra("return-data", true);
                startActivityForResult(intentFromRecord,5);
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        //cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }

    private void alert(String text) {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode){
            case 4:
                if (null != data) {
                    Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    } else {
                        // 视频捕获并保存到指定的fileUri意图
                        Toast.makeText(this, "Video saved to:\n" + data.getData(),
                                Toast.LENGTH_LONG).show();
                        Cursor c = getContentResolver().query(uri,
                                new String[]{MediaStore.MediaColumns.DATA},
                                null, null, null);
                        if (c != null && c.moveToFirst()) {
                            // tv.setText("上传中请等待......");
                            // pb.setVisibility(View.VISIBLE);
                            videoPath = c.getString(0);
                            if (!TextUtils.isEmpty(videoPath)) {
                                //videoFile = new File(filPath);
                                showVideoImage.setVisibility(View.VISIBLE);
                                //Log.e("test", videoFile.getAbsolutePath());
                                      if (!TextUtils.isEmpty(videoPath)) {
                                          videoView.setVideoPath(videoPath);
                                          videoThumbnail.setImageBitmap(getVideoThumbnail(videoPath, 600, 250,
                                                  MediaStore.Images.Thumbnails.MINI_KIND));
                                      }

                            }

                        }

                    }
                }




                break;
            case 5:
                if (null != data) {
                    Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    } else {
                        // 音频捕获并保存到指定的fileUri意图
                        Toast.makeText(this, "Music saved to:\n" + data.getData(),
                                Toast.LENGTH_LONG).show();
                        Cursor c = getContentResolver().query(uri,
                                new String[] { MediaStore.MediaColumns.DATA },
                                null, null, null);
                        if (c != null && c.moveToFirst()) {
                            // tv.setText("上传中请等待......");
                            // pb.setVisibility(View.VISIBLE);
                            musicPath = c.getString(0);
                            File musicFile = new File(musicPath);
                           String name= musicFile.getName();
                            TextView musicTv=new TextView(ResumeMessageActivity.this);
                            musicTv.setText(name);
                            musicTv.setTextColor(Color.WHITE);
                            Drawable drawable= getResources().getDrawable(R.mipmap.music_button_icon);
                            /// 这一步必须要做,否则不会显示.
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            musicTv.setCompoundDrawables(null, null, drawable, null);
                            musicTv.setCompoundDrawablePadding(300);
                            musicWordWrapView.addView(musicTv);

                        }

                    }
                }

                break;

            case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
                startPhotoZoom(Uri.fromFile(tempFile));
                break;
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null)
                    startPhotoZoom(data.getData());
                break;
            case PHOTO_REQUEST_CUT:// 返回的结果
                if (data != null)
                    // setPicToView(data);
                    sentPicToNext(data);

                break;


        }

        super.onActivityResult(requestCode, resultCode, data);
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

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    // 将进行剪裁后的图片传递到下一个界面上
    private void sentPicToNext(Intent picdata) {
        Bundle bundle = picdata.getExtras();

        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            ImageView imageView=new ImageView(ResumeMessageActivity.this);
            //showAddPictureLayout.addView(imageView);
            wordWrapView.addView(imageView);
            if (photo == null) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            } else {
                Bitmap zoomBitmap = ImageUtil.zoomBitmap(photo, 200, 200);
                //获取圆角图片
                Bitmap roundBitmap = ImageUtil.getRoundedCornerBitmap(zoomBitmap, 30.0f);
                imageView.setImageBitmap(roundBitmap);



                //获取倒影图片
                //Bitmap reflectBitmap = ImageUtil.createReflectionImageWithOrigin(roundBitmap);
//                设置文本内容为    图片绝对路径和名字
                //text.setText(tempFile.getAbsolutePath());
                //Log.e("tempFile",tempFile.getAbsolutePath());
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(screenshotFile);
                    if (null != fos) {
                        photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();

                        //  Toast.makeText(ResumeActivity.this, "截屏文件已保存至SDCard/AndyDemo/ScreenImage/下", Toast.LENGTH_LONG).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] photodata = baos.toByteArray();
                //System.out.println(photodata.toString());

            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     * @param videoPath 视频的路径
     * @param width 指定输出视频缩略图的宽度
     * @param height 指定输出视频缩略图的高度度
     * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    private Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        //System.out.println("w"+bitmap.getWidth());
        //System.out.println("h"+bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


}
