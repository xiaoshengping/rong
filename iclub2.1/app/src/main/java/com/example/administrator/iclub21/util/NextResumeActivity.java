package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

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
                startActivityForResult(pictureIntent, 9);

                break;
            case R.id.add_video_tv:
                Intent videoIntent = new Intent(NextResumeActivity.this, ResumeMessageActivity.class);
                videoIntent.putExtra("caseData", "videoNo");
                startActivityForResult(videoIntent, 10);

                break;
            case R.id.add_music_tv:
                Intent musicIntent = new Intent(NextResumeActivity.this, ResumeMessageActivity.class);
                musicIntent.putExtra("caseData", "musicNo");
                startActivityForResult(musicIntent, 11);
                break;
            case R.id.addresume_return_tv:
                finish();
                break;
            case R.id.complete_resume_tv:
                if (!TextUtils.isEmpty(resumeid) && !TextUtils.isEmpty(userVideoPath)) {

                    initAddVideoData(resumeid, userVideoPath);

                }
                if (!TextUtils.isEmpty(resumeid) && !TextUtils.isEmpty(userPicturePath)) {
                    intiPhotoData(resumeid, userPicturePath);
                }
                if (!TextUtils.isEmpty(resumeid) && !TextUtils.isEmpty(userMusicPath)) {
                    intiMusicData(resumeid, userMusicPath);

                }
               finish();
                break;




        }




    }

    @Override
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


    }

    private void initAddVideoData(String resumeid, String videoPath) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("resumeid", resumeid);
        requestParams.addBodyParameter("movie", new File(videoPath));
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddMovie(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // Log.e("initAddVideoData", responseInfo.result);
               // ProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });


    }

    private void intiPhotoData(String resumeid, String userPicturePath) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("resumeid", resumeid);
        requestParams.addBodyParameter("picture", new File(userPicturePath));

        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddPicture(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // Log.e("intiPhotoData", responseInfo.result);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });


    }


    private void intiMusicData(String resumeid, String musicPath) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("resumeid", resumeid);
        requestParams.addBodyParameter("music", new File(musicPath));
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddMusic(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // Log.e("onSuccess", responseInfo.result);
               // ProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });

    }

}
