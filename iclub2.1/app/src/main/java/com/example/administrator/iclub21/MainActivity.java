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

    /** 显示认证后的信息，如 AccessToken */
    private TextView mTokenText;

    private AuthInfo mAuthInfo;

    /** 封装�? "access_token"�?"expires_in"�?"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有�? */
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //有点东西

        text = (TextView)findViewById(R.id.text);
        text1 = (TextView)findViewById(R.id.text1);
        mTokenText = (TextView)findViewById(R.id.text2);
//        text2 = 没地方麻烦麻烦LLLLLOOOOOOOOjdhgs

//        text2 = 没地方麻烦麻烦LLLLLSSSSSSSSS
        ///asdfasdfasdfasdghegheg
        ///啊束带结发是


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTencent = Tencent.createInstance("222222", MainActivity.this);
                doLogin();
            }
        });

        //微博登陆
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

    //第三方微博登�?
    private void weibodenglu(){
        // 获取 Token View，并让提�? View 的内容可滚动（小屏幕可能显示不全�?

//        TextView hintView = (TextView) findViewById(com.sina.weibo.sdk.demo.R.id.obtain_token_hint);
//        hintView.setMovementMethod(new ScrollingMovementMethod());

        // 快�?�授权时，请不要传入 SCOPE，否则可能会授权不成�?
        mAuthInfo = new AuthInfo(MainActivity.this, Constantser.APP_KEY, Constantser.REDIRECT_URL, Constantser.SCOPE);
        mSsoHandler = new SsoHandler(MainActivity.this, mAuthInfo);

        mSsoHandler.authorize(new AuthListener());

        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            updateTokenView(true);
        }
    }
    /**
     * �? SSO 授权 Activity �?出时，该函数被调用�??
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发�? SSO 登陆�? Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

    /**
     * 微博认证授权回调类�??
     * 1. SSO 授权时，�?要在 {@link #onActivityResult} 中调�? {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行�?
     * 2. �? SSO 授权时，当授权结束后，该回调就会被执行�??
     * 当授权成功后，请保存�? access_token、expires_in、uid 等信息到 SharedPreferences 中�??
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // �? Bundle 中解�? Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                updateTokenView(false);

                // 保存 Token �? SharedPreferences
                AccessTokenKeeper.writeAccessToken(MainActivity.this, mAccessToken);
                Toast.makeText(MainActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收�? Code�?
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时�?
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时�??
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
     * 显示当前 Token 信息�?
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
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


    //qq登录
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

    //第三方qq登录
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
            //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参�?�api文档
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
