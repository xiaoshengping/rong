package com.example.administrator.iclub21.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.iclub21.adapter.ArtistDetailMusicAdater;
import com.example.administrator.iclub21.adapter.ArtistDetailVideoAdater;
import com.example.administrator.iclub21.bean.LayoutSizes;
import com.example.administrator.iclub21.bean.artist.ArtistListBean;
import com.example.administrator.iclub21.bean.recruitment.SlideShowView;
import com.example.administrator.iclub21.bean.talent.Utility;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sina.weibo.sdk.demo.R;

/**
 * Created by Administrator on 2015/6/18.
 */
public class ArtistDetailActivity extends Activity implements View.OnClickListener{

    private ListView artist_detail_lv;

    private LinearLayout artistdetail_header_ll;
    private TextView types_of_profession_tv;
    private TextView district_tv;
    private TextView name_tv;
    private TextView page_view_tv;
    private TextView essential_information_tv;
    private TextView music_tv;
    private TextView video_tv;
    private TextView represent_tv;
    private TextView appear_tv;
    private TextView company_tv;
    private TextView qq_tv;
    private TextView helpline_tv;
    private TextView emsil_tv;
    private ScrollView scrollView;
//    private SelectedCityOrPositionAdapter adAdater;

    ArtistListBean artistParme;

    private int MUSIC = 1;
    private int VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        init();
    }

    private void binding(){
        artistdetail_header_ll = (LinearLayout) findViewById(R.id.artistdetail_header_ll);
        types_of_profession_tv = (TextView) findViewById(R.id.types_of_profession_tv);
        district_tv = (TextView) findViewById(R.id.district_tv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        page_view_tv = (TextView) findViewById(R.id.page_view_tv);
        essential_information_tv = (TextView) findViewById(R.id.essential_information_tv);
        music_tv = (TextView) findViewById(R.id.music_tv);
        video_tv = (TextView) findViewById(R.id.video_tv);
        represent_tv = (TextView) findViewById(R.id.represent_tv);
        appear_tv = (TextView) findViewById(R.id.appear_tv);
        company_tv = (TextView) findViewById(R.id.company_tv);
        qq_tv = (TextView) findViewById(R.id.qq_tv);
        helpline_tv = (TextView) findViewById(R.id.helpline_tv);
        emsil_tv = (TextView) findViewById(R.id.emsil_tv);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        music_tv.setOnClickListener(this);
        video_tv.setOnClickListener(this);
        helpline_tv.setOnClickListener(this);
    }

    private void init(){
        binding();
        BitmapUtils bitmapUtils=new BitmapUtils(this);
        artistParme = (ArtistListBean) getIntent().getParcelableExtra("Detail");

        initArtistBack();

//        artistdetail_header_ll = (LinearLayout) findViewById(R.id.artistdetail_header_ll);
//        types_of_profession_tv.setText(artistParme.get);
//        district_tv.setText(artistParme.get);
        name_tv.setText(artistParme.getName());
        page_view_tv.setText(artistParme.getViewCount()+"");
        essential_information_tv.setText(artistParme.getInfo());
        if(artistParme.getEndorse().equals("")){}else {
            represent_tv.setText(artistParme.getEndorse());
        }
        if(artistParme.getShows().equals("")){}else {
            appear_tv.setText(artistParme.getShows());
        }
        company_tv.setText(artistParme.getPerson().getBEcompanyName());
        qq_tv.setText(artistParme.getPerson().getBEqq());
        helpline_tv.setText(artistParme.getPerson().getBEphone()+"  ");
        emsil_tv.setText(artistParme.getPerson().getBEemail());
        music_tv.setText("音乐("+artistParme.getArtistAlbum().size()+")");
        video_tv.setText("视频("+artistParme.getArtistMovie().size()+")");
        artist_detail_lv = (ListView)findViewById(R.id.artist_detail_lv);

        initArtistList(MUSIC);
        scrollView.smoothScrollTo(0, 0);

        //调用浏览接口
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getArtistViewCount(artistParme.getArtistid()), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }




    //初始化list内容
    private void initArtistList(int i){
        music_tv.setBackgroundColor(0x00ffffff);
        video_tv.setBackgroundColor(0x00ffffff);
        music_tv.setTextColor(0xffffffff);
        video_tv.setTextColor(0xffffffff);
        if(i==MUSIC) {
            music_tv.setBackgroundResource(R.drawable.artistdetail_button_shape);
            music_tv.setTextColor(0xffc44667);
            ArtistDetailMusicAdater admAdater = new ArtistDetailMusicAdater(this, artistParme.getArtistAlbum());
            artist_detail_lv.setAdapter(admAdater);
            Utility.setListViewHeightBasedOnChildren(artist_detail_lv);
            admAdater.notifyDataSetChanged();
        }else if(i==VIDEO){
            video_tv.setBackgroundResource(R.drawable.artistdetail_button_shape);
            video_tv.setTextColor(0xffc44667);
            ArtistDetailVideoAdater advAdater = new ArtistDetailVideoAdater(this, artistParme.getArtistMovie());
            artist_detail_lv.setAdapter(advAdater);
            Utility.setListViewHeightBasedOnChildren(artist_detail_lv);
            advAdater.notifyDataSetChanged();
        }


    }

    //艺人背景图片
    private void initArtistBack(){
        SlideShowView ssv = new SlideShowView(this, artistParme.getArtistPicture(),0);
        artistdetail_header_ll.addView(ssv);
        LayoutSizes ls = new LayoutSizes();
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ls.layoutHeigh(screenWidth,620,690));//(int) (getResources().getDimension(R.dimen.ssv_height)));
        layoutParams.setMargins(0, 0, 0, 0);
        ssv.setLayoutParams(layoutParams);
    }

    private Utility utility = new Utility();

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.music_tv:
                initArtistList(MUSIC);
                break;
            case R.id.video_tv:
                initArtistList(VIDEO);
                break;
            case R.id.helpline_tv:
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + artistParme.getPerson().getBEphone()));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    public void back(View v){

        finish();
//        video_display_lv.setVisibility(View.VISIBLE);

    }
}
