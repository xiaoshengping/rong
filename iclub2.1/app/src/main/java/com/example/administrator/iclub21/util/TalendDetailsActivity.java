package com.example.administrator.iclub21.util;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.iclub21.adapter.VideoDisplyAdapter;
import com.example.administrator.iclub21.bean.talent.PicturesshowMoreActivity;
import com.example.administrator.iclub21.bean.talent.ResumeMovie;
import com.example.administrator.iclub21.bean.talent.ResumeMusic;
import com.example.administrator.iclub21.bean.talent.ResumePicture;
import com.example.administrator.iclub21.bean.talent.RoundAngleImageView;
import com.example.administrator.iclub21.bean.talent.SpaceImageDetailActivity;
import com.example.administrator.iclub21.bean.talent.TalentValueBean;
import com.example.administrator.iclub21.calendar.CalendarActivity;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.jeremy.Customer.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/3.
 */
public class TalendDetailsActivity extends Activity {

    private ImageView talen_back_iv;//人才背景
    private ImageView talent_head_civ;//头像
    private TextView talent_name_tv;//名字
    private ImageView talent_sex_iv;//性别
    private TextView talent_pageview_tv;//浏览数
    private TextView talent_age_tv;//年龄
    private TextView talent_workplace_tv;//工作地点
    private TextView talent_position_tv;//职位
    private TextView self_introduction_tv;//自我介绍
    private TextView work_experience_tv;//工作经历
    private RelativeLayout contact_information_rl;//联系方式
    private TextView qq_tv;//qq
    private TextView email_tv;//email
    private TextView phone_tv;//手机号码
    private TextView contact_information_tips_tv;//联系方式提示
    private ListView video_display_lv;
//    private ScrollView scrollView;
    private TextView picturesshow_more_tv;
    private LinearLayout picturesshow_ll;
    private RoundAngleImageView picturesshow1_iv,picturesshow2_iv,picturesshow3_iv,picturesshow4_iv;
    private List<ResumePicture> resumePictureData;

    private boolean register = false;//登录状态

    private boolean movieShow = true;
    private boolean musicShow = true;
    private int musicNum;

    private String states = null;//用户类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talendetails);
        init();
    }

    private void bindingheader(View view) {
        talen_back_iv = (ImageView) view.findViewById(R.id.talen_back_iv);
        talent_head_civ = (ImageView) view.findViewById(R.id.talent_head_civ);
        talent_name_tv = (TextView) view.findViewById(R.id.talent_name_tv);
        talent_sex_iv = (ImageView) view.findViewById(R.id.talent_sex_iv);
        talent_pageview_tv = (TextView) view.findViewById(R.id.talent_pageview_tv);
        talent_age_tv = (TextView) view.findViewById(R.id.talent_age_tv);
        talent_workplace_tv = (TextView) view.findViewById(R.id.talent_workplace_tv);
        talent_position_tv = (TextView) view.findViewById(R.id.talent_position_tv);
        self_introduction_tv = (TextView) view.findViewById(R.id.self_introduction_tv);
        work_experience_tv = (TextView) view.findViewById(R.id.work_experience_tv);
        contact_information_rl = (RelativeLayout) view.findViewById(R.id.contact_information_rl);
        qq_tv = (TextView) view.findViewById(R.id.qq_tv);
        email_tv = (TextView) view.findViewById(R.id.email_tv);
        phone_tv = (TextView) view.findViewById(R.id.phone_tv);
        contact_information_tips_tv = (TextView) view.findViewById(R.id.contact_information_tips_tv);
        picturesshow_more_tv = (TextView) view.findViewById(R.id.picturesshow_more_tv);
        picturesshow_ll = (LinearLayout) view.findViewById(R.id.picturesshow_ll);
        //点击更多
        picturesshow_more_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalendDetailsActivity.this, PicturesshowMoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList) resumePictureData);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_not);
            }
        });

        picturesshow1_iv = (RoundAngleImageView) view.findViewById(R.id.picturesshow1_iv);
        picturesshow2_iv = (RoundAngleImageView) view.findViewById(R.id.picturesshow2_iv);
        picturesshow3_iv = (RoundAngleImageView) view.findViewById(R.id.picturesshow3_iv);
        picturesshow4_iv = (RoundAngleImageView) view.findViewById(R.id.picturesshow4_iv);
        ViewTreeObserver vto2 = picturesshow1_iv.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                picturesshow1_iv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                picturesshow1_iv.setLayoutParams(new LinearLayout.LayoutParams(picturesshow1_iv.getWidth(), picturesshow1_iv.getWidth()));
                picturesshow2_iv.setLayoutParams(new LinearLayout.LayoutParams(picturesshow1_iv.getWidth(), picturesshow1_iv.getWidth()));
                picturesshow3_iv.setLayoutParams(new LinearLayout.LayoutParams(picturesshow1_iv.getWidth(), picturesshow1_iv.getWidth()));
                picturesshow4_iv.setLayoutParams(new LinearLayout.LayoutParams(picturesshow1_iv.getWidth(), picturesshow1_iv.getWidth()));
