package com.silver.krish.schedulr.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Models.Class;
import com.silver.krish.schedulr.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Krishna Kandula on 9/18/2016.
 */
public class AddAssignmentFragment extends Fragment{
	@BindView(R.id.fragment_add_assignment_priority_spinner) Spinner prioritySpinner;
	@BindView(R.id.fragment_add_assignment_class_spinner) Spinner classesSpinner;
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
		setupClassSpinner();
		return view;
	}

	private void setupPrioritySpinner() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
				R.array.add_priority_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		prioritySpinner.setAdapter(adapter);
	}

	private void setupClassSpinner(){
		//Get all classes programmatically
		ArrayList<String> classDescriptions = new ArrayList<>();
		List<Class> classArrayList = ClassController.getClassController().getClassList();
		for(Class c : classArrayList){
			StringBuilder classDetails = new StringBuilder(c.getSubject());
			classDetails.append(" ").append(c.getClassNumber());
			classDescriptions.add(classDetails.toString());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
				android.R.layout.simple_spinner_item,
				classDescriptions);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		classesSpinner.setAdapter(adapter);
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