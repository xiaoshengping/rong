package com.example.administrator.iclub21;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.iclub21.adapter.ViewPagerAdapter;
import com.example.administrator.iclub21.util.HomeActivity;
import com.jeremy.Customer.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements OnClickListener, OnPageChangeListener{

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageButton experience_immediately;
    private Boolean isFirstIn = false;
    private SharedPreferences.Editor editor;

    //引导图片资源
    private static final int[] pics = {R.mipmap.guidance1, R.mipmap.guidance2, R.mipmap.guidance3 };

    //底部小店图片
    private ImageView[] dots ;

    //记录当前选中位置
    private int currentIndex;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = this.getSharedPreferences("myActivityName", 0);
        //取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = pref.getBoolean("isFirstIn", true);
        if(isFirstIn) {
            experience_immediately = (ImageButton) findViewById(R.id.experience_immediately);
            experience_immediately.setVisibility(View.INVISIBLE);
            experience_immediately.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    inti();
                }
            });

            views = new ArrayList<View>();

            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            //初始化引导图片列表
            for (int i = 0; i < pics.length; i++) {
                ImageView iv = new ImageView(this);
                iv.setLayoutParams(mParams);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setImageResource(pics[i]);
                views.add(iv);
            }
            vp = (ViewPager) findViewById(R.id.viewpager);
            //初始化Adapter
            vpAdapter = new ViewPagerAdapter(views);
            vp.setAdapter(vpAdapter);
            //绑定回调
            vp.setOnPageChangeListener(MainActivity.this);

            //初始化底部小点
//        initDots();
        }else {
            inti();
        }
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[pics.length];

        //循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);//都设为灰色
            dots[i].setOnClickListener(MainActivity.this);
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色，即选中状态
    }

    /**
     *设置当前的引导页
     */
    private void setCurView(int position)
    {
        if (position < 0 || position >= pics.length) {
            return;
        }

        vp.setCurrentItem(position);
    }

    /**
     *这只当前引导小点的选中
     */
    private void setCurDot(int positon)
    {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }

//        dots[positon].setEnabled(false);
//        dots[currentIndex].setEnabled(true);

        currentIndex = positon;
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        if(arg0==2){
            experience_immediately.setVisibility(View.VISIBLE);
        }else {
            experience_immediately.setVisibility(View.INVISIBLE);
        }
//        setCurDot(arg0);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        setCurView(position);
        setCurDot(position);
    }
    public  void inti(){
        SharedPreferences pref = this.getSharedPreferences("myActivityName", 0);
        editor = pref.edit();
        editor.putBoolean("isFirstIn", false);
        editor.commit();
        Intent intent = new Intent(this, HomeActivity.class);  //方法1
        startActivityForResult(intent, 0);
//
//        Intent intent=new Intent(this, HomeActivity.class);
//        startActivity(intent);

    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        finish();

    }
}