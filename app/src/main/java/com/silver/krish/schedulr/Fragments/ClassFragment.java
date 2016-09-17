package com.silver.krish.schedulr.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.silver.krish.schedulr.Controllers.AssignmentController;
import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.MainActivity;
import com.silver.krish.schedulr.Models.Assignment;
import com.silver.krish.schedulr.Models.Class;
import com.silver.krish.schedulr.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Krishna Kandula on 9/10/2016.
 */
public class ClassFragment extends Fragment {
	@BindView(R.id.classes_recycler_view) RecyclerView mRecyclerView;

	private RecyclerViewAdapter mRecyclerViewAdapter;
	private LinearLayoutManager mLinearLayoutManager;
	private Unbinder mUnbinder;
	private Realm mRealm;
	private static final String LOG_TAG = ClassFragment.class.getSimpleName();
	private static boolean classItemViewIsSelected;
	private static Class classItemSelected;
	private static Integer classItemSelectedPosition;
	OnClassItemSelectedListener mClassItemSelectedListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mClassItemSelectedListener = (OnClassItemSelectedListener)activity;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRealm = Realm.getDefaultInstance();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.classes_fragment, container, false);
		mUnbinder = ButterKnife.bind(this, view);

		setupClassRecyclerView();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		//Refresh list whenever class fragment is shown
		Log.v(LOG_TAG, "onResume called");
		refreshClassList();
		AssignmentController.getAssignmentController().updateAssignmentList();
	}

	public class RecyclerViewAdapter extends RecyclerView.Adapter<ClassViewHolder>{

		public RecyclerViewAdapter(){
		}

		@Override
		public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.class_item_view, parent, false);
			return new ClassViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ClassViewHolder holder, int position) {
			holder.bindView(position);
		}

		@Override
		public int getItemCount() {
			return ClassController.getClassController().getClassList().size();
		}

	}

	public class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
		@BindView(R.id.class_item_view_title) TextView mTitleView;
		@BindView(R.id.class_item_class_number_text_view) TextView  mClassNumberTextView;
		@BindView(R.id.class_item_subject_text_view) TextView mSubjectTextView;
		@BindView(R.id.class_item_teacher_text_view) TextView mTeacherTextView;
		@BindView(R.id.class_item_card_view) CardView mCardView;

		private Class c;
		private int position;
		public ClassViewHolder(View itemView){
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);
		}

		public void bindView(int position) {
			this.position = position;
			this.c = ClassController.getClassController().getClassList().get(position);
			mTitleView.setText(c.getClassName());
			mClassNumberTextView.setText(String.format("%d", c.getClassNumber()));
			mSubjectTextView.setText(c.getSubject());
			mTeacherTextView.setText(c.getTeacher());
			if(classItemViewIsSelected){
				mCardView.setBackgroundResource(R.color.cardview_shadow_start_color);
			} else {
				mCardView.setBackgroundResource(R.color.default_color);
			}
		}

		@Override
		public void onClick(View v) {
			if(classItemViewIsSelected && position == classItemSelectedPosition){
				mCardView.setBackgroundResource(R.color.default_color);
				classItemViewIsSelected = false;
				mClassItemSelectedListener.onClassItemSelected(false);
			} else {
				//TODO:Open class detail activity
			}
		}

		@Override
		public boolean onLongClick(View v) {
			if(!classItemViewIsSelected){
				classItemViewIsSelected = true;
				mCardView.setBackgroundResource(R.color.cardview_shadow_start_color);
				position = mLinearLayoutManager.getPosition(itemView);
				classItemSelectedPosition = position;
				//Set public variable equal to class so Main Activity can call detail activity
				classItemSelected = ClassController.getClassController()
						.getClass((String)mSubjectTextView.getText(), Long.parseLong((String)mClassNumberTextView.getText()));

				//Call callback method in main activity
				mClassItemSelectedListener.onClassItemSelected(true);
				return true;
			} else {
				return false;
			}
		}
	}

	public interface OnClassItemSelectedListener{
		void onClassItemSelected(boolean isSelected);
	}

	private void setupClassRecyclerView(){
		mRecyclerViewAdapter = new RecyclerViewAdapter();
		mLinearLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLinearLayoutManager);
		mRecyclerView.setAdapter(mRecyclerViewAdapter);
	}

	private void refreshClassList(){
		ClassController.getClassController().updateClassList();
		mRecyclerViewAdapter.notifyDataSetChanged();
	}


	public Class getClassItemSelected(){
		return classItemSelected;
	}

	public boolean getClassItemViewIsSelected(){
		return classItemViewIsSelected;
	}

	public void setClassItemViewIsSelected(boolean classItemViewIsSelected) {
		ClassFragment.classItemViewIsSelected = classItemViewIsSelected;
	}

	public void setClassItemSelected(Class classItemSelected) {
		ClassFragment.classItemSelected = classItemSelected;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mRealm.close();
		mUnbinder.unbind();
	}
}