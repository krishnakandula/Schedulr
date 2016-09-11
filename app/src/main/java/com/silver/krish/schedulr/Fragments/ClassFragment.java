package com.silver.krish.schedulr.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.MainActivity;
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

	private Unbinder mUnbinder;
	private Realm mRealm;
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
		refreshClassList();
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
		}

		@Override
		public void onClick(View v) {
			Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.activity_main_coordinator_layout), "CLICK", Snackbar.LENGTH_LONG);
			snackbar.show();
		}

		@Override
		public boolean onLongClick(View v) {
			v.setSelected(true);
			return true;
		}
	}

	private void setupClassRecyclerView(){
		mRecyclerViewAdapter = new RecyclerViewAdapter();
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRecyclerView.setAdapter(mRecyclerViewAdapter);
	}

	private void refreshClassList(){
		ClassController.getClassController().updateClassList();
		mRecyclerViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mRealm.close();
		mUnbinder.unbind();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
