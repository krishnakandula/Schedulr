package com.silver.krish.schedulr.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silver.krish.schedulr.Adapters.TasksAdapter;
import com.silver.krish.schedulr.Constants;
import com.silver.krish.schedulr.Controllers.AssignmentController;
import com.silver.krish.schedulr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Krishna Kandula on 9/10/2016.
 */
public class AssignmentsFragment extends Fragment {
	@BindView(R.id.assignment_recycler_view) RecyclerView assignmentRecyclerView;

	private Unbinder mUnbinder;
	private TasksAdapter mTasksAdapter;
	@Override
	public void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_assignments, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		setupAssignmentsRecyclerView();

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		refreshAssignmentList();
	}

	private void setupAssignmentsRecyclerView(){
		assignmentRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constants.getAssignmentGridLayoutColumns(),
				StaggeredGridLayoutManager.VERTICAL));
		mTasksAdapter = new TasksAdapter(getContext());
		assignmentRecyclerView.setAdapter(mTasksAdapter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mUnbinder.unbind();
	}

	private void refreshAssignmentList(){
		AssignmentController.getAssignmentController().updateAssignmentList();
		mTasksAdapter.notifyDataSetChanged();
	}
}
