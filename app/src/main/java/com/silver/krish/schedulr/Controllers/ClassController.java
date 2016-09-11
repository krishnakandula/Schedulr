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
				Realm mRealm = Realm.getDefaultInstance();

				mRealm.beginTransaction();
				Class c = mRealm.copyToRealm(newClass);
				mRealm.commitTransaction();

				classList.add(newClass);
			}
			return unique;
		}
	}



	public void updateClassList(){
		Realm realm = Realm.getDefaultInstance();
		RealmResults<Class> updatedClasses = realm.where(Class.class).findAll();
		if(updatedClasses.size() == 0){
			Log.v(LOG_TAG, "Class table was empty");
		} else {
			classList.clear();
			for(Class c : updatedClasses){
				addClass(c);
			}
		}
		realm.close();
	}

	public List<Class> getClassList(){
		return classList;
	}

}