//                picturesshow1_ib.setAdjustViewBounds(true);
//                picturesshow1_ib.setMinimumWidth(picturesshow1_ib.getWidth()*2);
//                picturesshow1_ib.setMinimumHeight(picturesshow1_ib.getWidth()*2);
//                picturess/how1_ib.append("\n\n"+imageView.getHeight()+","+imageView.getWidth());
            }
        });


//        video_display_lv = (ListView) findViewById(R.id.video_display_lv);
//        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
    }

    private TalentValueBean talentValueBean;

    private void init() {

        //获取登录状态
        SQLhelper sqLhelper=new SQLhelper(this);
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        states=null;
        while (cursor.moveToNext()) {
            states = cursor.getString(4);

        }
        if (TextUtils.isEmpty(states)||states.equals("1")){
            register = false;
        }else if(states.equals("2")){
            register = true;
        }else if(states.equals("3")){
            register = true;
        }


        video_display_lv = (ListView) findViewById(R.id.video_display_lv);
        View header = View.inflate(this, R.layout.video_display_list_header, null);//头部内容
        bindingheader(header);


//        initMovieList();





        BitmapUtils bitmapUtils=new BitmapUtils(this);
//        bitmapUtils.display(viewHodle.imageIcon, AppUtilsUrl.ImageBaseUrl + data.get(position).getUsericon());
//        Bundle bundle=this.getIntent().getExtras();
//        ArrayList list2 = bundle.getParcelableArrayList("list");
        talentValueBean = (TalentValueBean) getIntent().getSerializableExtra("Detail");
//        status = bundle.getInt("Status");
        resumePictureData = talentValueBean.getResumePicture();
        if(resumePictureData.size()==0){
            picturesshow_ll.setVisibility(View.GONE);
        }
        if(resumePictureData.size()>=1) {
            bitmapUtils.display(picturesshow1_iv, AppUtilsUrl.ImageBaseUrl + resumePictureData.get(0).getPath());
            picturesshow1_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TalendDetailsActivity.this, SpaceImageDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList("list", (ArrayList)resumePictureData);
                    bundle.putInt("num", 0);
                    bundle.putInt("MaxNum",4);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.spaceimagedetail_in,R.anim.out_to_not);
                }
            });
        }
        if(resumePictureData.size()>=2){
            bitmapUtils.display(picturesshow2_iv, AppUtilsUrl.ImageBaseUrl + resumePictureData.get(1).getPath());
            picturesshow2_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TalendDetailsActivity.this, SpaceImageDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList("list", (ArrayList) resumePictureData);
                    bundle.putInt("num", 1);
                    bundle.putInt("MaxNum",4);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.spaceimagedetail_in,R.anim.out_to_not);
                }
            });
        }
        if(resumePictureData.size()>=3){
            bitmapUtils.display(picturesshow3_iv, AppUtilsUrl.ImageBaseUrl + resumePictureData.get(2).getPath());
            picturesshow3_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TalendDetailsActivity.this, SpaceImageDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList("list", (ArrayList) resumePictureData);
                    bundle.putInt("num", 2);
                    bundle.putInt("MaxNum",4);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.spaceimagedetail_in,R.anim.out_to_not);
                }
            });
        }
        if(resumePictureData.size()>=4){
            bitmapUtils.display(picturesshow4_iv, AppUtilsUrl.ImageBaseUrl + resumePictureData.get(3).getPath());
            picturesshow4_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TalendDetailsActivity.this, SpaceImageDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList("list", (ArrayList) resumePictureData);
                    bundle.putInt("num",3);
                    bundle.putInt("MaxNum", 4);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.spaceimagedetail_in,R.anim.out_to_not);
                }
            });
        }


        if(talentValueBean.getResumeUserbg().equals("")){}else {
            bitmapUtils.display(talen_back_iv, AppUtilsUrl.ImageBaseUrl + talentValueBean.getResumeUserbg());
        }
        if(talentValueBean.getUsericon().equals("")){}else {
            bitmapUtils.display(talent_head_civ, AppUtilsUrl.ImageBaseUrl + talentValueBean.getUsericon());
        }
        talent_name_tv.setText(talentValueBean.getResumeZhName());
