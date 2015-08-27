package com.example.administrator.iclub21.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.iclub21.http.MyAppliction;
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

public class BoundPhoneActivity extends ActionBarActivity implements View.OnClickListener {
    @ViewInject(R.id.register_title_tv)
    private TextView title;
    @ViewInject(R.id.register_phone)
    private EditText phoneEditText;
    @ViewInject(R.id.captcha_edit)
    private EditText captchaEditText;
    @ViewInject(R.id.captcha_button)
    private Button captchaButton;
    @ViewInject(R.id.register_commit_tv)
    private TextView commitTv;
    private TimeCount time;
    private HttpUtils httpUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_phone);
        ViewUtils.inject(this);
        intiView();
    }

    private void intiView() {
        httpUtils=new HttpUtils();
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        title.setText("绑定手机");
        captchaButton.setOnClickListener(this);
        commitTv.setOnClickListener(this);


    }

    private void intiVcodeData() {
        String pohten=phoneEditText.getText().toString();
        if (pohten!=null&&pohten.length()==11) {
            time.start();
            httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getVcodeData(pohten), new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {

                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.e("onFailure", s);
                }
            });
        }else {
            MyAppliction.showToast("电话号码错误");

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.captcha_button:
                intiVcodeData();
                break;
            case R.id.register_commit_tv:
                boundPhoneData();


                break;
        }



    }

    private void boundPhoneData() {
        SQLhelper sqLhelper=new SQLhelper(BoundPhoneActivity.this);
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);
        }
        RequestParams requestParams=new RequestParams();
        if (!TextUtils.isEmpty(uid)){
            if (!TextUtils.isEmpty(captchaEditText.getText().toString())){
                if (captchaEditText.getText().toString().length()!=6){
                  if (!TextUtils.isEmpty(phoneEditText.getText().toString())) {
                      if (phoneEditText.getText().toString().length()!=11){
                          requestParams.addBodyParameter("uid", uid);
                          requestParams.addBodyParameter("vcode",captchaEditText.getText().toString());
                          requestParams.addBodyParameter("mobile",phoneEditText.getText().toString());
                          final String finalUid = uid;
                          httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getBoundData(), requestParams, new RequestCallBack<String>() {
                              @Override
                              public void onSuccess(ResponseInfo<String> responseInfo) {
                                 MyAppliction.showToast("手机号码绑定成功");
                                  update(finalUid,phoneEditText.getText().toString());
                                  finish();
                              }

                              @Override
                              public void onFailure(HttpException e, String s) {

                              }
                          });

                      }else {
                          MyAppliction.showToast("请输入11位手机号码");

                      }

                  }else {
                      MyAppliction.showToast("请输入手机号码");
                  }



                }else {
                    MyAppliction.showToast("验证码为6位");

                }

            }else {
                MyAppliction.showToast("请输入验证码");
            }


        }else {
            MyAppliction.showToast("您的uid为空");
        }

    }
    //更新电话号码
    public void update(String uid,String name){
        SQLhelper sqLhelper= new SQLhelper(BoundPhoneActivity.this);
        SQLiteDatabase db = sqLhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLhelper.MOBILE, name);
        db.update(SQLhelper.tableName, contentValues,
                "uid=?",
                new String[]{uid});
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            captchaButton.setText("重新验证");
            captchaButton.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            captchaButton.setClickable(false);
            captchaButton.setText(millisUntilFinished / 1000 + "秒");
        }

    }

}
