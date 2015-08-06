package com.example.administrator.iclub21.bean.recruitment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.administrator.iclub21.bean.ArtistHeadBean;
import com.example.administrator.iclub21.bean.artist.ArtistPicture;
import com.jeremy.Customer.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



/**
 * Created by Administrator on 2015/5/28.
 */
public class SlideShowView extends FrameLayout {

//轮播图图片数量
    private final static int IMAGE_COUNT = 5;
//自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
//自动轮播启用开关
    private final static boolean isAutoPlay = true;

//自定义轮播图的资源ID
    private int[] imagesResIds;
//放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
//放圆点的View的list
    private List<View> dotViewsList;

    private ViewPager viewPager;
//当前轮播页
    private int currentItem  = 0;
//定时任务
    private ScheduledExecutorService scheduledExecutorService;
//Handler
    private Handler handler = new Handler(){

    @Override
        public void handleMessage(Message msg) {
// TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }

    };

    private List<RecruitmentImageBean> data;
    List<ArtistPicture> artistPicture;
    List<ArtistHeadBean> artistHeadBean;

    public SlideShowView(Context context , List<RecruitmentImageBean> recruitmentImageData) {
        super(context);
        data = recruitmentImageData;
        initData();
        initUI(context);
        if(isAutoPlay){
            startPlay();
        }
// TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context , List<ArtistPicture> data,int i) {
        super(context);
        artistPicture = data;
        initData();
        initArtistUI(context);
        if(isAutoPlay){
            startPlay();
        }
// TODO Auto-generated constructor stub
    }

    public SlideShowView(Context context , List<ArtistHeadBean> data,int i,int j) {
        super(context);
        artistHeadBean = data;
        initData();
        initArtistFragmentUI(context);
        if(isAutoPlay){
            startPlay();
        }
// TODO Auto-generated constructor stub
    }
//    public SlideShowView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//// TODO Auto-generated constructor stub
//    }
//    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//// TODO Auto-generated constructor stub
//        initData();
//        initUI(context);
//        if(isAutoPlay){
//            startPlay();
//        }
//
//    }



    /**
 * 开始轮播图切换
 */
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }
/**
 * 停止轮播图切换
 */
    private void stopPlay(){
        scheduledExecutorService.shutdown();
    }
/**
 * 初始化相关Data
 */


    private void initData(){

//        imagesResIds = new int[]{
//                R.drawable.star_p,
//                R.drawable.star_n,
//                R.drawable.star_p,
//                R.drawable.star_n,
//                R.drawable.star_p,
//
//        };
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();

    }

