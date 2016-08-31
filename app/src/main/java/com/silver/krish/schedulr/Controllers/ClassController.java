package com.silver.krish.schedulr.Controllers;

import com.silver.krish.schedulr.Models.Class;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna Kandula on 8/30/2016.
 */
public class ClassController {
	private static ClassController singletonClassController;
	private static List<Class> classList;
	private ClassController(){
		classList = new ArrayList<>();
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
				if(newClass.getPrimaryId() == c.getPrimaryId()){
					unique = false;
				}
			}
			if(unique)
				classList.add(newClass);
			return unique;
		}
	}
}
