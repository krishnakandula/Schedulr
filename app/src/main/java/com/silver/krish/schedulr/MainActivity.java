package com.silver.krish.schedulr;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.github.rubensousa.floatingtoolbar.FloatingToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.main_view_pager) ViewPager mViewPager;
	@BindView(R.id.view_pager_tabs) TabLayout mTabLayout;
	private ViewPagerAdapter mPagerAdapter;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mTabLayout.setupWithViewPager(mViewPager, true);
		setTabLayoutText();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	public class ViewPagerAdapter extends FragmentPagerAdapter{
		public static final int numberOfPages = 3;
		public ViewPagerAdapter(FragmentManager fm){
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return new ClassFragment();
		}

		@Override
		public int getCount() {
			return numberOfPages;
		}
	}

	//TODO: Change this method to actual text later
	private void setTabLayoutText(){
		for(int i = 0; i < 3; i++){
			mTabLayout.getTabAt(i).setText(String.format("%d", i + 1));
		}
	}
}
