package com.silver.krish.schedulr.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
	private AssignmentsViewAdapter mViewAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_assignments, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		return view;
	}

	public class AssignmentsViewAdapter extends RecyclerView.Adapter<AssignmentViewHolder>{
		@Override
		public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return null;
		}

		@Override
		public void onBindViewHolder(AssignmentViewHolder holder, int position) {

		}

		@Override
		public int getItemCount() {
			return 0;
		}
	}

	public class AssignmentViewHolder extends RecyclerView.ViewHolder{
		public AssignmentViewHolder(View itemView) {
			super(itemView);
		}
	}

	private void setupAssignmentsRecyclerView(){
//		assignmentRecyclerView.setLayoutManager();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mUnbinder.unbind();
	}
}
