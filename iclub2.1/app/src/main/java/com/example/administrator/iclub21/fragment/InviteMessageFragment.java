package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.InviteMessageListAdapter;
import com.example.administrator.iclub21.bean.InviteMessgaeListValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.url.HttpHelper;
import com.example.administrator.iclub21.util.CompanyInviteMessageActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteMessageFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2<ListView>{

    @ViewInject(R.id.invite_message_list_lv)
    //private ListView inviteMessageLv;
    private PullToRefreshListView inviteMessageLv;
    private HttpUtils httpUtils;
    private RequestParams requestParams;
    private List<InviteMessgaeListValueBean> inviteMessgaeListValueBeans;
    private InviteMessageListAdapter inviteMessagelistAdapter;
    private int offset=0;
    public InviteMessageFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_invite_message, container, false);
        ViewUtils.inject(this, view);

        inti();
        return view;
    }

    private void inti() {
           intiView();
           //intiData();
        intiListData();
        intiPullToRefresh();
        intiListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        inviteMessageLv.setRefreshing();

    }

    public void intiPullToRefresh(){
        inviteMessageLv.setMode(PullToRefreshBase.Mode.BOTH);
        inviteMessageLv.setOnRefreshListener(this);
        ILoadingLayout endLabels  = inviteMessageLv
                .getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在刷新...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        ILoadingLayout startLabels  = inviteMessageLv
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        inviteMessageLv.setRefreshing();
    }
    /**
     * 初始化数据
     *
     */
    public void intiListData(){
        inviteMessgaeListValueBeans= new  ArrayList<InviteMessgaeListValueBean>();
        inviteMessagelistAdapter=new InviteMessageListAdapter(inviteMessgaeListValueBeans,getActivity());
        inviteMessageLv.setAdapter(inviteMessagelistAdapter);
    }
    private void intiView() {
        HttpHelper.getHelper();
        httpUtils=new HttpUtils();
        requestParams=new RequestParams();



    }

    private void intiData(int offset) {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getInviteMessage(uid, "note", offset), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (!TextUtils.isEmpty(result)){
                    HttpHelper.baseToUrl(result, new TypeReference< ArtistParme<InviteMessgaeListValueBean>>(){},inviteMessgaeListValueBeans,inviteMessagelistAdapter);
                    inviteMessageLv.onRefreshComplete();

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });



    }

    private void intiListView() {

        inviteMessageLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CompanyInviteMessageActivity.class);
                intent.putExtra("InviteMessgaeListValueBean", inviteMessgaeListValueBeans.get(position-1));
                intent.putExtra("flage", "InviteMessageFragment");
                startActivity(intent);
            }
        });

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        inviteMessgaeListValueBeans.clear();
        int offset=0;
        intiData(offset);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

        offset=offset+10;
        intiData(offset);
    }
}
