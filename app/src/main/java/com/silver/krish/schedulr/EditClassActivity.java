package com.silver.krish.schedulr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Models.Class;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

public class EditClassActivity extends AppCompatActivity {

	@BindView(R.id.add_class_activity_class_name_edit_text) EditText classNameEditText;
	@BindView(R.id.add_class_activity_subject_edit_text) EditText subjectEditText;
	@BindView(R.id.add_class_activity_class_num_edit_text) EditText classNumEditText;
	@BindView(R.id.add_class_activity_teacher_edit_text) EditText teacherEditText;
	@BindView(R.id.add_class_activity_floating_action_button) FloatingActionButton mFloatingActionButton;
	@BindView(R.id.add_class_activity_toolbar) Toolbar mToolbar;

	private Class selectedClass;
	private Unbinder mUnbinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_class);
		mUnbinder = ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		getClassFromIntent();
		setViewNames();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.activity_edit_class, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_edit_class_delete:
				String subject = selectedClass.getSubject();
				long classNumber = selectedClass.getClassNumber();
				boolean wasDeleted = ClassController.getClassController().deleteClass(subject, classNumber);
				ClassController.getClassController().updateClassList();
				Intent resultIntent = new Intent();
				resultIntent.putExtra(Constants.getEditClassNumberKey(), classNumber);
				resultIntent.putExtra(Constants.getEditClassSubjectKey(), subject);
				if(wasDeleted) {
					setResult(Activity.RESULT_OK, resultIntent);
				} else {
					setResult(Activity.RESULT_CANCELED, resultIntent);
				}
				onBackPressed();
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void setupActionBar(){
		mToolbar.setTitle("Edit " + selectedClass.getClassName());
		mToolbar.setTitleTextColor(Color.WHITE);
	}

	private void getClassFromIntent(){
		Intent intent = getIntent();
		String classSubject = intent.getStringExtra(Constants.getEditClassSubjectKey());
		long classNumber = intent.getLongExtra(Constants.getEditClassNumberKey(), 0);
		selectedClass = ClassController.getClassController().getClass(classSubject, classNumber);
	}

	private void setViewNames(){
		classNameEditText.setText(selectedClass.getClassName());
		subjectEditText.setText(selectedClass.getSubject());
		classNumEditText.setText("" + selectedClass.getClassNumber());
		teacherEditText.setText(selectedClass.getTeacher());
	}

	@OnClick(R.id.add_class_activity_floating_action_button)
	protected void onClickFloatingActionButton(){
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		selectedClass.setClassName(classNameEditText.getText().toString());
		selectedClass.setSubject(subjectEditText.getText().toString());
		selectedClass.setTeacher(teacherEditText.getText().toString());
		selectedClass.setClassNumber(Long.parseLong(classNumEditText.getText().toString()));
		realm.copyToRealmOrUpdate(selectedClass);
		realm.commitTransaction();
		onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mUnbinder.unbind();
	}
}