package com.silver.krish.schedulr.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.silver.krish.schedulr.Fragments.ClassFragment;
import com.silver.krish.schedulr.Models.Assignment;
import com.silver.krish.schedulr.Models.Class;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Krishna Kandula on 8/30/2016.
 */
public class ClassController {
	private static final String LOG_TAG = ClassController.class.getSimpleName();
	private static ClassController singletonClassController;
	private static List<Class> classList;
	private Realm mRealm;
	private ClassController(){
		classList = new ArrayList<>();
		updateClassList();
	}

	public static ClassController getClassController(){
		if(singletonClassController == null){
			singletonClassController = new ClassController();
		}
		return singletonClassController;
	}

	/**
	 * Adds a class to the class list
	 * @param newClass the class that needs to be added
	 * @return whether or not the class was added
	 */
	public boolean addClass(Class newClass){
		mRealm = Realm.getDefaultInstance();
		if(classList.isEmpty()){
			addClassToRealm(newClass);
			classList.add(newClass);
			return true;
		} else {
			//Check if the primary id of newClass is unique
			boolean unique = classIsUnique(newClass);
			if(unique) {
				//Add to Realm db
				addClassToRealm(newClass);
				classList.add(newClass);
			}
			return unique;
		}
	}

	public boolean classIsUnique(Class newClass){
		for(Class c : classList){
			if((newClass.getSubject() .equals(c.getSubject())) && newClass.getClassNumber() == c.getClassNumber()){
				return false;
			}
		}
		return true;
	}

	private void addClassToRealm(Class newClass){
		mRealm.beginTransaction();
		Class c = mRealm.copyToRealmOrUpdate(newClass);
		mRealm.commitTransaction();
	}

	public Class getClass(String subject, long classNumber){
		mRealm = Realm.getDefaultInstance();
		Class realmResult = mRealm.where(Class.class)
				.equalTo("subject", subject)
				.equalTo("classNumber", classNumber)
				.findFirst();

		return realmResult;
	}

	public void updateClassList(){
		mRealm = Realm.getDefaultInstance();
		RealmResults<Class> updatedClasses = mRealm.where(Class.class).findAll();
		classList.clear();
		if(updatedClasses.size() == 0){
			Log.v(LOG_TAG, "Class table was empty");
		} else {
			for(Class c : updatedClasses){
				addClass(c);
			}
		}
	}

	public boolean deleteClass(String subject, long classNumber){
		mRealm.beginTransaction();
		RealmResults<Class> results = mRealm.where(Class.class)
				.equalTo("subject", subject)
				.equalTo("classNumber", classNumber)
				.findAll();

		if (results.size() > 0){
			results.deleteAllFromRealm();
			mRealm.commitTransaction();
			return true;
		} else {
			mRealm.cancelTransaction();
			return false;
		}

		//TODO: Delete assignments related to this class
	}

	public List<Class> getClassList(){
		return classList;
	}
}
