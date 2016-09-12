package com.silver.krish.schedulr;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.silver.krish.schedulr.Fragments.AssignmentsFragment;
import com.silver.krish.schedulr.Fragments.ClassFragment;
import com.silver.krish.schedulr.Models.Class;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements ClassFragment.OnClassItemSelectedListener{
	@BindView(R.id.main_activity_toolbar) Toolbar mToolbar;
	@BindView(R.id.main_view_pager) ViewPager mViewPager;
	@BindView(R.id.view_pager_tabs) TabLayout mTabLayout;
	@BindView(R.id.main_floating_action_button) FloatingActionButton mFloatingActionButton;

	private Unbinder mUnbinder;
	private ClassFragment mClassFragment;
	private static final int CLASS_LIST_PAGE_POSITION = 0;
	private static final int ASSIGNMENT_LIST_PAGE_POSITION = 1;
	private static final int NUMBER_OF_PAGES = 2;
	private int currentViewPage = 0;

	private ViewPagerAdapter mPagerAdapter;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mUnbinder = ButterKnife.bind(this);
		setSupportActionBar(mToolbar);

		setupTabLayout();

		//Create default Realm instance
		RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
		Realm.setDefaultConfiguration(realmConfiguration);


	}

	public class ViewPagerAdapter extends FragmentPagerAdapter{
		public ViewPagerAdapter(FragmentManager fm){
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position){
				case CLASS_LIST_PAGE_POSITION: return new ClassFragment();
				case ASSIGNMENT_LIST_PAGE_POSITION: return new AssignmentsFragment();
				default: return null;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

	}

	private void setupTabLayout(){
		mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(NUMBER_OF_PAGES);
		mTabLayout.setupWithViewPager(mViewPager, true);

		mTabLayout.getTabAt(0).setText("Classes");
		mTabLayout.getTabAt(1).setText("Tasks");

		mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);
		mTabLayout.setTabTextColors(Color.WHITE, Color.WHITE);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				switch(position){
					case CLASS_LIST_PAGE_POSITION:
						mFloatingActionButton.show();
						currentViewPage = 0;
						break;
					case ASSIGNMENT_LIST_PAGE_POSITION:
						mFloatingActionButton.show();
						currentViewPage = 1;
						break;
				}
			}
		});
	}

	@OnClick(R.id.main_floating_action_button)
	public void onClickFloatingActionButton(View v) {
		switch(currentViewPage){
			case CLASS_LIST_PAGE_POSITION:
				ClassFragment classFragment = (ClassFragment)mPagerAdapter.getItem(CLASS_LIST_PAGE_POSITION);
				if(classFragment.getClassItemViewIsSelected()){
					Toast toast = Toast.makeText(this, classFragment.getClassItemSelected().getClassName(), Toast.LENGTH_LONG);
					toast.show();
				} else{
					//TODO
//					Intent intent = new Intent(this, AddClassActivity.class);
//				    startActivity(intent);
				}

				break;
			case ASSIGNMENT_LIST_PAGE_POSITION:
				Snackbar s = Snackbar.make(findViewById(R.id.activity_main_coordinator_layout), "TASK", Snackbar.LENGTH_SHORT);
				s.show();
				break;
			default:
				break;
		}
	}

	@Override
	public void onClassItemSelected(boolean isSelected) {
		//TODO: Change this so that edit is not overwritten if even one changes
		if(isSelected) {
			//Change FAB to edit
			mFloatingActionButton.setImageResource(R.drawable.ic_action_edit);
		} else {
			mFloatingActionButton.setImageResource(R.drawable.ic_action_add);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mUnbinder.unbind();
		Realm.getDefaultInstance().close();
	}
}