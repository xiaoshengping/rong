package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.MerchantInviteListAdapter;
import com.example.administrator.iclub21.bean.MerchantInviteValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.url.HttpHelper;
import com.example.administrator.iclub21.util.ResumeListParticularActivity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantAcceptInviteFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2<ListView> {

    @ViewInject(R.id.merchant_invite_message_list_lv)
    private PullToRefreshListView merchantInviteMessageLv;
    private HttpUtils httpUtils;
    private  List<MerchantInviteValueBean> merchantInviteValueBeans;
    private MerchantInviteListAdapter inviteMessagelistAdapter;
    private int limit=10;
    public MerchantAcceptInviteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_merchant_accept_invite, container, false);
        ViewUtils.inject(this, view);
        inti();

        return view;
    }
    private void inti() {
        intiView();
        intiListView();
    }
    private void intiView() {
        httpUtils=new HttpUtils();



    }
    private void intiData(int limit) {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }



        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getMerchantInvite(uid,"accept",limit), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("inviteintiData", result);
                if (!TextUtils.isEmpty(result)) {


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
                Intent intent = new Intent(getActivity(), ResumeListParticularActivity.class);
                intent.putExtra("resumeValueBeans", merchantInviteValueBeans.get(position).getInviteResume());
                intent.putExtra("flage", "MerchantInviteMessageFragment");
                startActivity(intent);
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
