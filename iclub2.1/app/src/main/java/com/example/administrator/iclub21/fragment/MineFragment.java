package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.util.AmendAboutActivity;
import com.example.administrator.iclub21.util.AmendPswActivity;
import com.example.administrator.iclub21.util.InformationActivity;
import com.example.administrator.iclub21.util.LoginActivity;
import com.example.administrator.iclub21.util.MerchantActivity;
import com.example.administrator.iclub21.util.ResumeActivity;
import com.example.administrator.iclub21.util.RoleActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.WeiXinShareContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.login_cancel)
    private LinearLayout cancel;
    @ViewInject(R.id.imageView)
    private ImageView headTv;
    @ViewInject(R.id.mine_name)
    private TextView mineName;
    @ViewInject(R.id.mine_role_layout)
    private LinearLayout roleLayout;
    @ViewInject(R.id.information_tv)
    private TextView  informationTv;
    @ViewInject(R.id.amend_psw_tv)
    private TextView amendPswTv;
     @ViewInject(R.id.about_tv)
    private TextView amendAboutTv;
    private String state;
    @ViewInject(R.id.share_sdk_layout)
    private LinearLayout shareLayout;
    //分享
    private  UMSocialService mController;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_mine, container, false);
            ViewUtils.inject(this, view);

        init();
        return view;
    }

    private void init() {
        cancel.setOnClickListener(this);
        headTv.setOnClickListener(this);
        roleLayout.setOnClickListener(this);
        informationTv.setOnClickListener(this);
        amendPswTv.setOnClickListener(this);
        amendAboutTv.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
             intiSocial();
        SharedPreferences setting = getActivity().getSharedPreferences("myMineFragment", 0);
        Boolean user_first = setting.getBoolean("FIRST",true);
        if(user_first){//第一次
            setting.edit().putBoolean("FIRST", false).commit();
            Toast.makeText(getActivity(), "第一次", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "不是第一次", Toast.LENGTH_LONG).show();
        }


    }

    private void intiSocial() {
// 首先在您的Activity中添加如下成员变量
         mController = UMServiceFactory.getUMSocialService("com.umeng.share");
// 设置分享内容
        mController.setShareContent("我开始用【iclub】App了,全新的演艺招聘模式，专业的娱乐行业服务平台!");
// 设置分享图片, 参数2为图片的url地址
       /* mController.setShareMedia(new UMImage(getActivity(),
                "http://www.umeng.com/images/pic/banner_module_social.png"));*/
        mController.setShareMedia(new UMImage(getActivity(), R.mipmap.icon));

         //微信
        String appID = "wx4083a90ea6888404";
        String appSecret = "45d30589a4f8a3efe7ae617c746f6b8c";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(getActivity(),appID,appSecret);
        wxHandler.addToSocialSDK();
       // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(),appID,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        //设置分享文字
        weixinContent.setShareContent("我开始用【iclub】App了,全新的演艺招聘模式，专业的娱乐行业服务平台!");
        //设置title
        weixinContent.setTitle("微信分享");
       //设置分享内容跳转URL
        weixinContent.setTargetUrl("http://www.iclubapps.com/");
        //设置分享图片
        weixinContent.setShareImage(new UMImage(getActivity(), R.mipmap.icon));
        mController.setShareMedia(weixinContent);



        //qq好友
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), "1102291619",
                "aPJ8P5pCeUTKK0Id");
        qqSsoHandler.addToSocialSDK();
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent("我开始用【iclub】App了,全新的演艺招聘模式，专业的娱乐行业服务平台!");
        qqShareContent.setTitle("QQ分享");
        qqShareContent.setShareImage(new UMImage(getActivity(), R.mipmap.icon));
        qqShareContent.setTargetUrl("http://www.iclubapps.com/");
        mController.setShareMedia(qqShareContent);
         //qq空间
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(), "1102291619",
                "aPJ8P5pCeUTKK0Id");

        qZoneSsoHandler.addToSocialSDK();
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent("我开始用【iclub】App了,全新的演艺招聘模式，专业的娱乐行业服务平台!");
        qzone.setTargetUrl("http://www.iclubapps.com/");
        qzone.setTitle("QQ空间分享");
        qzone.setShareImage(new UMImage(getActivity(), R.mipmap.icon));
        mController.setShareMedia(qzone);
       // https://api.weibo.com/oauth2/default.html
        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }

        if (uid!=null){
            cancel.setVisibility(View.VISIBLE);
            //MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + imageIcon, headTv, MyAppliction.RoundedOptions);
            mineName.setVisibility(View.GONE);
        }else {
            mineName.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);

        }

        cursor.close();
        db.close();




    }

    @Override
    public void onClick(View v) {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        String states=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);
            states = cursor.getString(4);


        }

        switch (v.getId()){
            case R.id.imageView:
                if (uid!=null){
                    cancel.setVisibility(View.VISIBLE);
                   // MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + imageIcon, headTv, MyAppliction.RoundedOptions);
                    MyAppliction.showToast("您已经登录了!");
                }else {
                    cancel.setVisibility(View.GONE);
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
                cursor.close();
                db.close();
                break;
            case R.id.login_cancel:
                showExitGameAlert();
                break;
            case  R.id.mine_role_layout:
                if (TextUtils.isEmpty(states)||states.equals("1")){
                    Intent intentRole = new Intent(getActivity(), RoleActivity.class);
                    startActivity(intentRole);
                }else if(states.equals("2")){
                    Intent intentAdd=new Intent(getActivity(),ResumeActivity.class);
                    startActivity(intentAdd);

                }else if(states.equals("3")){
                      Intent merchantIntent=new Intent(getActivity(),MerchantActivity.class);
                     startActivity(merchantIntent);

                }
                cursor.close();

                break;
            case R.id.information_tv:
                Intent informationIntent=new Intent(getActivity(),InformationActivity.class);
                startActivity(informationIntent);
                break;
            case R.id.amend_psw_tv:
                if (!TextUtils.isEmpty(uid)){
                    Intent amendIntent=new Intent(getActivity(),AmendPswActivity.class);
                    startActivity(amendIntent);
                }else {
                    showPswGameAlert();
                }


                break;
            case R.id.about_tv:
                Intent aboutIntent=new Intent(getActivity(),AmendAboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.share_sdk_layout:
//                Intent shareItent=new Intent(getActivity(),ShareActivity.class);
//                startActivity(shareItent);\
                mController.openShare(getActivity(), false);
                break;

        }
    }





    //对话框
    private void showExitGameAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.shrew_exit_dialog);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        tailte.setText("确定注销账号？");
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setText("确定");
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancel.setVisibility(View.GONE);
                SQLhelper sqLhelper = new SQLhelper(getActivity());
                SQLiteDatabase db = sqLhelper.getWritableDatabase();
                Cursor cursor = db.query("user", null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String uid = cursor.getString(0);
                    if (uid != null) {
                        db.delete("user", null, null);

                    }
                }
                mineName.setVisibility(View.VISIBLE);
                cursor.close();
                db.close();
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }

    private void showPswGameAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.shrew_exit_dialog);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        tailte.setText("您还没有登录");
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setText("登录");
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 退出应用...
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }
}
