package com.silver.krish.schedulr;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.silver.krish.schedulr.Adapters.TasksAdapter;
import com.silver.krish.schedulr.Controllers.AssignmentController;
import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Fragments.ClassFragment;
import com.silver.krish.schedulr.Models.Assignment;
import com.silver.krish.schedulr.Models.Class;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.RealmList;

public class ClassDetailActivity extends AppCompatActivity implements TasksAdapter.OnTaskClickedListener{
	@BindView(R.id.class_activity_detail_toolbar) Toolbar mToolbar;
	@BindView(R.id.activity_class_detail_subject_text_view) TextView subjectTextView;
	@BindView(R.id.activity_class_detail_teacher_text_view) TextView teacherTextView;
	@BindView(R.id.class_detail_activity_assignment_recycler_view) RecyclerView mRecyclerView;

	private Class currentClass;
	private Unbinder mUnbinder;
	private TasksAdapter mTasksAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_detail);
		mUnbinder = ButterKnife.bind(this);
		getClassInformation();
		setupToolbar();
		setupRecyclerView();
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

	private void setupRecyclerView(){
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constants.getAssignmentGridLayoutColumns(),
				StaggeredGridLayoutManager.VERTICAL));
		List<Assignment> assignments = currentClass.getAssignments();
		mTasksAdapter = new TasksAdapter(this, assignments, this);
		mRecyclerView.setAdapter(mTasksAdapter);
	}

	@Override
	public void onTaskClicked(Assignment assignment) {
//		Toast.makeText(this, "TEST CLICK", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLongClicked(Assignment assignment, int position) {
		//Delete Assignment
		AssignmentController.getAssignmentController().deleteAssignment(assignment);
		mTasksAdapter.notifyItemRemoved(position);

		Toast.makeText(this, "Assignment Deleted", Toast.LENGTH_LONG).show();
	}
}
