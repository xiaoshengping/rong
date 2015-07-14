package com.example.administrator.iclub21.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.RatingBarStar;
import com.example.administrator.iclub21.bean.recruitment.JobDetailsDialog;
import com.example.administrator.iclub21.bean.recruitment.RecruitmentListBean;
import com.example.administrator.iclub21.bean.recruitment.SendParme;
import com.example.administrator.iclub21.bean.recruitment.ViewCountBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2015/6/3.
 */
public class JobDetailsActivity extends Activity {

    private TextView workPay;
    private TextView companyName;
    private TextView workPlace;
    private TextView recruitingNumbers;
    private TextView jobRequirements;
    private TextView jobInfo;
    private TextView phone;
    private TextView email;
    private TextView web;
    private TextView address;
    private TextView position;
    private TextView puttime;
    private LinearLayout star;
    private TextView viewCount;

    private RatingBarStar rbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetails);
        init();
    }

    private void binding() {
        workPay = (TextView) findViewById(R.id.workpay_tv);
        companyName = (TextView) findViewById(R.id.companyName_tv);
        workPlace = (TextView) findViewById(R.id.workplace_tv);
        recruitingNumbers = (TextView) findViewById(R.id.recruitingNumbers_tv);
        jobRequirements = (TextView) findViewById(R.id.jobRequirements_tv);
        jobInfo = (TextView) findViewById(R.id.jobInfo_tv);
        phone = (TextView) findViewById(R.id.phone_tv);
        email = (TextView) findViewById(R.id.email_tv);
        web = (TextView) findViewById(R.id.web_tv);
        address = (TextView) findViewById(R.id.address_tv);
        position = (TextView) findViewById(R.id.position_tv);
        puttime = (TextView) findViewById(R.id.puttime_tv);
        star = (LinearLayout) findViewById(R.id.star_ll);
        viewCount = (TextView) findViewById(R.id.viewCount_tv);
    }

    private RecruitmentListBean recruitmentListBean;

    private void init() {
        binding();
//        Bundle bundle=this.getIntent().getExtras();
//        ArrayList list2 = bundle.getParcelableArrayList("list");
        recruitmentListBean = (RecruitmentListBean) getIntent().getSerializableExtra("Detail");
//        status = bundle.getInt("Status");
        workPay.setText(recruitmentListBean.getWorkPay());
        companyName.setText(recruitmentListBean.getCompanyName());
        workPlace.setText(recruitmentListBean.getWorkPlace());
        recruitingNumbers.setText(recruitmentListBean.getRecruitingNumbers() + "人");
        jobRequirements.setText(recruitmentListBean.getJobRequirements());
        jobInfo.setText(recruitmentListBean.getJobInfo());
        phone.setText(recruitmentListBean.getPhone());
        email.setText(recruitmentListBean.getEmail());
        web.setText(recruitmentListBean.getWeb());
        address.setText(recruitmentListBean.getAddress());
        position.setText(recruitmentListBean.getPosition());
        puttime.setText("发布时间：" + recruitmentListBean.getPuttime().split(" ")[0]);
        viewCount.setText("浏览量:" + recruitmentListBean.getViewCount());
        rbs = new RatingBarStar();
        rbs.getRatingBarStar(this, star, recruitmentListBean.getStar());
//        star.setRating((int) recruitmentListBean.getStar());

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getViewCount(recruitmentListBean.getJobId()), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String result = responseInfo.result;
//                if (result != null) {
//                    ArtistParme<ViewCountBean> viewCountBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<ViewCountBean>>() {
//                    });
////                    if (viewCountBean.getState().equals("success")) {
//////                        List<ViewCountBean> viewCountData = viewCountBean.getValue();
//////                        if(viewCountData.get(0).getMessage().equals("success")){
////////                            workPay.setText(viewCountData.get(0).getMessage());
//////                        }
//////
////                    }
//
//                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }

    private boolean register = true;//登录状态
    private boolean sendBl=true;//是否投递成功
    private JobDetailsDialog dialog2;

    private void dialog() {
        dialog2 = new JobDetailsDialog(JobDetailsActivity.this,register,sendBl);
//        EditText editText = (EditText) dialog.getEditText();//方法在CustomDialog中实现
        dialog2.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.dismiss();
            }
        });
        dialog2.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

//        Window window = dialog2.getWindow();
//        window.setGravity(Gravity.TOP);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        // 设置透明度为0.3
//        lp.alpha = 0.6f;
//        window.setAttributes(lp);

        dialog2.show();
    }


    public void send(View v){

        if(register) {

            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getSend(recruitmentListBean.getJobId(), 13), new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    if (result != null) {
                    SendParme<ViewCountBean> viewCountBean = JSONObject.parseObject(result, new TypeReference<SendParme<ViewCountBean>>() {
                    });
                    if (viewCountBean.getState().equals("success")) {
                        ViewCountBean viewCountData = JSONObject.parseObject(viewCountBean.getValue(), ViewCountBean.class);

                        if (viewCountData.getMessage().equals("success")) {
                            sendBl = true;

                        } else if (viewCountData.getMessage().equals("failure")){
                            sendBl = false;
                        }else {
                            sendBl = false;
                        }
                    }

                    }


                    }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        }

        dialog();

    }

    public void back(View v){

        finish();

    }

}
