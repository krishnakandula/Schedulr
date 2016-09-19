package com.silver.krish.schedulr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silver.krish.schedulr.Controllers.AssignmentController;
import com.silver.krish.schedulr.R;

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
			if(position == 3){
				titleView.setText("Work on Discrete Math Homework");
				dueDateView.setText("Jan 4");
				descriptionView.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam purus magna, accumsan ut nisi eget, imperdiet porttitor eros. Vivamus maximus fermentum vehicula. Curabitur viverra enim ut nisi fermentum laoreet. Curabitur lobortis enim eget nulla hendrerit iaculis. Donec condimentum ligula eleifend justo sodales, et bibendum sem bibendum. Interdum et malesuada fames ac ante ipsum primis in faucibus. In tortor arcu, faucibus ac est ac, auctor efficitur orci. In mattis nulla id malesuada bibendum. Duis sagittis aliquet vestibulum. Praesent dapibus urna in malesuada fermentum. Etiam eros eros, venenatis vitae sem ut, hendrerit iaculis dui. In at turpis nec sem tristique rutrum. Cras eros augue, pretium quis dignissim ut, porttitor sed tortor. Aliquam dignissim ante libero, quis consectetur lectus tincidunt vel. Pellentesque at gravida enim.");
				itemView.setBackgroundResource(R.color.material_amber_400);
			}
		}

		@Override
		public void onClick(View v) {

		}
	}
}
