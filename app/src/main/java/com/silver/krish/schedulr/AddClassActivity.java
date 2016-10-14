package com.silver.krish.schedulr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Dialogs.ColorPickerDialog;
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
public class AddClassActivity extends AppCompatActivity implements ColorPickerDialog.OnColorChosenListener{
	@BindView(R.id.add_class_activity_floating_action_button) FloatingActionButton mFloatingActionButton;
	@BindView(R.id.add_class_activity_class_name_edit_text) EditText mClassNameEditText;
	@BindView(R.id.add_class_activity_class_num_edit_text) EditText mClassNumEditText;
	@BindView(R.id.add_class_activity_subject_edit_text) EditText mSubjectEditText;
	@BindView(R.id.add_class_activity_teacher_edit_text) EditText mTeacherEditText;
	@BindView(R.id.add_class_activity_toolbar) Toolbar mToolbar;
	@BindView(R.id.add_class_activity_color_picker_button) Button mColorPickerButton;
	private Unbinder mUnbinder;
	private String colorCode = Constants.ColorCodes.colors[0];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_class);
		mUnbinder = ButterKnife.bind(this);
		setupToolbar();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mUnbinder.unbind();
	}

	private void setupToolbar(){
		mToolbar.setTitle("New Class");
		mToolbar.setTitleTextColor(Color.WHITE);
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
		newClass.setColorCode(colorCode);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Toast toast = Toast.makeText(this, ":)", Toast.LENGTH_SHORT);
		toast.show();
	}

	@OnClick(R.id.add_class_activity_color_picker_button)
	public void onClickColorPickerButton(){
		showColorPickerDialog();
	}

	private void showColorPickerDialog(){
		FragmentManager fm = getSupportFragmentManager();
		ColorPickerDialog dialog = new ColorPickerDialog();
		dialog.show(fm, "DIALOG");
	}

	@Override
	public void onColorChosen(String colorCode) {
		Toast.makeText(this, colorCode, Toast.LENGTH_SHORT).show();
		mColorPickerButton.setBackgroundColor(Color.parseColor(colorCode));
		this.colorCode = colorCode;
	}
}