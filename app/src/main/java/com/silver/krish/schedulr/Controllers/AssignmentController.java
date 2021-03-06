package com.silver.krish.schedulr.Controllers;

import android.util.Log;

import com.silver.krish.schedulr.Models.Assignment;
import com.silver.krish.schedulr.Models.Class;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
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
	}

	public boolean addAssignment(Assignment assignment){
		addAssignmentToRealm(assignment);
		addAssignmentToClass(assignment);
		assignmentList.add(assignment);
		return true;
	}

	private void addAssignmentToRealm(Assignment assignment){
		Realm mRealm = Realm.getDefaultInstance();
		mRealm.beginTransaction();
		mRealm.copyToRealmOrUpdate(assignment);
		mRealm.commitTransaction();
	}

	private void addAssignmentToClass(Assignment assignment){
		Realm mRealm = Realm.getDefaultInstance();
		mRealm.beginTransaction();
		Class c = ClassController.getClassController().getClass(assignment.getSubject(), assignment.getClassNumber());
		c.getAssignments().add(assignment);
		mRealm.commitTransaction();
	}

	public void deleteAssignment(Assignment assignment){
		final long assignmentId = assignment.getAssignmentId();

		//Remove assignment from list
		Iterator<Assignment> it = getAssignmentList().listIterator();
		while(it.hasNext()){
			if(it.next().getAssignmentId() == assignmentId)
				it.remove();
		}

		//Remove assignment from DB
		Realm mRealm = Realm.getDefaultInstance();
		mRealm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				Assignment a = realm.where(Assignment.class).equalTo("assignmentId", assignmentId).findFirst();
				a.deleteFromRealm();
			}
		});
	}
}