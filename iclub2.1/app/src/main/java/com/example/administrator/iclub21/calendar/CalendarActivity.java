package com.example.administrator.iclub21.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.LayoutSizes;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.bean.recruitment.JobDetailsDialog;
import com.example.administrator.iclub21.bean.recruitment.SendParme;
import com.example.administrator.iclub21.bean.recruitment.ViewCountBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/2.
 */
public class CalendarActivity extends Activity implements View.OnClickListener, CalendarCard.OnCellClickListener {
    private ViewPager mViewPager;
    private int mCurrentIndex = 0;
    private CalendarCard[] mShowViews;
    private CalendarViewAdapter<CalendarCard> adapter;
    private SildeDirection mDirection = SildeDirection.NO_SILDE;
    enum SildeDirection {
        RIGHT, LEFT, NO_SILDE;
    }

    private LinearLayout preImgBtn, nextImgBtn;
    private TextView monthText;
    private ImageButton closeImgBtn;

    private DbUtils dbu;
    private Day user;
//    private TextView tv;
    private List<Day> list;
    public static int userType = 2;//1、商家 ，2、个人
    private String id = "";
    public static List<DayBean> dayBeanslist = new ArrayList<DayBean>();

    public int PASTDUE = 1;//已过期
    public int OFFER = 2;//邀约
    public int BEDEFEATED = 3;//加载失败
    public int ING = 4;//加载中

    private int tipsType;
    private int resumeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calendar);

        //初始化界面状态
        init();
        Bundle bundle = getIntent().getExtras();
        userType = bundle.getInt("userType");
        id = bundle.getString("Personid");
        resumeid = bundle.getInt("Resumeid");
//        Toast.makeText(CalendarActivity.this, resumeid+"", Toast.LENGTH_LONG).show();
//       initRoute();

//        tv = (TextView) findViewById(R.id.tv);

        mViewPager = (ViewPager) this.findViewById(R.id.vp_calendar);
        preImgBtn = (LinearLayout) this.findViewById(R.id.btnPreMonth);
        nextImgBtn = (LinearLayout) this.findViewById(R.id.btnNextMonth);
        monthText = (TextView) this.findViewById(R.id.tvCurrentMonth);
//        closeImgBtn = (ImageButton) this.findViewById(R.id.btnClose);
        preImgBtn.setOnClickListener((View.OnClickListener) this);
        nextImgBtn.setOnClickListener((View.OnClickListener) this);
//        closeImgBtn.setOnClickListener((View.OnClickListener) this);

        CalendarCard[] views = new CalendarCard[3];
        for (int i = 0; i < 3; i++) {
            views[i] = new CalendarCard(this, this);
        }
        adapter = new CalendarViewAdapter<CalendarCard>(views);
        setViewPager();

        dbu = DbUtils.create(this);
        user = new Day(); //这里需要注意的是User对象必须有id属性，或者有通过@ID注解的属性



