package com.silver.krish.schedulr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Models.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by Krishna Kandula on 8/27/2016.
 */
public class ClassFragment extends Fragment implements FloatingToolbar.ItemClickListener{
	@BindView(R.id.classes_recycler_view) RecyclerView mRecyclerView;
	@BindView(R.id.main_floating_toolbar) FloatingToolbar mFloatingToolbar;
	@BindView(R.id.main_floating_action_button) FloatingActionButton mFloatingActionButton;
	private static final String LOG_TAG = ClassFragment.class.getSimpleName();
	private static final int ADD_CLASS_REQUEST_CODE = 0;
	private static final int EDIT_CLASS_REQUEST_CODE = 1;
	public static final String EDIT_CLASS_ID_KEY = "edit_class_id_key";
	private Unbinder mUnbinder;
	private RecyclerViewAdapter mViewAdapter;
	private ClassController mClassController;
	private Realm mRealm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mClassController = ClassController.getClassController();
		mRealm = Realm.getDefaultInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.classes_fragment, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//		mSwipeRefreshLayout.setOnRefreshListener(this);
		onRefresh();
		//Setup floating toolbar
		mFloatingToolbar.attachFab(mFloatingActionButton);
		mFloatingToolbar.attachRecyclerView(mRecyclerView);
		mFloatingToolbar.setClickListener(this);
		return view;
	}

	public class RecyclerViewAdapter extends RecyclerView.Adapter<ClassViewHolder>{
		private List<Class> mClassList;

		public RecyclerViewAdapter(List<Class> classes){
			updateClassList(classes);
		}

		@Override
		public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.class_item_view, parent, false);
			return new ClassViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ClassViewHolder holder, int position) {
			holder.bindView(mClassList.get(position));
		}

		@Override
		public int getItemCount() {
			return mClassList.size();
		}

		public void updateClassList(List<Class> updatedClassList){
			mClassList = updatedClassList;
		}
	}

	public class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
		@BindView(R.id.class_item_view_title) TextView mTitleView;
		@BindView(R.id.class_item_class_number_text_view) TextView  mClassNumberTextView;
		@BindView(R.id.class_item_subject_text_view) TextView mSubjectTextView;
		@BindView(R.id.class_item_teacher_text_view) TextView mTeacherTextView;
		private Class c;
		public ClassViewHolder(View itemView){
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);
		}

		public void bindView(Class newClass){
			this.c = newClass;
			mTitleView.setText(newClass.getClassName());
			mClassNumberTextView.setText(String.format("%d", newClass.getClassNumber()));
			mSubjectTextView.setText(newClass.getSubject());
			mTeacherTextView.setText(newClass.getTeacher());
		}

		@Override
		public void onClick(View v) {
			//Open detail activity
			Intent intent = new Intent(getContext(), ClassDetailActivity.class);
			intent.putExtra(EDIT_CLASS_ID_KEY, c.getClassId());
			getActivity().startActivityForResult(intent, EDIT_CLASS_REQUEST_CODE);
		}

		@Override
		public boolean onLongClick(View v) {
			//Open up ClassOptionsDialog
			final MaterialDialog classOptionsDialog = new MaterialDialog.Builder(getContext())
					.title("Edit").content(mSubjectTextView.getText() + " " + mClassNumberTextView.getText())
					.positiveText("Edit")
					.negativeText("Cancel")
					.onPositive(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							//Open edit view
						}
					}).onNegative(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							dialog.dismiss();
						}
					}).show();
			return true;
		}
	}

	@Override
	public void onItemClick(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId){
			case R.id.main_floating_toolbar_add_class:
				FragmentManager fm = getActivity().getSupportFragmentManager();
				AddClassFragment addClassFragment = AddClassFragment.newInstance();
				addClassFragment.setTargetFragment(ClassFragment.this, ADD_CLASS_REQUEST_CODE);
				fm.beginTransaction()
						.replace(R.id.main_activity_container, addClassFragment)
						.addToBackStack(null)
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.commit();
				break;
			default:
		}
	}

	@Override
	public void onItemLongClick(MenuItem item) {

	}

	public void onRefresh() {
		mClassController.updateClassList();
		List<Class> updatedList = mClassController.getClassList();
		if(mViewAdapter == null){
			mViewAdapter = new RecyclerViewAdapter(updatedList);
		}
		mRecyclerView.setAdapter(mViewAdapter);
		mViewAdapter.updateClassList(updatedList);
		mViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		onRefresh();
		Log.v(LOG_TAG, "onResume called");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//Unbind toolbar to prevent memory leaks
		mUnbinder.unbind();
		mRealm.close();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode != Activity.RESULT_OK){
			Log.e(LOG_TAG, "resultCode is not okay");
		} else {
			switch (requestCode){
				//New class added, so refresh
				case ADD_CLASS_REQUEST_CODE:
					//Pop back stack to go back to this fragment
					String className = data.getStringExtra(AddClassFragment.CLASS_NAME_KEY);
					long classNumber = data.getLongExtra(AddClassFragment.CLASS_NUM_KEY, 0);
					String teacherName = data.getStringExtra(AddClassFragment.TEACHER_NAME_KEY);
					String subject = data.getStringExtra(AddClassFragment.SUBJECT_NAME_KEY);
					mRealm.beginTransaction();
					Class newClass = mRealm.createObject(Class.class);
					newClass.setClassId(new Random().nextInt());
					newClass.setClassName(className);
					newClass.setClassNumber(classNumber);
					newClass.setSubject(subject);
					newClass.setTeacher(teacherName);
					mClassController.addClass(newClass);
					mRealm.commitTransaction();
					onRefresh();
					break;
			}
		}
	}
}