package com.silver.krish.schedulr.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.silver.krish.schedulr.Controllers.AssignmentController;
import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Models.Assignment;
import com.silver.krish.schedulr.Models.Class;
import com.silver.krish.schedulr.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by Krishna Kandula on 9/18/2016.
 */
public class AddAssignmentFragment extends Fragment implements AdapterView.OnItemSelectedListener{
	@BindView(R.id.fragment_add_assignment_priority_spinner) Spinner prioritySpinner;
	@BindView(R.id.fragment_add_assignment_class_spinner) Spinner classesSpinner;
	@BindView(R.id.fragment_add_assignment_due_date_button) Button dueDateButton;
//	@BindView(R.id.fragment_add_assignment_name_edit_text) EditText nameEditText;
	@BindView(R.id.fragment_add_assignment_description_edit_text) EditText descriptionEditText;
	@BindView(R.id.fragment_add_assignment_fab) FloatingActionButton fab;

	private Unbinder mUnbinder;

	private static final int DATE_PICKER_FRAGMENT_REQUEST_CODE = 1;
	private static final String DATE_PICKER_FRAGMENT_TAG = "DATE_PICKER_DIALOG";
	private static final String LOG_TAG = AddAssignmentFragment.class.getSimpleName();

	private Date assignmentDate;
	private String assignmentDescription;
	private Long classNumber;
	private String classSubject;
	@Override
	public void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		setupPrioritySpinner();
		setupClassSpinner();
		setupDueDateButton();
		return view;
	}

	private void setupPrioritySpinner() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
				R.array.add_priority_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		prioritySpinner.setAdapter(adapter);
	}

	private void setupDueDateButton(){
		//Have button display current date
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		StringBuilder dateString= new StringBuilder("").append(month + "/").append(day + "/").append(year);
		dueDateButton.setText(dateString.toString());
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
		classesSpinner.setOnItemSelectedListener(this);
	}

	@OnClick(R.id.fragment_add_assignment_due_date_button)
	public void onClickDueDateButton(){
		DialogFragment fragment = new DatePickerFragment();
		fragment.setTargetFragment(this, DATE_PICKER_FRAGMENT_REQUEST_CODE);
		fragment.setCancelable(true);
		fragment.show(getChildFragmentManager(), DATE_PICKER_FRAGMENT_TAG);
	}

	@OnClick(R.id.fragment_add_assignment_fab)
	public void onClickFab(){
		//TODO: Add assignment to assigment list
		if(descriptionEditText.getText().toString().isEmpty()){
			makeSnackbar("Please enter an assignment description");
			return;
		}

		assignmentDescription = descriptionEditText.getText().toString();
		if(assignmentDate == null){
			Calendar c = Calendar.getInstance();
			assignmentDate = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		}
		Assignment assignment = new Assignment(assignmentDate, assignmentDescription);
		if(classNumber != null && classSubject != null){
			assignment.setClassNumber(classNumber);
			assignment.setSubject(classSubject);
			AssignmentController.getAssignmentController().addAssignment(assignment);
		}

		getActivity().onBackPressed();
	}

	private void makeSnackbar(String message){
		Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.fragment_add_assignment_coordinator),
				"Please enter an assignment name", Snackbar.LENGTH_LONG);
		snackbar.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case DATE_PICKER_FRAGMENT_REQUEST_CODE:
				if(resultCode == Activity.RESULT_OK){
					int day = data.getIntExtra(DatePickerFragment.DAY_KEY, 1);
					int month = data.getIntExtra(DatePickerFragment.MONTH_KEY, 1);
					int year = data.getIntExtra(DatePickerFragment.YEAR_KEY, 2016);
					assignmentDate = new Date(year, month, day);

					//Change due date button text
					StringBuilder formattedDate = new StringBuilder("" + month).append("/").append(day).append("/").append(year);
					dueDateButton.setText(formattedDate.toString());
				} else {
					//Do nothing
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String selectedClass = parent.getItemAtPosition(position).toString();
		Pattern p = Pattern.compile("\\w*\\b");
		Matcher m = p.matcher(selectedClass);
		if(m.find()){
			classSubject = m.group(0).toString();
		}
		//find last space to get classNumber
		try {
			classNumber = Long.parseLong(selectedClass.substring(selectedClass.lastIndexOf(' ') + 1, selectedClass.length()));
		} catch(NumberFormatException e){
			Log.e(LOG_TAG, e.getMessage(), e);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		//Do nothing
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