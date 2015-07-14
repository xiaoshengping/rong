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

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.MerchantInviteListAdapter;
import com.example.administrator.iclub21.bean.MerchantInviteValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.ResumeListParticularActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantAcceptInviteFragment extends Fragment {

    @ViewInject(R.id.merchant_invite_message_list_lv)
    private ListView merchantInviteMessageLv;
    private HttpUtils httpUtils;
    private RequestParams requestParams;
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
                        List<MerchantInviteValueBean> inviteMessgaeListValueBeans = artistParme.getValue();
                        intiListView(inviteMessgaeListValueBeans);

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });



    }

    private void intiListView(final List<MerchantInviteValueBean> data) {

        MerchantInviteListAdapter inviteMessagelistAdapter=new MerchantInviteListAdapter(data,getActivity());
        merchantInviteMessageLv.setAdapter(inviteMessagelistAdapter);
        inviteMessagelistAdapter.notifyDataSetChanged();
        merchantInviteMessageLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ResumeListParticularActivity.class);
                intent.putExtra("resumeValueBeans", data.get(position).getInviteResume());
                intent.putExtra("flage", "MerchantInviteMessageFragment");
                startActivity(intent);
            }
        });

    }

}