//        initRoute();



    }

    private void setViewPager() {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                measureDirection(position);
                updateCalendarView(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        LayoutSizes ls = new LayoutSizes();
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ls.layoutHeigh(screenWidth,313,269));
        layoutParams.setMargins(0, 0, 0, 0);
        mViewPager.setLayoutParams(layoutParams);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPreMonth:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case R.id.btnNextMonth:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                break;
//            case R.id.btnClose:
//                finish();
//                break;
            default:
                break;
        }
    }

    public static String i="";
    private boolean leisure = true;


    @Override
    public void clickDate(CustomDate date) {

        String str = date.year+"-"+(date.month > 9 ? date.month : ("0" + date.month))+"-"+(date.day > 9 ? date.day : ("0" + date.day));

        if(DateUtil.isCurrentMonth(date)&&date.getDay()<DateUtil.getCurrentMonthDay()) {
            dialog(PASTDUE);
        }else {

            if (userType == 1) {
//            tv.setText(dayBeanslist.get(date.day-1).getDay());
                if(tipsType == -1) {
                    if (dayBeanslist.get(date.day - 1).getStatus().equals("1") && dayBeanslist.get(date.day - 1).getDay().equals(str)) {

                    } else {
                        i = str;
                    }
                }else {
                    if(tipsType==BEDEFEATED) {
                        dialog(BEDEFEATED);
                    }else {
                        dialog(ING);
                    }
                    //显示获取失败
                }


            } else if (userType == 2) {
                try {
                    list = dbu.findAll(Selector.from(Day.class).where(WhereBuilder.b("id", "!=", -1).and("day", "=", date.year + "-" + (date.month > 9 ? date.month : ("0" + date.month)) + "-" + (date.day > 9 ? date.day : ("0" + date.day)))).orderBy("id").limit(10));


                } catch (DbException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (list == null || list.size() == 0) {
                    user.setYear(date.year);
                    user.setMonth(date.month);
                    user.setDay(date.year + "-" + (date.month > 9 ? date.month : ("0" + date.month)) + "-" + (date.day > 9 ? date.day : ("0" + date.day)));
                    //			db.save(user);
                    try {
                        dbu.save(user);
                    } catch (DbException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {

                    try {
                        dbu.deleteAll(list);//(Selector.from(Day.class).where(WhereBuilder.b("id", "<", 54).and("day","=","2015-06-25")).orderBy("id").limit(10));
                    } catch (DbException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }

        updateCalendarView(7);

    }




    @Override
    public void changeDate(CustomDate date) {
        monthText.setText(date.year + "年" + date.month + "月");
        if(DateUtil.getMonth()==date.getMonth()){
            preImgBtn.setVisibility(View.INVISIBLE);
            nextImgBtn.setVisibility(View.VISIBLE);
        }else {
            preImgBtn.setVisibility(View.VISIBLE);
            nextImgBtn.setVisibility(View.INVISIBLE);
        }
        initRoute(date.year + "-" + (date.month > 9 ? date.month : ("0" + date.month)));
    }

    /**
     * 计算方向
     *
     * @param arg0
     */
    private void measureDirection(int arg0) {

        if (arg0 > mCurrentIndex) {
            mDirection = SildeDirection.RIGHT;

        } else if (arg0 < mCurrentIndex) {
            mDirection = SildeDirection.LEFT;
        }
        mCurrentIndex = arg0;
    }

    // 更新日历视图
    private void updateCalendarView(int arg0) {
        mShowViews = adapter.getAllItems();
        if (mDirection == SildeDirection.RIGHT) {
            mShowViews[arg0 % mShowViews.length].rightSlide();
        } else if (mDirection == SildeDirection.LEFT) {
            mShowViews[arg0 % mShowViews.length].leftSlide();
        }
        mDirection = SildeDirection.NO_SILDE;
    }


    private void init(){
        i="";
//        List<DayBean> list = new ArrayList<DayBean>();
//        DayBean dayBean = new DayBean();
//        dayBean.setDay("2015-08-");
//        dayBean.setStatus("0");
//        list.add(dayBean);
//        dayBeanslist = list;
//        monthText

    }

    //商家进入初始化日历
    private void initRoute(String yearAndMonth){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getRoute(id, yearAndMonth), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    ArtistParme<DayBean> dayBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<DayBean>>() {
                    });
                    if (dayBean.getState().equals("success")) {
                        dayBeanslist = dayBean.getValue();
                        updateCalendarView(7);
                        tipsType = -1;
                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                tipsType = BEDEFEATED;
                monthText.setText(s);
            }
        });
    }

    //邀约
    public void Invite(){
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getInvite(i, "13800138000", resumeid), new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    if (result != null) {
                        SendParme<ViewCountBean> viewCountBean = JSONObject.parseObject(result, new TypeReference<SendParme<ViewCountBean>>() {
                        });
                        Toast.makeText(CalendarActivity.this, i, Toast.LENGTH_LONG).show();
                        if (viewCountBean.getState().equals("success")) {
                            ViewCountBean viewCountData = JSONObject.parseObject(viewCountBean.getValue(), ViewCountBean.class);

                            if (viewCountData.getMessage().equals("success")) {
                                Toast.makeText(CalendarActivity.this, "邀约成功", Toast.LENGTH_LONG).show();

                            } else if (viewCountData.getMessage().equals("failure")) {
                                Toast.makeText(CalendarActivity.this, "邀约失败", Toast.LENGTH_LONG).show();
                            } else {
//                                Toast.makeText(CalendarActivity.this, "邀约失败", Toast.LENGTH_LONG).show();
                            }
                        }

                    }


                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        }






    private JobDetailsDialog dialog2;

    //提示框
    private void dialog(final int tipsTyp) {
        dialog2 = new JobDetailsDialog(this,tipsTyp);
//        EditText editText = (EditText) dialog.getEditText();//方法在CustomDialog中实现
        dialog2.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipsTyp==OFFER) {
                    Invite();
                }
                dialog2.dismiss();
            }
        });
        dialog2.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

//        Window window = dialog2.getWindow();
//        window.setGravity(Gravity.TOP);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        // 设置透明度为0.3
//        lp.alpha = 0.6f;
//        window.setAttributes(lp);

        dialog2.show();
    }

    public void offer(View v){
        dialog(OFFER);
    }


}