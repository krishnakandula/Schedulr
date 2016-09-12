package com.silver.krish.schedulr.Controllers;

import android.util.Log;

import com.silver.krish.schedulr.Fragments.ClassFragment;
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
			classList.add(newClass);
			return true;
		} else {
			boolean unique = true;
			//Check if the primary id of newClass is unique
			for(Class c : classList){
				if(newClass.getClassId() == c.getClassId()){
					unique = false;
				}
			}
			if(unique) {
				//Add to Realm db
				mRealm.beginTransaction();
				Class c = mRealm.copyToRealm(newClass);
				mRealm.commitTransaction();

				classList.add(newClass);
			}
			return unique;
		}
	}

	public Class getClass(String subject, long classNumber){
		mRealm = Realm.getDefaultInstance();
		RealmResults<Class> realmResults = mRealm.where(Class.class)
				.equalTo("subject", subject)
				.equalTo("classNumber", classNumber)
				.findAll();

		return realmResults.get(0);
	}

	public void updateClassList(){
		mRealm = Realm.getDefaultInstance();
		RealmResults<Class> updatedClasses = mRealm.where(Class.class).findAll();
		if(updatedClasses.size() == 0){
			Log.v(LOG_TAG, "Class table was empty");
		} else {
			classList.clear();
			for(Class c : updatedClasses){
				addClass(c);
			}
		}
	}

	public List<Class> getClassList(){
		return classList;
	}

}
