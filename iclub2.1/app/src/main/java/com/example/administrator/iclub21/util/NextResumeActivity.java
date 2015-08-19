package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NextResumeActivity extends ActionBarActivity implements View.OnClickListener {

    //头部
    @ViewInject(R.id.addresume_return_tv)
    private TextView addResumeReturnTv;
    @ViewInject(R.id.text_resume)
    private TextView textResumeTalite;


    //图片介绍
    @ViewInject(R.id.add_iamge_tv)
    private TextView addImage;
    private String userPicturePath;


    //视频介绍
    @ViewInject(R.id.add_video_tv)
    private TextView addVideoTv;
    private String userVideoPath;


    //音乐展示
    @ViewInject(R.id.add_music_tv)
    private TextView addMusicTv;
    private String userMusicPath;

    //完成
    @ViewInject(R.id.complete_resume_tv)
    private TextView completeResumeTv;

    private HttpUtils httpUtils;
    private String resumeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_resume);
        ViewUtils.inject(this);
        intiView();


    }

    private void intiView() {
        textResumeTalite.setText("简历附件");
        httpUtils=new HttpUtils();
        resumeid=getIntent().getStringExtra("resumeid");
        addImage.setOnClickListener(this);
        addVideoTv.setOnClickListener(this);
        addMusicTv.setOnClickListener(this);
        completeResumeTv.setOnClickListener(this);
        addResumeReturnTv.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_iamge_tv:
                Intent pictureIntent = new Intent(NextResumeActivity.this, ResumeMessageActivity.class);
                pictureIntent.putExtra("caseData", "pictureNo");
                pictureIntent.putExtra("resumeid",resumeid);
                startActivityForResult(pictureIntent, 9);

                break;
            case R.id.add_video_tv:
                Intent videoIntent = new Intent(NextResumeActivity.this, ResumeMessageActivity.class);
                videoIntent.putExtra("caseData", "videoNo");
                videoIntent.putExtra("resumeid",resumeid);
                startActivityForResult(videoIntent, 10);

                break;
            case R.id.add_music_tv:
                Intent musicIntent = new Intent(NextResumeActivity.this, ResumeMessageActivity.class);
                musicIntent.putExtra("caseData", "musicNo");
                musicIntent.putExtra("resumeid",resumeid);
                startActivityForResult(musicIntent, 11);
                break;
            case R.id.addresume_return_tv:
                finish();
                break;
            case R.id.complete_resume_tv:
               finish();
                break;




        }




    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 9:
                userPicturePath = data.getStringExtra("userPicturePath").toString();
                //Log.e("userPicturePath",userPicturePath);
                break;
            case 10:
                userVideoPath = data.getStringExtra("userVideoPath").toString();
                // Log.e("userVideoPath",userVideoPath);
                break;
            case 11:
                userMusicPath = data.getStringExtra("usermusicPath").toString();
                break;




        }


    }*/








}
