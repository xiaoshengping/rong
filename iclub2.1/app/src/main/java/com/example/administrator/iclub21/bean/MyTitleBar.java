package com.example.administrator.iclub21.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.R;


/**
 * Created by Administrator on 2015/7/20.
 */
public class MyTitleBar extends RelativeLayout {

    private TextView title_left,title_name;
    private ImageView title_right;

    private String title_name_text = "yuf";
    private boolean title_left_show = false;
    private boolean title_right_show = false;

    public MyTitleBar(Context context) {
        super(context);
    }
    public MyTitleBar(final Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_titlebar, this);
        title_left = (TextView)findViewById(R.id.title_left);
        title_name = (TextView)findViewById(R.id.title_name);
        title_right = (ImageView)findViewById(R.id.title_right);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        title_name_text = a.getString(R.styleable.MyTitleBar_title_name_text);
        title_left_show = a.getBoolean(R.styleable.MyTitleBar_title_left_show, false);
        title_right_show = a.getBoolean(R.styleable.MyTitleBar_title_left_show, false);

//        title_name.setText(title_name_text);
        title_name.setTextSize((int) (getHeight() * 0.8));
        title_left.setTextSize((int)(getHeight()*0.8));
        if(title_left_show){
            title_left.setVisibility(View.VISIBLE);
        }else {
            title_left.setVisibility(View.GONE);
        }
        if(title_right_show){
            title_right.setVisibility(View.VISIBLE);
        }else {
            title_right.setVisibility(View.GONE);
        }

//        a.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响

//        roundHeight= a.getResourceId(R.styleable.MyRelativeLayout_drawable,roundHeight);

//        setGravity(Gravity.CENTER);

//        textView = new TextView(context);
////        imageView = new ImageView(context);
//        textView.setText(str);
//        textView.setTextSize(20);
//        textView.setGravity(Gravity.CENTER);
////        textView.set
//        addView(textView);
//        imageView = new ImageView(context);
//        imageView.setImageResource(roundHeight);
//        addView(imageView);



//        imageView=(ImageView) findViewById(R.id.imageView1);
//        textView=(TextView)findViewById(R.id.textView1);
    }
}
