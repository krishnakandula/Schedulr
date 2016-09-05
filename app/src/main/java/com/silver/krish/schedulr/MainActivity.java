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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.github.rubensousa.floatingtoolbar.FloatingToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity {
	@BindView(R.id.main_activity_toolbar) Toolbar mToolbar;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		FragmentManager fm = getSupportFragmentManager();
		MainFragment fragment = new MainFragment();
		fm.beginTransaction().add(R.id.main_activity_container, fragment).commit();
	}
}
