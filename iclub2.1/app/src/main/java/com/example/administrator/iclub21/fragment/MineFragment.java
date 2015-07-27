package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.AmendAboutActivity;
import com.example.administrator.iclub21.util.AmendPswActivity;
import com.example.administrator.iclub21.util.InformationActivity;
import com.example.administrator.iclub21.util.LoginActivity;
import com.example.administrator.iclub21.util.MerchantActivity;
import com.example.administrator.iclub21.util.ResumeActivity;
import com.example.administrator.iclub21.util.RoleActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.login_cancel)
    private LinearLayout cancel;
    @ViewInject(R.id.mine_head_iv)
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

    }

    @Override
    public void onResume() {
        super.onResume();
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        String imageIcon=null;
        String name=null;
        String personid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);
            imageIcon=cursor.getString(3);
            name=cursor.getString(2);
            personid=cursor.getString(5);

        }

        if (uid!=null){
            cancel.setVisibility(View.VISIBLE);
            MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + imageIcon, headTv, MyAppliction.RoundedOptions);
            mineName.setText(name);
        }else {
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
        String imageIcon=null;
        String name=null;
        String personid=null;
        String states=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);
            imageIcon=cursor.getString(3);
            name=cursor.getString(2);
            states = cursor.getString(4);
            personid=cursor.getString(5);

        }

        switch (v.getId()){
            case R.id.mine_head_iv:
                if (uid!=null){
                    cancel.setVisibility(View.VISIBLE);
                    MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + imageIcon, headTv, MyAppliction.RoundedOptions);
                    mineName.setText(name);
                    Log.e("personid", personid);
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
               /* SQLhelper sqLhelper=new SQLhelper(getActivity());
                SQLiteDatabase db= sqLhelper.getWritableDatabase();
                Cursor cursor=db.query("user", null, null, null, null, null, null);
                String states=null;
                while (cursor.moveToNext()) {
                   states = cursor.getString(3);

                }*/

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
                Intent amendIntent=new Intent(getActivity(),AmendPswActivity.class);
                startActivity(amendIntent);
                break;
            case R.id.about_tv:
                Intent aboutIntent=new Intent(getActivity(),AmendAboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.share_sdk_layout:
//                Intent shareItent=new Intent(getActivity(),ShareActivity.class);
//                startActivity(shareItent);\
                showShare();
                break;

        }
    }
    //分享
    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(getActivity());
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
        tailte.setText("确定注册账号？");
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
                    String states = cursor.getString(0);
                    if (states != null) {
                        db.delete("user", null, null);
                    }
                }
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


}
