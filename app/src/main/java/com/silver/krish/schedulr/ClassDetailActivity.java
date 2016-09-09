package com.silver.krish.schedulr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silver.krish.schedulr.Models.Class;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ClassDetailActivity extends AppCompatActivity {
	@BindView(R.id.class_detail_activity_assignment_recycler_view) RecyclerView mAssignmentRecyclerView;
	@BindView(R.id.class_activity_detail_toolbar) Toolbar mToolbar;
	@BindView(R.id.activity_class_detail_class_number_textview) TextView mClassNumberTextview;
	@BindView(R.id.activity_class_detail_subject_textview) TextView mSubjectTextView;
	private AssignmentListViewAdapter mAssignmentListViewAdapter;
	private Class editClass;
	private Realm mRealm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_detail);
		mRealm = Realm.getDefaultInstance();
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		mAssignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAssignmentListViewAdapter = new AssignmentListViewAdapter();
		mAssignmentRecyclerView.setAdapter(mAssignmentListViewAdapter);
		Intent intent = getIntent();
		long classId = intent.getLongExtra(ClassFragment.EDIT_CLASS_ID_KEY, 0);
		editClass = getEditClass(classId);

		//TODO: Put these in a callback
		getSupportActionBar().setTitle(editClass.getClassName());
		mClassNumberTextview.setText("" + editClass.getClassNumber());
		mSubjectTextView.setText(editClass.getSubject());
	}


	public class AssignmentListViewAdapter extends RecyclerView.Adapter<AssignmentListViewHolder>{

		@Override
		public AssignmentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(ClassDetailActivity.this);
			View itemView = inflater.inflate(R.layout.class_detail_assignment_item_view, parent, false);
			return new AssignmentListViewHolder(itemView);
		}

		@Override
		public void onBindViewHolder(AssignmentListViewHolder holder, int position) {
			holder.bindHolderViews(position);
		}

		@Override
		public int getItemCount() {
			return 10;
		}

	}

	public class AssignmentListViewHolder extends RecyclerView.ViewHolder{
		@BindView(R.id.class_detail_assignment_item_title)
		TextView mTitleTextView;
		public AssignmentListViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void bindHolderViews(int position){
			mTitleTextView.setText("Krishna");
		}
	}

	private Class getEditClass(long classId){
		RealmQuery<Class> query = mRealm.where(Class.class);
		query.equalTo("classId", classId);

		RealmResults<Class> results = query.findAll();
		return results.get(0);
	}
}
