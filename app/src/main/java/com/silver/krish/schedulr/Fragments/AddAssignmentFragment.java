package com.silver.krish.schedulr.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silver.krish.schedulr.R;

/**
 * Created by Krishna Kandula on 9/18/2016.
 */
public class AddAssignmentFragment extends Fragment{
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}