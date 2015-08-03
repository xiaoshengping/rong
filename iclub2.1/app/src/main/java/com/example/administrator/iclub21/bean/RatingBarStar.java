package com.example.administrator.iclub21.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremy.Customer.R;


/**
 * Created by Administrator on 2015/6/29.
 */
public class RatingBarStar {
    View view;
    private LayoutInflater mInflater;
    public void getRatingBarStar(Context context ,LinearLayout linearLayout,int num){
        TextView text1 = (TextView)linearLayout.findViewById(R.id.text1);
        TextView text2 = (TextView)linearLayout.findViewById(R.id.text2);
        TextView text3 = (TextView)linearLayout.findViewById(R.id.text3);
        TextView text4 = (TextView)linearLayout.findViewById(R.id.text4);
        TextView text5 = (TextView)linearLayout.findViewById(R.id.text5);

        text1.setText(context.getString(R.string.star_hidden));
        text2.setText(context.getString(R.string.star_hidden));
        text3.setText(context.getString(R.string.star_hidden));
        text4.setText(context.getString(R.string.star_hidden));
        text5.setText(context.getString(R.string.star_hidden));

        if(num>=1) {
            text1.setText(context.getString(R.string.star_bright));
        }
        if(num>=2) {
            text2.setText(context.getString(R.string.star_bright));
        }
        if(num>=3) {
            text3.setText(context.getString(R.string.star_bright));
        }
        if(num>=4) {
            text4.setText(context.getString(R.string.star_bright));
        }
        if(num>=5) {
            text5.setText(context.getString(R.string.star_bright));
        }

    }
}
