package com.example.administrator.iclub21.fragment;


import android.content.Intent;
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

import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.LoginActivity;
import com.example.administrator.iclub21.util.RoleActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoleTalentsFragment extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.talents_image)
    private ImageView talentsImage;
   @ViewInject(R.id.cloess_talents_tv)
    private TextView cloessTalents;
    @ViewInject(R.id.cwei_talents_layou)
    private LinearLayout cweiTalents;

    private int imageUrl;
    public RoleTalentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_role_talents, container, false);
        ViewUtils.inject(this,view);
         inti();
        return view;
    }

    private void inti() {
        intiView();


    }

    private void intiView() {
        cloessTalents.setOnClickListener(this);
        cweiTalents.setOnClickListener(this);


        Bundle bundle = getArguments();
        imageUrl= bundle.getInt("iamgeUrl");
        talentsImage.setBackgroundResource(imageUrl);
        if (imageUrl==R.mipmap.talents_icon_d||imageUrl==R.mipmap.merchantion_icon_d){
            cloessTalents.setVisibility(View.VISIBLE);
            cweiTalents.setVisibility(View.VISIBLE);


        }


    }


    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.cloess_talents_tv:
                   Intent intent=new Intent(getActivity(), RoleActivity.class);
                   startActivity(intent);
                   getActivity().finish();

                   break;
               case R.id.cwei_talents_layou:

                   SQLhelper sqLhelper = new SQLhelper(getActivity());
                   SQLiteDatabase db = sqLhelper.getWritableDatabase();
                   Cursor cursor = db.query("user", null, null, null, null, null, null);
                   String uid = null;
                   String state=null;
                   while (cursor.moveToNext()) {
                       uid = cursor.getString(0);
                       state = cursor.getString(4);
                   }
                   if (!TextUtils.isEmpty(uid)){
                       if (imageUrl==R.mipmap.talents_icon_d){
                           if (state.equals("2")){
                               MyAppliction.showToast("您已经成为人才角色了!");
                           }else if (state.equals("3")){
                               MyAppliction.showToast("您已经成为商家角色了!");

                           }else {
                               intiData(uid, "2");


                           }


                       }else if(imageUrl==R.mipmap.merchantion_icon_d){
                           if (state.equals("2")){
                               MyAppliction.showToast("您已经成为人才角色了!");
                           }else if (state.equals("3")){
                               MyAppliction.showToast("您已经成为商家角色了!");

                           }else {
                               intiData(uid,"3");
                           }


                       }



                   }else {
                       showExitGameAlert();
                   }
                   cursor.close();
                   db.close();
                   break;

           }
    }

    private void intiData(String uid,String state) {

        HttpUtils httpUtils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("uid",uid);
        requestParams.addBodyParameter("state",state);
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddRoleTalents(),requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                getActivity().finish();


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });





    }

    private void showExitGameAlert() {
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
