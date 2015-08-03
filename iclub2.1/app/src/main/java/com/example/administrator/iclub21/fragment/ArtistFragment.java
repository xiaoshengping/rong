package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.ArtistListAdapter;
import com.example.administrator.iclub21.bean.ArtistHeadBean;
import com.example.administrator.iclub21.bean.artist.ArtistConditionSelectActivity;
import com.example.administrator.iclub21.bean.artist.ArtistListBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.bean.artist.ArtistSeekActivity;
import com.example.administrator.iclub21.bean.recruitment.SlideShowView;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.ArtistDetailActivity;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

//import com.example.administrator.iclub21.adapter.ArtistPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {
//       @ViewInject(R.id.artist_pager)
//    private ViewPager artistPager;
    private HttpUtils httpUtils;
    @ViewInject(R.id.artist_list_gridView)
    private GridView artistGridView;
    @ViewInject(R.id.artist_area_tv)
    private TextView artist_area_tv;
    @ViewInject(R.id.artist_sex_tv)
    private TextView artist_sex_tv;
    @ViewInject(R.id.artist_tupe_tv)
    private TextView artist_tupe_tv;
    @ViewInject(R.id.artist_title_search_ib)
    private ImageButton artist_title_search_ib;
    @ViewInject(R.id.back_ib)
    private ImageButton back_ib;
    @ViewInject(R.id.fascrollView)
    private ScrollView fascrollView;
    @ViewInject(R.id.progressbar)
    private ProgressBar progressbar;
    @ViewInject(R.id.londing_tip)
    private TextView londing_tip;

    List<ArtistHeadBean> headDate=new ArrayList<ArtistHeadBean>();

    private int AREA = 1;
    private int SEX = 2;
    private int TUPE = 3;

    ArtistParme<ArtistListBean> artistParme;
    List<ArtistListBean> artistListData;

    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_artist, container, false);
        ViewUtils.inject(this, view);
             inti();
        return view;
    }

    private void inti() {
        fascrollView.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        httpUtils=new HttpUtils();
        initPager();

        initListData("", "", "");
        londing_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inti();
            }
        });

    }

    private void initListData(String area,String sex,String tupe) {
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getArtistList(area, sex, tupe), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    artistParme = JSONObject.parseObject(result, new TypeReference<ArtistParme<ArtistListBean>>() {
                    });
                    if ("success".equals(artistParme.getState())) {
                        artistListData = artistParme.getValue();
                        //intiGridView(artistListData);
//                      Log.e("name", artistParme.getValue().get(0).getArtistPicture().get(0).getName())  ;
                        ArtistListAdapter adapter = new ArtistListAdapter(artistParme.getValue(), getActivity());
                        artistGridView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        artistGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Intent intent = new Intent(getActivity(), JobDetailsActivity.class);  //方法1
//                                Bundle bundle=new Bundle();
//                                bundle.putSerializable("Detail",recruitmentListData.get(position-1));
//                                intent.putExtras(bundle);
//                                startActivity(intent);
                                Intent intent = new Intent(getActivity(), ArtistDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("Detail", artistListData.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        intiHeadData();

                    }
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

                Log.e("onFailure", s);
                progressbar.setVisibility(View.GONE);
                londing_tip.setVisibility(View.VISIBLE);
            }
        });

        artist_area_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), SelectedCityOrPositionActivity.class);  //方法1
//                intent.putExtra("Status", areaBean.POSITION);
//                startActivityForResult(intent, areaBean.POSITION);

                Intent intent = new Intent(getActivity(), ArtistConditionSelectActivity.class);//方法1
                intent.putExtra("Screen", AREA);
                startActivityForResult(intent, AREA);
                getActivity().overridePendingTransition(R.anim.music_in, R.anim.out_to_not);
            }
        });
        artist_sex_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), SelectedCityOrPositionActivity.class);  //方法1
//                intent.putExtra("Status", areaBean.POSITION);
//                startActivityForResult(intent, areaBean.POSITION);

                Intent intent = new Intent(getActivity(), ArtistConditionSelectActivity.class);//方法1
                intent.putExtra("Screen", SEX);
                startActivityForResult(intent,SEX);
                getActivity().overridePendingTransition(R.anim.music_in, R.anim.out_to_not);
            }
        });
        artist_tupe_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), SelectedCityOrPositionActivity.class);  //方法1
//                intent.putExtra("Status", areaBean.POSITION);
//                startActivityForResult(intent, areaBean.POSITION);

                Intent intent = new Intent(getActivity(), ArtistConditionSelectActivity.class);//方法1
                intent.putExtra("Screen", TUPE);
                startActivityForResult(intent,TUPE);
                getActivity().overridePendingTransition(R.anim.music_in, R.anim.out_to_not);
            }
        });

        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initListData("","","");
                back_ib.setVisibility(View.INVISIBLE);
                artist_area_tv.setText("区域");
                artist_sex_tv.setText("性别");
                artist_tupe_tv.setText("类型");
            }
        });
        artist_title_search_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArtistSeekActivity.class);//方法1
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.music_in, R.anim.out_to_not);
            }
        });


    }

   /* private void intiGridView( List<ArtistListBean> artistListData) {
        ArtistListAdapter adapter=new ArtistListAdapter(artistListData,getActivity());
        artistGridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }*/

    private void intiHeadData() {
          HttpUtils headHttpUtils=new HttpUtils();
        headHttpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getArtistHead(), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                 String result=responseInfo.result;
                if (result!=null){
                    ArtistParme<ArtistHeadBean> headBean= JSONObject.parseObject(result, new TypeReference<ArtistParme<ArtistHeadBean>>() {
                    });
                    if ("success".equals(headBean.getState())){

                        SlideShowView ssv = new SlideShowView(getActivity(), headBean.getValue(),0,0);
                        LinearLayout header_ll = (LinearLayout)getActivity().findViewById(R.id.header_ll);
                        header_ll.addView(ssv);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (getResources().getDimension(R.dimen.ssv_height)));
                        layoutParams.setMargins(0, 0, 0, 0);
                        ssv.setLayoutParams(layoutParams);

                       // headDate.addAll(headBean.getValue());
//                        ArtistPagerAdapter adapter=new ArtistPagerAdapter(getActivity().getSupportFragmentManager(),headBean.getValue());
//                        artistPager.setAdapter(adapter);
                        fascrollView.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                progressbar.setVisibility(View.GONE);
                londing_tip.setVisibility(View.VISIBLE);
            }
        });

        fascrollView.smoothScrollTo(0, 0);
    }

    private void initPager() {


    }

    private String area="",sex="",tupe="";

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bundle bundle = data.getExtras();
        if(bundle.getString("Area").equals("")){}else {
            area = bundle.getString("Area");
            artist_area_tv.setText(bundle.getString("AreaName"));
        }
        if(bundle.getString("Sex").equals("")){}else {
            sex = bundle.getString("Sex");
            artist_sex_tv.setText(bundle.getString("SexName"));
        }
        if(bundle.getString("Tupe").equals("")){}else {
            tupe = bundle.getString("Tupe");
            artist_tupe_tv.setText(bundle.getString("TupeName"));
        }
        initListData(area.toString(),sex.toString(),tupe.toString());
        back_ib.setVisibility(View.VISIBLE);




    }


}
