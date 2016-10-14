package com.silver.krish.schedulr.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.silver.krish.schedulr.Constants;
import com.silver.krish.schedulr.Controllers.AssignmentController;
import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Models.Assignment;
import com.silver.krish.schedulr.Models.Class;
import com.silver.krish.schedulr.R;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Krishna Kandula on 9/17/2016.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
	private Context mContext;
	private List<Assignment> mAssignmentList;
	OnTaskClickedListener mTaskClickedListener;
	public TasksAdapter(Context context, List<Assignment> assignments, OnTaskClickedListener callback){
		mContext = context;
		setAssignmentList(assignments);
		this.mTaskClickedListener = callback;
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
		return mAssignmentList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
		@BindView(R.id.assignment_list_view_title) TextView titleView;
		@BindView(R.id.assignment_list_view_description) TextView descriptionView;
		@BindView(R.id.assignment_list_view_due_date) TextView dueDateView;
		@BindView(R.id.assignment_list_item_cardview) CardView cardView;

		private Assignment mAssignment;
		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);
		}

		public void onBind(int position){
			mAssignment = mAssignmentList.get(position);
			titleView.setText(mAssignment.getSubject() + " " + mAssignment.getClassNumber());
			descriptionView.setText(mAssignment.getDescription());
			Date date = mAssignment.getDueDate();
			int day = date.getDate();
			int month = date.getMonth();
			int year = date.getYear();
			StringBuilder formattedDate = new StringBuilder("" + month).append("/").append(day).append("/").append(year);
			dueDateView.setText("Due: " + formattedDate.toString());
			Class c = ClassController.getClassController().getClass(mAssignment.getSubject(), mAssignment.getClassNumber());
			cardView.setCardBackgroundColor(Color.parseColor(c.getColorCode()));
		}

		private void randomlySetCardViewBackgroundColor(CardView cardView){
			cardView.setCardBackgroundColor(Color.parseColor(Constants.ColorCodes.light_deep_orange));
		}

		@Override
		public void onClick(View v) {
			mTaskClickedListener.onTaskClicked(mAssignment);
		}

		@Override
		public boolean onLongClick(View view) {
			mTaskClickedListener.onLongClicked(mAssignment, getAdapterPosition());
			return true;
		}
	}

	public interface OnTaskClickedListener{
		void onTaskClicked(Assignment assignment);
		void onLongClicked(Assignment assignment, int position);
	}

	private static int getRandomInteger(int min, int max){
		int range = max - min;
		return ((int) (Math.random() * range) + min);
	}

	public void setAssignmentList(List<Assignment> assignmentList){
		this.mAssignmentList = assignmentList;
	}
}