//        talent_name_tv.setText(talentValueBean.getResumeUserbg());
        if(talentValueBean.getResumeSex()==0) {
            talent_sex_iv.setImageResource(R.mipmap.man_icon);
        }else {
            talent_sex_iv.setImageResource(R.mipmap.girl_icon);
        }
        talent_pageview_tv.setText("浏览数:"+talentValueBean.getResumeViewCount() + "");
        talent_age_tv.setText(talentValueBean.getResumeAge()+"");
        talent_workplace_tv.setText(talentValueBean.getResumeWorkPlace());
        talent_position_tv.setText(talentValueBean.getResumeJobCategoryName());
        self_introduction_tv.setText(talentValueBean.getResumeInfo());
        work_experience_tv.setText(talentValueBean.getResumeWorkExperience());
        if(register) {
            contact_information_rl.setVisibility(View.VISIBLE);
            contact_information_tips_tv.setVisibility(View.GONE);
        }else {
            contact_information_rl.setVisibility(View.GONE);
            contact_information_tips_tv.setVisibility(View.VISIBLE);
        }
        qq_tv.setText(talentValueBean.getResumeQq());
        email_tv.setText(talentValueBean.getResumeEmail());
        phone_tv.setText(talentValueBean.getResumeMobile());
//        video_display_lv.setVisibility(View.GONE);
        video_display_lv.addHeaderView(header);//添加头部
        initMovieList();
        //浏览
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getTalenViewCount(talentValueBean.getResumeid()), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    private VideoDisplyAdapter videoAdapter;


    private void initMovieList(){

//        seekBar = (SeekBar) findViewById(R.id.seekBar);
//        vv_video = (VideoView) findViewById(R.id.vv_videoview);
//
//        btn_play = (Button) findViewById(R.id.btn_play);
//        btn_pause = (Button) findViewById(R.id.btn_pause);
//        btn_replay = (Button) findViewById(R.id.btn_replay);
//        btn_stop = (Button) findViewById(R.id.btn_stop);
//
//
//        btn_play.setOnClickListener(click);
//        btn_pause.setOnClickListener(click);
//        btn_replay.setOnClickListener(click);
//        btn_stop.setOnClickListener(click);
//
//        // 为进度条添加进度更改事件
//        seekBar.setOnSeekBarChangeListener(change);



        List<ResumeMovie> resumeMovieData = talentValueBean.getResumeMovie();
        List<ResumeMusic> resumeMusicsData = talentValueBean.getResumeMusic();
        videoAdapter = new VideoDisplyAdapter(resumeMovieData,resumeMusicsData,this);
        video_display_lv.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
        //初始化音乐作品条目
        musicNum = talentValueBean.getResumeMovie().size() + 2;

        //视屏、音频隐藏显示功能
        video_display_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    if (movieShow) {
                        musicNum = 2;
                        movieShow = false;
                    } else {
                        musicNum = talentValueBean.getResumeMovie().size() + 2;;
                        movieShow = true;
                    }
                    videoAdapter.hideShowTool("Movie", movieShow, musicShow);
                }
                if (position == musicNum) {
                    if (musicShow) {
                        musicShow = false;
                    } else {
                        musicShow = true;
                    }
                    videoAdapter.hideShowTool("Music",movieShow , musicShow);
                }

                videoAdapter.notifyDataSetChanged();


            }
        });



//        talen_back_iv.setFocusable(true);
//        talen_back_iv.setFocusableInTouchMode(true);
//        talen_back_iv.requestFocus();
//        talen_back_iv.requestFocusFromTouch();

    }




    //返回
    public void back(View v){

        finish();
//        video_display_lv.setVisibility(View.VISIBLE);

    }
    //邀约
    public void call_for(View v){

        if (TextUtils.isEmpty(states)||states.equals("1")){
            Toast.makeText(TalendDetailsActivity.this, "非登录状态或非商家类型", Toast.LENGTH_LONG).show();
        }else if(states.equals("2")){
            Toast.makeText(TalendDetailsActivity.this, "非商家类型", Toast.LENGTH_LONG).show();
        }else if(states.equals("3")){
            Intent intent = new Intent(TalendDetailsActivity.this, CalendarActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("userType", 1);
            bundle.putString("Personid", talentValueBean.getPersonid() + "");
            bundle.putInt("Resumeid", talentValueBean.getResumeid());
//        Toast.makeText(this, talentValueBean.getPersonid()+"", Toast.LENGTH_LONG).show();
            intent.putExtras(bundle);
            startActivity(intent);
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_not);
        }

    }
    //信誉值
    public void reputation_value(View v){
        Intent intent = new Intent(TalendDetailsActivity.this, ReputationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("UserType",1);
        bundle.putInt("Personid", talentValueBean.getResumeid());
//        bundle.putInt("Resumeid",talentValueBean.getResumeid());
//        Toast.makeText(this, talentValueBean.getPersonid()+"", Toast.LENGTH_LONG).show();
        intent.putExtras(bundle);
        startActivity(intent);
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_not);
    }

}
