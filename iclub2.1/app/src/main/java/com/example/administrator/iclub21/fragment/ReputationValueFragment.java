package com.example.administrator.iclub21.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.ReputationAdapter;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.bean.recruitment.RecruitmentListBean;
import com.example.administrator.iclub21.bean.recruitment.SendParme;
import com.example.administrator.iclub21.bean.talent.CommentBean;
import com.example.administrator.iclub21.bean.talent.ReputationValueBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReputationValueFragment extends Fragment {


    public ReputationValueFragment() {
        // Required empty public constructor
    }

    private ListView reputation_list;
    private ReputationAdapter adapter;
    private int id = -1;
    private int type = -1;
    //    private
    private TextView authenticity_tv,integrity_tv,record_tv;
    private TextView company_url,company_phone,company_mailbox,company_location,company_name_tv;
    private RecruitmentListBean recruitmentListBean;
    private TextView title_name_tv;
    private ImageView authenticity_relatively_iv,integrity_relatively_iv;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_reputation, container, false);
        ViewUtils.inject(this, view);

        reputation_list = (ListView)view.findViewById(R.id.reputation_list);
        title_name_tv = (TextView)view.findViewById(R.id.title_name_tv);

        init();
        return view;



    }


    private void init(){


//        if(i==1) {
            title_name_tv.setText("信誉值");
            View header = View.inflate(getActivity(), R.layout.reputation_head, null);//头部内容
            authenticity_tv = (TextView) header.findViewById(R.id.authenticity_tv);
            integrity_tv = (TextView) header.findViewById(R.id.integrity_tv);
            record_tv = (TextView) header.findViewById(R.id.record_tv);
            authenticity_relatively_iv = (ImageView)header.findViewById(R.id.authenticity_relatively_iv);
            integrity_relatively_iv = (ImageView)header.findViewById(R.id.integrity_relatively_iv);
            reputation_list.addHeaderView(header);//添加头部

//        }else if(i==2){
//            title_name_tv.setText("公司详情");
//            View header = View.inflate(getActivity(), R.layout.company_details_head, null);//头部内容
//            authenticity_tv = (TextView) header.findViewById(R.id.authenticity_tv);
//            integrity_tv = (TextView) header.findViewById(R.id.integrity_tv);
//            record_tv = (TextView) header.findViewById(R.id.record_tv);
//            company_url = (TextView)header.findViewById(R.id.company_url);
//            company_phone = (TextView)header.findViewById(R.id.company_phone);
//            company_mailbox = (TextView)header.findViewById(R.id.company_mailbox);
//            company_location = (TextView)header.findViewById(R.id.company_location);
//            company_name_tv = (TextView)header.findViewById(R.id.company_name_tv);
//            reputation_list.addHeaderView(header);//添加头部
//
//            recruitmentListBean = (RecruitmentListBean) getIntent().getSerializableExtra("Detail");
//
//            company_url.setText(recruitmentListBean.getWeb());
//            company_phone.setText(recruitmentListBean.getPhone());
//            company_mailbox.setText(recruitmentListBean.getEmail());
//            company_location.setText(recruitmentListBean.getAddress());
//            company_name_tv.setText(recruitmentListBean.getCompanyName());
////            company_name_tv.setText(id+"");
//
//        }

        initReputationValue();
        initcCollaborateComment();
    }

    //初始化信誉值
    private void initReputationValue(){

        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getReputationValue(405), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    SendParme<ReputationValueBean> reputationValueBean = JSONObject.parseObject(result, new TypeReference<SendParme<ReputationValueBean>>() {
                    });
                    if (reputationValueBean.getState().equals("success")) {

                        if (reputationValueBean.getValue() != null) {
                            ReputationValueBean reputationValueDate = JSONObject.parseObject(reputationValueBean.getValue(), ReputationValueBean.class);
                            authenticity_tv.setText(reputationValueDate.getAuthenticity()+"");
                            integrity_tv.setText(reputationValueDate.getIntegrity()+"");
                            record_tv.setText(reputationValueDate.getTransactionRecord()+"");
                                if(reputationValueDate.getAuthenticity()<=60){
                                    authenticity_relatively_iv.setImageResource(R.mipmap.red_down);
                                }else if(reputationValueDate.getAuthenticity()<=90){
                                    authenticity_relatively_iv.setImageResource(R.mipmap.grey_flat);
                                }else{
                                    authenticity_relatively_iv.setImageResource(R.mipmap.green_top);
                                }
                                if(reputationValueDate.getIntegrity()<=60){
                                    integrity_relatively_iv.setImageResource(R.mipmap.red_down);
                                }else if(reputationValueDate.getIntegrity()<=90){
                                    integrity_relatively_iv.setImageResource(R.mipmap.grey_flat);
                                }else{
                                    integrity_relatively_iv.setImageResource(R.mipmap.green_top);
                                }


                        }

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    //初始化合作评论
    private void initcCollaborateComment(){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getComment(id,"getCommentByBePerson.action?personid="), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    ArtistParme<CommentBean> commentBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<CommentBean>>() {
                    });
                    if (commentBean.getState().equals("success")) {

//                        if(commentBean.getValue()!=null) {
                        List<CommentBean> commentDate = commentBean.getValue();
                        adapter = new ReputationAdapter(getActivity(),commentDate);
                        reputation_list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
//                        }

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    public void back(View v){
        getActivity().finish();
    }


}
