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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.MerchantInviteListAdapter;
import com.example.administrator.iclub21.bean.MerchantInviteValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.CooperationCommentActivity;
import com.example.administrator.iclub21.util.FailureCommentActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantSuccessfulInviteFragment extends Fragment {

    @ViewInject(R.id.merchant_invite_message_list_lv)
    private ListView merchantInviteMessageLv;
    private HttpUtils httpUtils;
    private RequestParams requestParams;

    private List<MerchantInviteValueBean> merchantInviteValueBeans;
    public MerchantSuccessfulInviteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_merchant_successful_invite, container, false);
        ViewUtils.inject(this, view);
        inti();

        return view;
    }
    private void inti() {
        intiView();
        intiData();
    }
    private void intiView() {
        httpUtils=new HttpUtils();
        requestParams=new RequestParams();


    }
    private void intiData() {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }
        if (!TextUtils.isEmpty(uid)){
            requestParams.addBodyParameter("uid",uid);
        }
        requestParams.addBodyParameter("value", "accept");
        requestParams.addBodyParameter("offset","0");
        requestParams.addBodyParameter("limit","10");

        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getMerchantInvite(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("inviteintiData", result);
                if (!TextUtils.isEmpty(result)) {
                    ArtistParme<MerchantInviteValueBean> artistParme = JSONObject.parseObject(result, new TypeReference<ArtistParme<MerchantInviteValueBean>>() {
                    });
                    if (artistParme.getState().equals("success")) {
                        merchantInviteValueBeans = artistParme.getValue();
                        intiListView();

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });



    }

    private void intiListView() {

        MerchantInviteListAdapter inviteMessagelistAdapter=new MerchantInviteListAdapter(merchantInviteValueBeans,getActivity());
        merchantInviteMessageLv.setAdapter(inviteMessagelistAdapter);
        inviteMessagelistAdapter.notifyDataSetChanged();
        merchantInviteMessageLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showExitGameAlert(position);
            }
        });

    }

    //对话框
    private void showExitGameAlert(final int position) {
        final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.shrew_exit_dialog);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        tailte.setText("双方合作情况");
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setText("失败");
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FailureCommentActivity.class);
                intent.putExtra("MerchantInviteValueBean", (Serializable) merchantInviteValueBeans.get(position));
                intent.putExtra("falgeData", "MerchantSuccessfulInviteFragment");
                startActivity(intent);
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText("成功");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CooperationCommentActivity.class);
                intent.putExtra("MerchantInviteValueBean", (Serializable) merchantInviteValueBeans.get(position));
                intent.putExtra("falgeData", "MerchantSuccessfulInviteFragment");
                startActivity(intent);
                dlg.cancel();
            }
        });
    }

}
