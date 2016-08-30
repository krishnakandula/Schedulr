package com.silver.krish.schedulr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Krishna Kandula on 8/27/2016.
 */
public class ClassFragment extends Fragment{
	@BindView(R.id.classes_recycler_view) RecyclerView mRecyclerView;
	private Unbinder mUnbinder;
	private RecyclerViewAdapter mViewAdapter;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.classes_fragment, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mViewAdapter = new RecyclerViewAdapter();
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRecyclerView.setAdapter(mViewAdapter);
		return view;
	}

	public class RecyclerViewAdapter extends RecyclerView.Adapter<ClassViewHolder>{

		@Override
		public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.class_item_view, parent, false);
			return new ClassViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ClassViewHolder holder, int position) {
			holder.bindView();
		}

		@Override
		public int getItemCount() {
			return 30;
		}
	}

	public class ClassViewHolder extends RecyclerView.ViewHolder{
		@BindView(R.id.class_item_view_title) TextView titleView;
		public ClassViewHolder(View itemView){
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void bindView(){
			titleView.setText("test");
		}
	}
}
