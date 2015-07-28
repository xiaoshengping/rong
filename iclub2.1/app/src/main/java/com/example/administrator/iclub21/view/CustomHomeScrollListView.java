package com.example.administrator.iclub21.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CustomHomeScrollListView extends ListView {

	public CustomHomeScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

    public CustomHomeScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public CustomHomeScrollListView(Context context) {
        super(context);
    }

    /** 测量方法 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
