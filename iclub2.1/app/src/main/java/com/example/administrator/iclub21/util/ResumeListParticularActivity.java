package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.iclub21.adapter.ResumeMusicAdapter;
import com.example.administrator.iclub21.adapter.ResumeVideoAdapter;
import com.example.administrator.iclub21.bean.ResumeValueBean;
import com.example.administrator.iclub21.bean.talent.CircleImageView;
import com.example.administrator.iclub21.bean.talent.PicturesshowMoreActivity;
import com.example.administrator.iclub21.bean.talent.SpaceImageDetailActivity;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.view.CustomHomeScrollListView;
import com.example.administrator.iclub21.view.WordWrapView;
import com.jeremy.Customer.R;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ResumeListParticularActivity extends ActionBarActivity implements View.OnClickListener ,CompoundButton.OnCheckedChangeListener {
      //基本信息

    @ViewInject(R.id.resumeZhName_tv)
    private TextView resumeZhName;
    @ViewInject(R.id.resumeSex_iv)
    private ImageView resumeSexIv;
    @ViewInject(R.id.resume_ViewCount_tv)
    private TextView resumeViewCountTv;
    @ViewInject(R.id.resumeAge_tv)
    private TextView resumeAgeTv;
    @ViewInject(R.id.resume_WorkPlace_tv)
    private TextView resumeWorkPlaceTv;
    @ViewInject(R.id.resumeJobName_isd_tv)
    private TextView resumeJobNameTv;
    @ViewInject(R.id.resumeInfo_tv)
    private TextView resumeInfoTv;
    @ViewInject(R.id.resume_WorkExperience_tv)
    private TextView resumeWorkExperienceTv;
    @ViewInject(R.id.resumeQq_tv)
    private TextView resumeQqTv;
    @ViewInject(R.id.resumeEmail_tv)
    private TextView resumeEmailTv;
    @ViewInject(R.id.resumeMobile_tv)
    private TextView resumeMobileTv;
    @ViewInject(R.id.usericon_background_iv)
    private CircleImageView userIconIv;

   //编辑和返回
    @ViewInject(R.id.list_retrun_tv)
    private TextView returnListTv;
    @ViewInject(R.id.list_compile_text)
    private TextView compileListTv;
    private  ResumeValueBean resumeValueBean;
    //图片
   @ViewInject(R.id.show_picture_tv)
    private TextView showPictureTv;
    @ViewInject(R.id.listparticual_picture_layout)
    private LinearLayout listPictureLayout;
    @ViewInject(R.id.more_button)
    private TextView moreButton;



    //音乐
    @ViewInject(R.id.music_togglButton)
    private ToggleButton musicToggleButton;
      @ViewInject(R.id.no_music_tv)
    private TextView noMusicTextView;
    @ViewInject(R.id.show_music_lv)
    private CustomHomeScrollListView showMusicListView;
    //视频
    @ViewInject(R.id.on_video_textview)
    private TextView noShowViewTv;
    @ViewInject(R.id.video_togglButton)
    private ToggleButton videoToggleButton;
    @ViewInject(R.id.listparticual_video_layout)
    private LinearLayout listViewLayout;
    @ViewInject(R.id.resume_video_list_view)
    private ListView videoListView;
    @ViewInject(R.id.progressbar)
    private ProgressBar progressbar;

    //背景
    @ViewInject(R.id.talen_back_iv)
    private ImageView talenBackIv;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());
    private File screenshotFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    private WordWrapView wordWrapView;


    private  Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_list_particular);
        ViewUtils.inject(ResumeListParticularActivity.this);
        inti();
    }
    private void intiView() {
        wordWrapView = (WordWrapView) this.findViewById(R.id.view_wordwrap);
       // showMusicWordWrapView=(WordWrapView) this.findViewById(R.id.show_music_tv);
        returnListTv.setOnClickListener(this);
        compileListTv.setOnClickListener(this);
        talenBackIv.setOnClickListener(this);
        musicToggleButton.setOnCheckedChangeListener(this);
        videoToggleButton.setOnCheckedChangeListener(this);

        //showMusicTextView.setOnClickListener(this);
        moreButton.setOnClickListener(this);
        //showVideoTextView.setOnClickListener(this);
         videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                 Uri videoUri = Uri.parse(AppUtilsUrl.ImageBaseUrl + resumeValueBean.getResumeMovie().get(position).getPath());
                 videoIntent.setDataAndType(videoUri, "video/mp4");
                 startActivity(videoIntent);

             }
         });


    }

    private void inti() {
        intiView();
        intiData();



    }


    private void intiData() {
        intent=getIntent();
        resumeValueBean= (ResumeValueBean) intent.getSerializableExtra("resumeValueBeans");
        if (intent.getSerializableExtra("flage").equals("MerchantInviteMessageFragment")){
            compileListTv.setVisibility(View.GONE);
        }else {
            compileListTv.setVisibility(View.VISIBLE);
        }
        MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + resumeValueBean.getUsericon(), userIconIv, MyAppliction.RoundedOptions);
        resumeZhName.setText(resumeValueBean.getResumeZhName());
        if (resumeValueBean.getResumeSex()==0){
            resumeSexIv.setBackgroundResource(R.mipmap.man_icon);

        }else if(resumeValueBean.getResumeSex()==1){
            resumeSexIv.setBackgroundResource(R.mipmap.girl_icon);

        }
        if(!TextUtils.isEmpty(resumeValueBean.getResumeUserbg())){
            MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + resumeValueBean.getResumeUserbg(), talenBackIv,MyAppliction.options);
        }else {
            talenBackIv.setBackgroundResource(R.mipmap.resume_background_icon);
        }

        resumeViewCountTv.setText("浏览量:"+resumeValueBean.getResumeViewCount());
        resumeAgeTv.setText(resumeValueBean.getResumeAge()+"");
        resumeWorkPlaceTv.setText(resumeValueBean.getResumeWorkPlace());
        resumeJobNameTv.setText(resumeValueBean.getResumeJobCategoryName());
        resumeInfoTv.setText(resumeValueBean.getResumeInfo());
        resumeWorkExperienceTv.setText(resumeValueBean.getResumeWorkExperience());
        resumeQqTv.setText(resumeValueBean.getResumeQq());
        resumeEmailTv.setText(resumeValueBean.getResumeEmail());
        resumeMobileTv.setText(resumeValueBean.getResumeMobile());
        if (resumeValueBean.getResumePicture()!=null&&resumeValueBean.getResumePicture().size()!=0){
            for (int i = 0; i <resumeValueBean.getResumePicture().size() ; i++) {
                ImageView pictureImage=new ImageView(ResumeListParticularActivity.this);
                pictureImage.setScaleType(ImageView.ScaleType.FIT_XY);
                pictureImage.setMinimumWidth(200);
                pictureImage.setMinimumHeight(200);
                wordWrapView.addView(pictureImage);
                MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + resumeValueBean.getResumePicture().get(i).getPath(), pictureImage, MyAppliction.RoundedOptionsOne);
                final int finalI = i;
                pictureImage.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(ResumeListParticularActivity.this, SpaceImageDetailActivity.class);
                         Bundle bundle=new Bundle();
                         bundle.putParcelableArrayList("list", (ArrayList) resumeValueBean.getResumePicture());
                         bundle.putInt("num", finalI);
                         bundle.putInt("MaxNum",resumeValueBean.getResumePicture().size() );
                         intent.putExtras(bundle);
                         startActivity(intent);
                         overridePendingTransition(R.anim.spaceimagedetail_in,R.anim.out_to_not);
                     }
                 });


            }

        }else {
            showPictureTv.setVisibility(View.VISIBLE);

        }

           if (resumeValueBean.getResumeMusic()!=null&&resumeValueBean.getResumeMusic().size()!=0){
               /*for (int j = 0; j < resumeValueBean.getResumeMusic().size(); j++) {
                   TextView musicTextView=new TextView(ResumeListParticularActivity.this);
                   musicTextView.setText(resumeValueBean.getResumeMusic().get(j).getTitle());
                   musicTextView.setTextColor(Color.WHITE);
                 Drawable drawable= getResources().getDrawable(R.mipmap.music_button_icon);
                   /// 这一步必须要做,否则不会显示.
                   drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                   musicTextView.setCompoundDrawables(null, null, drawable, null);
                   musicTextView.setCompoundDrawablePadding(100);
                   //musicTextView.setBackgroundResource(R.mipmap.misuc_beiji_icon);
                   showMusicWordWrapView.addView(musicTextView);
                   final int finalI = j;
                   musicTextView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent musicIntent = new Intent(Intent.ACTION_VIEW);
                           Uri musicUri = Uri.parse(AppUtilsUrl.ImageBaseUrl + resumeValueBean.getResumeMusic().get(finalI).getPath());
                           musicIntent.setDataAndType(musicUri, "audio/mp3");
                           startActivity(musicIntent);
                       }
                   });

               }*/
               ResumeMusicAdapter resumeMusicAdapter=new ResumeMusicAdapter(resumeValueBean.getResumeMusic(),ResumeListParticularActivity.this);
               showMusicListView.setAdapter(resumeMusicAdapter);
               resumeMusicAdapter.notifyDataSetChanged();
               showMusicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Intent musicIntent = new Intent(Intent.ACTION_VIEW);
                       Uri musicUri = Uri.parse(AppUtilsUrl.ImageBaseUrl + resumeValueBean.getResumeMusic().get(position).getPath());
                       musicIntent.setDataAndType(musicUri, "audio/mp3");
                       startActivity(musicIntent);
                   }
               });
               showMusicListView.setVisibility(View.VISIBLE);

           }else {

               noMusicTextView.setVisibility(View.VISIBLE);
           }



    }

    private void videoData() {

        if (resumeValueBean.getResumeMovie()!=null&&resumeValueBean.getResumeMovie().size()!=0){
            progressbar.setVisibility(View.VISIBLE);
            ResumeVideoAdapter resumeVideoAdapter=new ResumeVideoAdapter(resumeValueBean.getResumeMovie(),this);
            videoListView.setAdapter(resumeVideoAdapter);
            resumeVideoAdapter.notifyDataSetChanged();

            String imagpath= AppUtilsUrl.ImageBaseUrl+resumeValueBean.getResumeMovie().get(0).getPath();
            Bitmap bitmap=createVideoThumbnail(imagpath, 10, 10);
            if (bitmap!=null){

                videoListView.setVisibility(View.VISIBLE);
            }

        }else {
            noShowViewTv.setVisibility(View.VISIBLE);
            videoListView.setVisibility(View.GONE);
        }


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


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.list_compile_text:
                Intent intentCompile=new Intent(ResumeListParticularActivity.this,AddResumeActivity.class);
                intentCompile.putExtra("compiledata",resumeValueBean);
                intentCompile.putExtra("resumeNuber", "1111");
                //startActivity(intent);
                startActivityForResult(intentCompile,18);
                break;
            case R.id.list_retrun_tv:
                finish();
                break;
            case R.id.more_button:
                Intent intentMore = new Intent(ResumeListParticularActivity.this, PicturesshowMoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList) resumeValueBean.getResumePicture());
                intentMore.putExtras(bundle);
                startActivity(intentMore);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_not);

                break;
            case R.id.talen_back_iv:
                if ((intent.getSerializableExtra("flage")).equals("ResumeFragment")){
                    showExitHeadPhotoAlert();
                }
                break;

        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.video_togglButton:
                if (isChecked) {

                    listViewLayout.setVisibility(View.VISIBLE);
                    videoListView.setVisibility(View.VISIBLE);
                    videoData();

                }else {
                    listViewLayout.setVisibility(View.GONE);
                    videoListView.setVisibility(View.GONE);

                }
                break;
            case R.id.music_togglButton:

                if (isChecked){
                    if (resumeValueBean.getResumeMusic()!=null&&resumeValueBean.getResumeMusic().size()!=0){
                        showMusicListView.setVisibility(View.VISIBLE);
                    }else {
                        noMusicTextView.setVisibility(View.VISIBLE);
                    }


                }else {
                   showMusicListView.setVisibility(View.GONE);
                    noMusicTextView.setVisibility(View.GONE);
                }
                break;

        }



    }

    //拍照对话框
    private void showExitHeadPhotoAlert() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
            case 18:
                switch (requestCode){
                    case 18:
                        if ((data.getStringExtra("closeActivity")).equals("close")){
                            finish();
                        }

                        break;


                }

                break;


        }

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
            if (photo == null) {
                talenBackIv.setImageResource(R.mipmap.ic_launcher);
            } else {
                //Bitmap zoomBitmap = ImageUtil.zoomBitmap(photo, 100, 100);
                //获取圆角图片
                //Bitmap roundBitmap = ImageUtil.getRoundedCornerBitmap(zoomBitmap, 200.0f);
                //获取倒影图片
                //Bitmap reflectBitmap = ImageUtil.createReflectionImageWithOrigin(roundBitmap);
                talenBackIv.setImageBitmap(photo);
//                设置文本内容为    图片绝对路径和名字
                //text.setText(tempFile.getAbsolutePath());
                //Log.e("tempFile",tempFile.getAbsolutePath());
                intiBackgroundData();

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

    private void intiBackgroundData() {
        HttpUtils httpUtils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("resumeid",resumeValueBean.getResumeid()+"");
        requestParams.addBodyParameter("resumeUserbg",new File(tempFile.getAbsolutePath()));
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getResumeBackground(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("000000",responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure",s);
            }
        });




    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

}
