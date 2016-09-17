package com.silver.krish.schedulr.Controllers;

import android.util.Log;

import com.silver.krish.schedulr.Models.Assignment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Krishna Kandula on 9/17/2016.
 */
public class AssignmentController {
	private static AssignmentController assignmentController;
	private static List<Assignment> assignmentList;
	private static final String LOG_TAG = Assignment.class.getSimpleName();
	private AssignmentController(){
		assignmentList = new ArrayList<>();
	}

	public static AssignmentController getAssignmentController(){
		if(assignmentController == null){
			assignmentController = new AssignmentController();
		}
		return assignmentController;
	}

	public List<Assignment> getAssignmentList(){
		return assignmentList;
	}

	public void updateAssignmentList(){
		Realm mRealm = Realm.getDefaultInstance();
		RealmResults<Assignment> realmResults = mRealm.where(Assignment.class).findAll();
		if(realmResults.isEmpty()){
			Log.v(LOG_TAG, "Assignments table was empty");
			assignmentList.clear();
		} else {
			assignmentList = mRealm.copyFromRealm(realmResults);
		}
		Log.v(LOG_TAG, assignmentList.toString());
	}

//	public boolean addAssignment(Assignment assignment, Class c){
//
//	}
}