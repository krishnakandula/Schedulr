package com.silver.krish.schedulr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.silver.krish.schedulr.Controllers.ClassController;
import com.silver.krish.schedulr.Models.Class;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Krishna Kandula on 8/27/2016.
 */
public class ClassFragment extends Fragment implements FloatingToolbar.ItemClickListener, SwipeRefreshLayout.OnRefreshListener{
	@BindView(R.id.classes_recycler_view) RecyclerView mRecyclerView;
	@BindView(R.id.main_floating_toolbar) FloatingToolbar mFloatingToolbar;
	@BindView(R.id.main_floating_action_button) FloatingActionButton mFloatingActionButton;
	@BindView(R.id.class_fragment_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
	private static final String LOG_TAG = ClassFragment.class.getSimpleName();
	private static final int ADD_CLASS_REQUEST_CODE = 0;
	private Unbinder mUnbinder;
	private RecyclerViewAdapter mViewAdapter;
	private ClassController mClassController;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mClassController = ClassController.getClassController();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.classes_fragment, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		onRefresh();
		mSwipeRefreshLayout.setOnRefreshListener(this);
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

	public class ClassViewHolder extends RecyclerView.ViewHolder{
		@BindView(R.id.class_item_view_title) TextView titleView;
		public ClassViewHolder(View itemView){
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void bindView(Class newClass){
			titleView.setText(newClass.getClassName());
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
				fm.beginTransaction().replace(R.id.main_activity_container, addClassFragment).addToBackStack(null).commit();
				break;
			default:
		}
	}

	@Override
	public void onItemLongClick(MenuItem item) {
		//TODO: Add something here
	}

	@Override
	public void onRefresh() {
		List<Class> updatedList = mClassController.getClassList();
		if(mViewAdapter == null){
			mViewAdapter = new RecyclerViewAdapter(updatedList);
			mRecyclerView.setAdapter(mViewAdapter);
		}
		mViewAdapter.updateClassList(updatedList);
		mViewAdapter.notifyDataSetChanged();
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(LOG_TAG, "onResume called");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//Unbind toolbar to prevent memory leaks
		mUnbinder.unbind();
	}
}
