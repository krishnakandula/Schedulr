package com.silver.krish.schedulr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Krishna Kandula on 9/3/2016.
 */
public class AddClassFragment extends Fragment{

	public static AddClassFragment newInstance(){
		Bundle bundle = new Bundle();
		AddClassFragment classFragment = new AddClassFragment();
		classFragment.setArguments(bundle);

		return classFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_class, container, false);
		return view;
	}


}
