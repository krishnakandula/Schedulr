package com.silver.krish.schedulr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Fragments.ClassFragment;
import com.silver.krish.schedulr.Models.Class;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Krishna Kandula on 9/10/2016.
 */
public class AddClassActivity extends AppCompatActivity{
	@BindView(R.id.add_class_activity_floating_action_button) FloatingActionButton mFloatingActionButton;
	@BindView(R.id.add_class_activity_class_name_edit_text) EditText mClassNameEditText;
	@BindView(R.id.add_class_activity_class_num_edit_text) EditText mClassNumEditText;
	@BindView(R.id.add_class_activity_subject_edit_text) EditText mSubjectEditText;
	@BindView(R.id.add_class_activity_teacher_edit_text) EditText mTeacherEditText;

	private Unbinder mUnbinder;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_class);
		mUnbinder = ButterKnife.bind(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mUnbinder.unbind();
	}

	@OnClick(R.id.add_class_activity_floating_action_button)
	public void onClickFloatingActionButton(){
		String className = mClassNameEditText.getText().toString();
		if(className.isEmpty()){
			createErrorSnackBar("Please enter a class name");
			return;
		}
		String subjectName = mSubjectEditText.getText().toString();
		if(subjectName.isEmpty()){
			createErrorSnackBar("Please enter the subject name");
			return;
		}
		if(mClassNumEditText.getText().toString().isEmpty()){
			createErrorSnackBar("Please enter a class number");
			return;
		}
		long classNumber = Long.parseLong(mClassNumEditText.getText().toString());
		String teacherName = mTeacherEditText.getText().toString();
		if(teacherName.isEmpty()){
			createErrorSnackBar("Please enter the teacher's name");
			return;
		}

		Class newClass = new Class(className, classNumber, new Random().nextInt());
		newClass.setSubject(subjectName);
		newClass.setTeacher(teacherName);

		ClassController.getClassController().addClass(newClass);
		onBackPressed();
	}

	/**
	 * <h1>createErrorSnackBar</h1>
	 * Used as a template to create error message for input validation
	 * @param message that will be displayed in SnackBar
	 */
	private void createErrorSnackBar(String message){
		Snackbar snackbar = Snackbar.make(findViewById(R.id.add_class_activity_coordinator_layout), message, Snackbar.LENGTH_LONG);
		snackbar.show();
	}
}