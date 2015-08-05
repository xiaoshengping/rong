package com.example.administrator.iclub21;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jeremy.Customer.R;

class TestFragmentAdapter extends FragmentPagerAdapter {
	protected static final int[] CONTENT = new int[] {R.mipmap.invite_particular_select_icon,R.mipmap.iculd_beijin_icon,R.mipmap.iculd_beijin_icon,R.mipmap.iculd_beijin_icon,};

	public TestFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return TestFragment.newInstance(CONTENT[position]);
	}

	@Override
	public int getCount() {
		return CONTENT.length;
	}
}