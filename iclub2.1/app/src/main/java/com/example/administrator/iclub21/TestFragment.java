package com.example.administrator.iclub21;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public final class TestFragment extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";
	
	public static TestFragment newInstance(int content) {
		TestFragment fragment = new TestFragment();

		fragment.mContent = content;
		
		return fragment;
	}
	
	private int mContent = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
//			mContent = savedInstanceState.getString(KEY_CONTENT);
//		}
		
//		TextView text = new TextView(getActivity());
//		text.setText(mContent);
//		text.setTextSize(20 * getResources().getDisplayMetrics().density);
//		text.setPadding(20, 20, 20, 20);
//		text.setGravity(Gravity.CENTER);
		ImageView iv = new ImageView(getActivity());
		iv.setImageResource(mContent);
		iv.setScaleType(ScaleType.CENTER);
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setGravity(Gravity.CENTER);
		layout.addView(iv);
		
		return layout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
//		outState.putString(KEY_CONTENT, mContent);
	}
}
