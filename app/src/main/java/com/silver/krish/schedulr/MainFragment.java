package com.silver.krish.schedulr;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Krishna Kandula on 9/3/2016.
 */
public class MainFragment extends Fragment{
	@BindView(R.id.main_view_pager) ViewPager mViewPager;
	@BindView(R.id.view_pager_tabs) TabLayout mTabLayout;
	private Unbinder mUnbinder;
	private ViewPagerAdapter mPagerAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(2);
		mTabLayout.setupWithViewPager(mViewPager, true);
		setTabLayoutText();
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
		mTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		//TODO: Fix tab layout text colors
		return view;
	}

	public class ViewPagerAdapter extends FragmentPagerAdapter {
		public static final int numberOfPages = 2;
		public ViewPagerAdapter(FragmentManager fm){
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position){
				case 0: return new ClassFragment();
				case 1: return new ViewPagerPlaceHolderFragment();
				default: return null;
			}
		}

		@Override
		public int getCount() {
			return numberOfPages;
		}
	}

	//TODO: Change this method to actual text later
	private void setTabLayoutText(){
		mTabLayout.getTabAt(0).setText("Classes");
		mTabLayout.getTabAt(1).setText("Tasks");

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mUnbinder.unbind();
	}
}
