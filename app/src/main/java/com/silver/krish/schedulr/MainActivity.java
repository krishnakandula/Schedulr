package com.silver.krish.schedulr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	//TODO: Change usage of constants so Constants class is used
	private static final int CLASS_LIST_PAGE_POSITION = 0;
	private static final int ASSIGNMENT_LIST_PAGE_POSITION = 1;
	private static final int NUMBER_OF_PAGES = 2;
	private static final String EDIT_CLASS_KEY = "EDIT_CLASS";
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
						onClassItemSelected(isClassesFabEditIconShown());
						break;
					case ASSIGNMENT_LIST_PAGE_POSITION:
						mFloatingActionButton.show();
						currentViewPage = 1;
						//If fab edit icon is shown, change it to add icon
						onClassItemSelected(false);
						break;
				}
			}
		});
	}

	@OnClick(R.id.main_floating_action_button)
	public void onClickFloatingActionButton(View v) {
		switch(currentViewPage){
			case CLASS_LIST_PAGE_POSITION:
				if(isClassesFabEditIconShown()){
					ClassFragment classFragment = (ClassFragment)mPagerAdapter.getItem(CLASS_LIST_PAGE_POSITION);
					//ClassItemViews are long selected
					//Open EditClassActivity
					Class selectedClass = classFragment.getClassItemSelected();
					Intent intent = new Intent(this, EditClassActivity.class);
					intent.putExtra(Constants.getEditClassSubjectKey(), selectedClass.getSubject());
					intent.putExtra(Constants.getEditClassNumberKey(), selectedClass.getClassNumber());
					startActivityForResult(intent, Constants.getEditClassIntentRequestCode());
				} else{
					Intent intent = new Intent(this, AddClassActivity.class);
				    startActivity(intent);
				}

				break;
			case ASSIGNMENT_LIST_PAGE_POSITION:
				//Start AddAssignmentActivity
				Intent intent = new Intent(this, AddAssignmentActivity.class);
				startActivity(intent);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.getEditClassIntentRequestCode()) {
			if(resultCode == Activity.RESULT_OK){
				String subject = data.getStringExtra(Constants.getEditClassSubjectKey());
				long number = data.getLongExtra(Constants.getEditClassNumberKey(), 0);
				String message = String.format("%s %d was deleted", subject, number);
				makeSnackbar(message, Snackbar.LENGTH_LONG);

				//Set classItemViewIsSelected in ClassFragment to null
				ClassFragment fragment = (ClassFragment) mPagerAdapter.getItem(CLASS_LIST_PAGE_POSITION);
				fragment.setClassItemSelected(null);
				fragment.setClassItemViewIsSelected(false);
				onClassItemSelected(false);
			}
		}
	}

	private void makeSnackbar(String message, int length){
		Snackbar s = Snackbar.make(findViewById(R.id.activity_main_coordinator_layout), message, length);
		s.show();
	}

	private boolean isClassesFabEditIconShown(){
		ClassFragment classFragment = (ClassFragment)mPagerAdapter.getItem(CLASS_LIST_PAGE_POSITION);
		return classFragment.getClassItemViewIsSelected();
	}
}