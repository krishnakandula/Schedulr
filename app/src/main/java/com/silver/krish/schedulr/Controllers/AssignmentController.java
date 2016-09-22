package com.silver.krish.schedulr.Controllers;

import android.util.Log;

import com.silver.krish.schedulr.Models.Assignment;
import com.silver.krish.schedulr.Models.Class;

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
		mRealm.beginTransaction();
		RealmResults<Assignment> realmResults = mRealm.where(Assignment.class).findAll();
		assignmentList.clear();
		if(realmResults.isEmpty()){
			Log.v(LOG_TAG, "Assignments table was empty");
		} else {
			assignmentList = mRealm.copyFromRealm(realmResults);
		}
		mRealm.commitTransaction();
		Log.v(LOG_TAG, assignmentList.toString());
	}

	public boolean addAssignment(Assignment assignment){
		addAssignmentToRealm(assignment);
		assignmentList.add(assignment);
		//Update classes assignment list
//		Class c = ClassController.getClassController().getClass(assignment.getSubject(), assignment.getClassNumber());
//		c.getAssignments().add(assignment);
		return true;
	}

	private void addAssignmentToRealm(Assignment assignment){
		Realm mRealm = Realm.getDefaultInstance();
		mRealm.beginTransaction();
		mRealm.copyToRealmOrUpdate(assignment);
		mRealm.commitTransaction();
	}

	private void updateAssignmentsForClasses(){

	}
}