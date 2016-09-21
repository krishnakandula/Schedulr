package com.silver.krish.schedulr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silver.krish.schedulr.Controllers.AssignmentController;
import com.silver.krish.schedulr.Models.Assignment;
import com.silver.krish.schedulr.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Krishna Kandula on 9/17/2016.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
	private Context mContext;
	public TasksAdapter(Context context){
		mContext = context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View itemView = inflater.inflate(R.layout.assignment_item_list_view, parent, false);

		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.onBind(position);
	}

	@Override
	public int getItemCount() {
		return AssignmentController.getAssignmentController().getAssignmentList().size();
//		return 5;
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		@BindView(R.id.assignment_list_view_title) TextView titleView;
		@BindView(R.id.assignment_list_view_description) TextView descriptionView;
		@BindView(R.id.assignment_list_view_due_date) TextView dueDateView;
		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void onBind(int position){
			Assignment assignment = AssignmentController.getAssignmentController().getAssignmentList().get(position);
			titleView.setText(assignment.getAssignmentName());
			descriptionView.setText(assignment.getDescription());
			Date date = assignment.getDueDate();
			int day = date.getDay();
			int month = date.getMonth();
			int year = date.getYear();
			StringBuilder formattedDate = new StringBuilder("" + month).append("/").append(day).append("/").append(year);
			dueDateView.setText("Due: " + formattedDate.toString());
		}

		@Override
		public void onClick(View v) {

		}
	}
}
