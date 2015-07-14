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

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.InviteMessageListAdapter;
import com.example.administrator.iclub21.bean.InviteMessgaeListValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.CompanyInviteMessageActivity;
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
public class InviteMessageFragment extends Fragment {

    @ViewInject(R.id.invite_message_list_lv)
    private ListView inviteMessageLv;
    private HttpUtils httpUtils;
    private RequestParams requestParams;

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
        requestParams.addBodyParameter("value","note");
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getInviteMessageList(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
               // Log.e("inviteintiData",result);
                if (!TextUtils.isEmpty(result)){
                    ArtistParme<InviteMessgaeListValueBean> artistParme= JSONObject.parseObject(result,new TypeReference<ArtistParme<InviteMessgaeListValueBean>>(){});
                    if (artistParme.getState().equals("success")){
                       List<InviteMessgaeListValueBean> inviteMessgaeListValueBeans=  artistParme.getValue();
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
        inviteMessageLv.setAdapter(inviteMessagelistAdapter);
        inviteMessagelistAdapter.notifyDataSetChanged();
        inviteMessageLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),CompanyInviteMessageActivity.class);
                intent.putExtra("InviteMessgaeListValueBean",data.get(position));
                intent.putExtra("flage","InviteMessageFragment");
                startActivity(intent);
            }
        });

    }


}
