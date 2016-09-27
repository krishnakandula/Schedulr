package com.silver.krish.schedulr;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Fragments.ClassFragment;
import com.silver.krish.schedulr.Models.Class;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
//TODO: Finish writing ClassDetailActivity
public class ClassDetailActivity extends AppCompatActivity {
	@BindView(R.id.class_activity_detail_toolbar) Toolbar mToolbar;
	@BindView(R.id.activity_class_detail_subject_text_view) TextView subjectTextView;
	@BindView(R.id.activity_class_detail_teacher_text_view) TextView teacherTextView;

	private Class currentClass;

	private Unbinder mUnbinder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_detail);
		mUnbinder = ButterKnife.bind(this);
		getClassInformation();
		setupToolbar();

		subjectTextView.setText(currentClass.getSubject() + " " + currentClass.getClassNumber());
		teacherTextView.setText(currentClass.getTeacher());
	}

	private void getClassInformation(){
		Intent intent = getIntent();
		String subject = intent.getStringExtra(ClassFragment.CLICKED_CLASS_SBJ_KEY);
		long number = intent.getLongExtra(ClassFragment.CLICKED_CLASS_NUM_KEY, 1);
		currentClass = ClassController.getClassController().getClass(subject, number);
	}

	private void setupToolbar(){
		mToolbar.setTitle(currentClass.getClassName());
		mToolbar.setTitleTextColor(Color.WHITE);
	}
}