    private View view;
/**
 * 初始化Views等UI
 */
    private void initUI(Context context){
        view = LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        for(int i=0;i<data.size();i++){//int imageID : imagesResIds){
            ImageView view =  new ImageView(context);
            BitmapUtils bitmapUtils = new BitmapUtils(context);
        if(data!=null)
            bitmapUtils.display(view, "http://www.iclubapps.com/upload/"+data.get(i).getPath());
//            view.setImageResource(imageID);
            view.setScaleType(ScaleType.FIT_XY);
            imageViewsList.add(view);

//            LinearLayout slideshow_ll = (LinearLayout)view.findViewById(R.id.slideshow_ll);
////
////            View v = new View(context);
//            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.setMargins(0, 0, 0, 50);
//            slideshow_ll.setLayoutParams(layoutParams);
//            slideshow_ll.addView(v);
//
//            dotViewsList.add(v);
        }

        Dot(data.size());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }
    //艺人详情
    private void initArtistUI(Context context){
        view = LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        for(int i=0;i<artistPicture.size();i++){//int imageID : imagesResIds){
            ImageView view =  new ImageView(context);

            BitmapUtils bitmapUtils = new BitmapUtils(context);
            if(artistPicture!=null)
                bitmapUtils.display(view, "http://www.iclubapps.com/upload/"+artistPicture.get(i).getName());
//            view.setImageResource(imageID);
            view.setScaleType(ScaleType.CENTER_CROP);
            imageViewsList.add(view);

//            LinearLayout slideshow_ll = (LinearLayout)view.findViewById(R.id.slideshow_ll);
//
//            View v = new View(context);
//            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(16, 16);
//            layoutParams.setMargins(0, 5, 0, 5);
//            v.setLayoutParams(layoutParams);
//            slideshow_ll.addView(v);
//
//            dotViewsList.add(v);
        }

//        Dot(artistPicture.size());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    //艺人模块
    private void initArtistFragmentUI(Context context){
        view = LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        for(int i=0;i<artistHeadBean.size();i++){//int imageID : imagesResIds){
            ImageView view =  new ImageView(context);
            BitmapUtils bitmapUtils = new BitmapUtils(context);
            if(artistHeadBean!=null)
                bitmapUtils.display(view, "http://www.iclubapps.com/upload/"+artistHeadBean.get(i).getPath());
//            view.setImageResource(imageID);
            view.setScaleType(ScaleType.FIT_XY);
            imageViewsList.add(view);

//            LinearLayout slideshow_ll = (LinearLayout)view.findViewById(R.id.slideshow_ll);
////            slideshow_ll.setPadding(0,20,0,0);
////
////            View v = new View(context);
//            RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//            layoutParams.setMargins(0, 0, 0, 0);
//            slideshow_ll.setLayoutParams(layoutParams);
//            slideshow_ll.addView(v);
//
//            dotViewsList.add(v);
        }

        Dot(artistHeadBean.size());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    private void Dot(int i) {

        View v_dot1 = (View)view.findViewById(R.id.v_dot1);
        View v_dot2 = (View)view.findViewById(R.id.v_dot2);
        View v_dot3 = (View)view.findViewById(R.id.v_dot3);
        View v_dot4 = (View)view.findViewById(R.id.v_dot4);
        View v_dot5 = (View)view.findViewById(R.id.v_dot5);
        View v_dot6 = (View)view.findViewById(R.id.v_dot6);
        View v_dot7 = (View)view.findViewById(R.id.v_dot7);
        View v_dot8 = (View)view.findViewById(R.id.v_dot8);
        View v_dot9 = (View)view.findViewById(R.id.v_dot9);

        switch (i) {
            case 1:
//                v_dot1.setVisibility(View.VISIBLE);
//                dotViewsList.add(v_dot1);
                break;
            case 2:
                v_dot1.setVisibility(View.VISIBLE);
                v_dot2.setVisibility(View.VISIBLE);
                dotViewsList.add(v_dot1);
                dotViewsList.add(v_dot2);

                break;
            case 3:
                v_dot1.setVisibility(View.VISIBLE);
                v_dot2.setVisibility(View.VISIBLE);
                v_dot3.setVisibility(View.VISIBLE);
                dotViewsList.add(v_dot1);
                dotViewsList.add(v_dot2);
                dotViewsList.add(v_dot3);

                break;
            case 4:
                v_dot1.setVisibility(View.VISIBLE);
                v_dot2.setVisibility(View.VISIBLE);
                v_dot3.setVisibility(View.VISIBLE);
                v_dot4.setVisibility(View.VISIBLE);
                dotViewsList.add(v_dot1);
                dotViewsList.add(v_dot2);
                dotViewsList.add(v_dot3);
                dotViewsList.add(v_dot4);

                break;
            case 5:
                v_dot1.setVisibility(View.VISIBLE);
                v_dot2.setVisibility(View.VISIBLE);
                v_dot3.setVisibility(View.VISIBLE);
                v_dot4.setVisibility(View.VISIBLE);
                v_dot5.setVisibility(View.VISIBLE);
                dotViewsList.add(v_dot1);
                dotViewsList.add(v_dot2);
                dotViewsList.add(v_dot3);
                dotViewsList.add(v_dot4);
                dotViewsList.add(v_dot5);

                break;
            case 6:
                v_dot1.setVisibility(View.VISIBLE);
                v_dot2.setVisibility(View.VISIBLE);
                v_dot3.setVisibility(View.VISIBLE);
                v_dot4.setVisibility(View.VISIBLE);
                v_dot5.setVisibility(View.VISIBLE);
                v_dot6.setVisibility(View.VISIBLE);
                dotViewsList.add(v_dot1);
                dotViewsList.add(v_dot2);
                dotViewsList.add(v_dot3);
                dotViewsList.add(v_dot4);
                dotViewsList.add(v_dot5);
                dotViewsList.add(v_dot6);

                break;
            case 7:
                v_dot1.setVisibility(View.VISIBLE);
                v_dot2.setVisibility(View.VISIBLE);
                v_dot3.setVisibility(View.VISIBLE);
                v_dot4.setVisibility(View.VISIBLE);
                v_dot5.setVisibility(View.VISIBLE);
                v_dot6.setVisibility(View.VISIBLE);
                v_dot7.setVisibility(View.VISIBLE);
                dotViewsList.add(v_dot1);
                dotViewsList.add(v_dot2);
                dotViewsList.add(v_dot3);
                dotViewsList.add(v_dot4);
                dotViewsList.add(v_dot5);
                dotViewsList.add(v_dot6);
                dotViewsList.add(v_dot7);

                break;
            case 8:
                v_dot1.setVisibility(View.VISIBLE);
                v_dot2.setVisibility(View.VISIBLE);
                v_dot3.setVisibility(View.VISIBLE);
                v_dot4.setVisibility(View.VISIBLE);
                v_dot5.setVisibility(View.VISIBLE);
                v_dot6.setVisibility(View.VISIBLE);
                v_dot7.setVisibility(View.VISIBLE);
                v_dot8.setVisibility(View.VISIBLE);

                dotViewsList.add(v_dot1);
                dotViewsList.add(v_dot2);
                dotViewsList.add(v_dot3);
                dotViewsList.add(v_dot4);
                dotViewsList.add(v_dot5);
                dotViewsList.add(v_dot6);
                dotViewsList.add(v_dot7);
                dotViewsList.add(v_dot8);

                break;
            case 9:
                v_dot1.setVisibility(View.VISIBLE);
                v_dot2.setVisibility(View.VISIBLE);
                v_dot3.setVisibility(View.VISIBLE);
                v_dot4.setVisibility(View.VISIBLE);
                v_dot5.setVisibility(View.VISIBLE);
                v_dot6.setVisibility(View.VISIBLE);
                v_dot7.setVisibility(View.VISIBLE);
                v_dot8.setVisibility(View.VISIBLE);
                v_dot9.setVisibility(View.VISIBLE);

                dotViewsList.add(v_dot1);
                dotViewsList.add(v_dot2);
                dotViewsList.add(v_dot3);
                dotViewsList.add(v_dot4);
                dotViewsList.add(v_dot5);
                dotViewsList.add(v_dot6);
                dotViewsList.add(v_dot7);
                dotViewsList.add(v_dot8);
                dotViewsList.add(v_dot9);
                break;


        }
    }

/**
 * 填充ViewPager的页面适配器
 * @author caizhiming
 */
    private class MyPagerAdapter  extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
// TODO Auto-generated method stub
//((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
// TODO Auto-generated method stub
            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
// TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
// TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
// TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
// TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
// TODO Auto-generated method stub

        }

    }
/**
 * ViewPager的监听器
 * 当ViewPager中页面的状态发生改变时调用
 * @author caizhiming
 */
    private class MyPageChangeListener implements OnPageChangeListener{

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
// TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
// 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
// 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                    viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
// TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int pos) {
// TODO Auto-generated method stub

            currentItem = pos;
            for(int i=0;i < dotViewsList.size();i++){
                if(i == pos)
                    (dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot_selected_shape);
                else {
                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_shape);
                }
            }
        }

    }

/**
 *执行轮播图切换任务
 *@author caizhiming
 */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
// TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }
/**
 263.
 * 销毁ImageView资源，回收内存
 264.
 * @author caizhiming
265.
 */
    private void destoryBitmaps() {

        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
//解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }

}