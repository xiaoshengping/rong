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

import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.MerchantInviteListAdapter;
import com.example.administrator.iclub21.bean.MerchantInviteValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.url.HttpHelper;
import com.example.administrator.iclub21.util.CooperationCommentActivity;
import com.example.administrator.iclub21.util.FailureCommentActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantSuccessfulInviteFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2<ListView> {

    @ViewInject(R.id.merchant_invite_message_list_lv)
    private PullToRefreshListView merchantInviteMessageLv;
    private HttpUtils httpUtils;

    private List<MerchantInviteValueBean> merchantInviteValueBeans;
    private MerchantInviteListAdapter inviteMessagelistAdapter;
    private int limit=10;
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

    }
    private void intiView() {
        httpUtils=new HttpUtils();
        intiListView();


    }
    private void intiData(int limit) {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }


        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getMerchantInvite(uid,"complete",limit), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                if (!TextUtils.isEmpty(result)) {
                    /*ArtistParme<MerchantInviteValueBean> artistParme = JSONObject.parseObject(result, new TypeReference<ArtistParme<MerchantInviteValueBean>>() {
                    });*/
                    HttpHelper.baseToUrl(result, new TypeReference<ArtistParme<MerchantInviteValueBean>>() {
                    }, merchantInviteValueBeans, inviteMessagelistAdapter);
                    merchantInviteMessageLv.onRefreshComplete();


                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });



    }

    private void intiListView() {
        merchantInviteValueBeans=new ArrayList<>();
        inviteMessagelistAdapter=new MerchantInviteListAdapter(merchantInviteValueBeans,getActivity());
        merchantInviteMessageLv.setAdapter(inviteMessagelistAdapter);
        merchantInviteMessageLv.setMode(PullToRefreshBase.Mode.BOTH);
        merchantInviteMessageLv.setOnRefreshListener(this);
        ILoadingLayout loadingLayout = merchantInviteMessageLv
                .getLoadingLayoutProxy();
        loadingLayout.setPullLabel("你可劲拉，拉...");// 刚下拉时，显示的提示
        loadingLayout.setRefreshingLabel("好嘞，正在刷新...");// 刷新时
        loadingLayout.setReleaseLabel("你敢放，我就敢刷新...");// 下来达到一定距离时，显示的提示
        merchantInviteMessageLv.setRefreshing();
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
                intent.putExtra("MerchantInviteValueBean", (Serializable) merchantInviteValueBeans.get(position-1));
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
                intent.putExtra("MerchantInviteValueBean", (Serializable) merchantInviteValueBeans.get(position-1));
                intent.putExtra("falgeData", "MerchantSuccessfulInviteFragment");
                startActivity(intent);
                dlg.cancel();
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        merchantInviteValueBeans.clear();
        int limit=10;
        intiData(limit);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        merchantInviteValueBeans.clear();
        limit++;
        intiData(limit);
    }
}
