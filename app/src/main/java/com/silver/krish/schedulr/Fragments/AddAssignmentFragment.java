package com.silver.krish.schedulr.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.silver.krish.schedulr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Krishna Kandula on 9/18/2016.
 */
public class AddAssignmentFragment extends Fragment implements AdapterView.OnItemSelectedListener{
	@BindView(R.id.fragment_add_assignment_priority_spinner) Spinner prioritySpinner;
	@BindView(R.id.fragment_add_assignment_due_date_button) Button dueDateButton;
	@BindView(R.id.fragment_add_assignment_name_edit_text) EditText nameEditText;
	@BindView(R.id.fragment_add_assignment_description_edit_text) EditText descriptionEditText;

	private Unbinder mUnbinder;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		setupPrioritySpinner();
		return view;
	}

	@OnClick(R.id.fragment_add_assignment_priority_spinner)
	public void onClickPrioritySpinner(){

	}

	private void setupPrioritySpinner() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
				R.array.add_priority_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		prioritySpinner.setAdapter(adapter);
		prioritySpinner.setOnItemSelectedListener(this);
	}


	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mUnbinder.unbind();
	}
}