package com.silver.krish.schedulr;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.silver.krish.schedulr.Fragments.AddAssignmentFragment;

public class AddAssignmentActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_assignment);
		FragmentManager fragmentManager = getSupportFragmentManager();
		AddAssignmentFragment fragment = new AddAssignmentFragment();
		fragmentManager.beginTransaction().add(R.id.activity_add_assignment_container, fragment).commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}