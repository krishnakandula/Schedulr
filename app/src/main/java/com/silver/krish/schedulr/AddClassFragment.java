package com.silver.krish.schedulr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Krishna Kandula on 9/3/2016.
 */
public class AddClassFragment extends Fragment{
	@BindView(R.id.add_class_fragment_floating_action_button) FloatingActionButton mFloatingActionButton;
	@BindView(R.id.add_class_fragment_class_name_edit_text) EditText mClassNameEditText;
	@BindView(R.id.add_class_fragment_class_num_edit_text) EditText mClassNumEditText;
	@BindView(R.id.add_class_fragment_subject_edit_text) EditText mSubjectEditText;
	@BindView(R.id.add_class_fragment_teacher_edit_text) EditText mTeacherEditText;

	private Unbinder mUnbinder;
	public static final String CLASS_NAME_KEY = "CLASS_NAME";
	public static final String CLASS_NUM_KEY = "CLASS_NUM";
	public static final String TEACHER_NAME_KEY = "TEACHER_NAME";
	public static final String SUBJECT_NAME_KEY = "SUBJECT_NAME";

	private static final String LOG_TAG = AddClassFragment.class.getSimpleName();
	public static AddClassFragment newInstance(){
		Bundle bundle = new Bundle();
		AddClassFragment classFragment = new AddClassFragment();
		classFragment.setArguments(bundle);

		return classFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_class, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.add_class_toolbar_title));
		return view;
	}

	public void sendResult(int resultCode, Intent data){
		if(getTargetFragment() == null){
			Log.e(LOG_TAG, "Target fragment is null");
		} else {
			getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, data);
			getActivity().getSupportFragmentManager().popBackStack();
		}
	}

	@OnClick(R.id.add_class_fragment_floating_action_button)
	public void onClickFloatingActionButton(){
		//TODO: Check to see if any of the inputs fields are null
		String className = mClassNameEditText.getText().toString();
		long classNumber = Long.parseLong(mClassNumEditText.getText().toString());
		String teacherName = mTeacherEditText.getText().toString();
		String subjectName = mSubjectEditText.getText().toString();
		Intent intent = new Intent();
		intent.putExtra(CLASS_NAME_KEY, className);
		intent.putExtra(CLASS_NUM_KEY, classNumber);
		intent.putExtra(TEACHER_NAME_KEY, teacherName);
		intent.putExtra(SUBJECT_NAME_KEY, subjectName);
		sendResult(Activity.RESULT_OK, intent);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//Nullify view references
		mUnbinder.unbind();
	}
}
