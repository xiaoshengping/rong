package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResumeListParticularActivity extends ActionBarActivity implements View.OnClickListener ,CompoundButton.OnCheckedChangeListener {
      //基本信息
  // @ViewInject(R.id.resumeUserbg_iv)
   // private ImageView resumeUserbgIv;
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

    private WordWrapView wordWrapView;


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
        musicToggleButton.setOnCheckedChangeListener(this);
        videoToggleButton.setOnCheckedChangeListener(this);
        //showMusicTextView.setOnClickListener(this);
        moreButton.setOnClickListener(this);
        //showVideoTextView.setOnClickListener(this);
         videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                 Uri videoUri = Uri.parse(AppUtilsUrl.ImageBaseUrl+resumeValueBean.getResumeMovie().get(position).getPath());
                 videoIntent.setDataAndType(videoUri , "video/mp4");
                 startActivity(videoIntent);

             }
         });


    }

    private void inti() {
        intiView();
        intiData();



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    private void intiData() {
        Intent intent=getIntent();
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
                Intent intent=new Intent(ResumeListParticularActivity.this,AddResumeActivity.class);
                intent.putExtra("compiledata",resumeValueBean);
                intent.putExtra("resumeNuber","1111");
                startActivity(intent);
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
}
