package com.example.administrator.iclub21;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.bean.LoginValueBean;
import com.example.administrator.iclub21.bean.recruitment.SendParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.HomeActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.text.SimpleDateFormat;


public class MainActivity extends ActionBarActivity {

    private TextView text,text1,text2;
    public static Tencent mTencent;

    private static final String TAG = "weibosdk";

    /** ��ʾ��֤�����Ϣ���� AccessToken */
    private TextView mTokenText;

    private AuthInfo mAuthInfo;

    /** ��װ�� "access_token"��"expires_in"��"refresh_token"�����ṩ�����ǵĹ�����  */
    private Oauth2AccessToken mAccessToken;

    /** ע�⣺SsoHandler ���� SDK ֧�� SSO ʱ��Ч */
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.text);
        text1 = (TextView)findViewById(R.id.text1);
        mTokenText = (TextView)findViewById(R.id.text2);
//        text2 =

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTencent = Tencent.createInstance("222222", MainActivity.this);
                doLogin();
            }
        });

        //΢����½
        mTokenText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                weibodenglu();
            }
        });


        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inti();
            }
        });



    }
         ///  ���Ǿ��úõĻ�
    //������΢����¼
    private void weibodenglu(){
        // ��ȡ Token View��������ʾ View �����ݿɹ�����С��Ļ������ʾ��ȫ��

//        TextView hintView = (TextView) findViewById(com.sina.weibo.sdk.demo.R.id.obtain_token_hint);
//        hintView.setMovementMethod(new ScrollingMovementMethod());

        // ������Ȩʱ���벻Ҫ���� SCOPE��������ܻ���Ȩ���ɹ�
        mAuthInfo = new AuthInfo(MainActivity.this, Constantser.APP_KEY, Constantser.REDIRECT_URL, Constantser.SCOPE);
        mSsoHandler = new SsoHandler(MainActivity.this, mAuthInfo);

        mSsoHandler.authorize(new AuthListener());

        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            updateTokenView(true);
        }
    }
    /**
     * �� SSO ��Ȩ Activity �˳�ʱ���ú��������á�
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO ��Ȩ�ص�
        // ��Ҫ������ SSO ��½�� Activity ������д onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

    /**
     * ΢����֤��Ȩ�ص��ࡣ
     * 1. SSO ��Ȩʱ����Ҫ�� {@link #onActivityResult} �е��� {@link SsoHandler#authorizeCallBack} ��
     *    �ûص��Żᱻִ�С�
     * 2. �� SSO ��Ȩʱ������Ȩ�����󣬸ûص��ͻᱻִ�С�
     * ����Ȩ�ɹ����뱣��� access_token��expires_in��uid ����Ϣ�� SharedPreferences �С�
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // �� Bundle �н��� Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //�������ȡ�û������ �绰������Ϣ
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // ��ʾ Token
                updateTokenView(false);

                // ���� Token �� SharedPreferences
                AccessTokenKeeper.writeAccessToken(MainActivity.this, mAccessToken);
                Toast.makeText(MainActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
            } else {
                // ���¼�������������յ� Code��
                // 1. ����δ��ƽ̨��ע���Ӧ�ó���İ�����ǩ��ʱ��
                // 2. ����ע���Ӧ�ó��������ǩ������ȷʱ��
                // 3. ������ƽ̨��ע��İ�����ǩ��������ǰ���Ե�Ӧ�õİ�����ǩ����ƥ��ʱ��
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(MainActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ��ʾ��ǰ Token ��Ϣ��
     *
     * @param hasExisted �����ļ����Ƿ��Ѵ��� token ��Ϣ���ҺϷ�
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
        mTokenText.setText(message);
        denglu(AppUtilsUrl.getLoginWeibo(mAccessToken.getToken()));
    }


    //qq��¼
    private void denglu(String token){
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, token, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    SendParme<LoginValueBean> loginValueBean = com.alibaba.fastjson.JSONObject.parseObject(result, new TypeReference<SendParme<LoginValueBean>>() {
                    });
                    if (loginValueBean.getState().equals("success")) {
                        LoginValueBean loginValueData = com.alibaba.fastjson.JSONObject.parseObject(loginValueBean.getValue(), LoginValueBean.class);

                        text.setText(loginValueData.getUid()+loginValueData.getUserName()+loginValueData.getState());
//                        if (viewCountData.equals("success")) {
////                            sendBl = true;
//
//                        } else if (viewCountData.equals("failure")){
////                            sendBl = false;
//                        }else {
////                            sendBl = false;
//                        }
                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    //������qq��¼
    private void doLogin() {
        IUiListener listener = new BaseUiListener() {
            @Override
            protected void doComplete(JSONObject values) {
                try {
                    String token = values.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = values.getString(Constants.PARAM_EXPIRES_IN);
                    String openId = values.getString(Constants.PARAM_OPEN_ID);
                    denglu(AppUtilsUrl.getLoginQQ(token));
//                    new_login_btn.setText(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                updateLoginButton();
            }
        };
        mTencent.login(this, "all", listener);
    }


    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //V2.0�汾������������JSONObject �ĳ���Object,�������Ͳο�api�ĵ�
//            mBaseMessageText.setText("onComplete:");
            doComplete((JSONObject)response);
        }
        protected void doComplete(JSONObject values) {

        }
        @Override
        public void onError(UiError e) {
//            showResult("onError:", "code:" + e.errorCode + ", msg:"
//                    + e.errorMessage + ", detail:" + e.errorDetail);
        }
        @Override
        public void onCancel() {
//            showResult("onCancel", "");
        }
    }

    public  void inti(){
        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

}
