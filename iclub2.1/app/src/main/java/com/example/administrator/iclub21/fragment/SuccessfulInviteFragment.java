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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.InviteMessageListAdapter;
import com.example.administrator.iclub21.bean.InviteMessgaeListValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.CooperationCommentActivity;
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
public class SuccessfulInviteFragment extends Fragment {


    @ViewInject(R.id.invite_successful_list_lv)
    private ListView inviteSuccessfulListLv;
    private HttpUtils httpUtils;
    private RequestParams requestParams;

    private List<InviteMessgaeListValueBean> inviteMessgaeListValueBeans;

    public SuccessfulInviteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_successful_invite, container, false);
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
        requestParams.addBodyParameter("value", "complete");
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getInviteMessageList(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                // Log.e("inviteintiData",result);
                if (!TextUtils.isEmpty(result)) {
                    ArtistParme<InviteMessgaeListValueBean> artistParme = JSONObject.parseObject(result, new TypeReference<ArtistParme<InviteMessgaeListValueBean>>() {
                    });
                    if (artistParme.getState().equals("success")) {
                        inviteMessgaeListValueBeans = artistParme.getValue();
                        intiListView(inviteMessgaeListValueBeans);

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });



    }

    private void intiListView(final List<InviteMessgaeListValueBean> data) {

        InviteMessageListAdapter inviteMessagelistAdapter=new InviteMessageListAdapter(data,getActivity());
        inviteSuccessfulListLv.setAdapter(inviteMessagelistAdapter);
        inviteMessagelistAdapter.notifyDataSetChanged();
        inviteSuccessfulListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText("成功");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CooperationCommentActivity.class);
                intent.putExtra("inviteMessgaeListValueBeans", (Serializable) inviteMessgaeListValueBeans.get(position));
                intent.putExtra("falgeData","SuccessfulInviteFragment");
                startActivity(intent);
                dlg.cancel();
            }
        });
    }



}
