package com.silver.krish.schedulr.Models;

import java.util.List;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Krishna Kandula on 8/28/2016.
 */
public class Class extends RealmObject{
	@PrimaryKey
	private long classId;
	private String className;
	private String subject;
	private String teacher;
	private long classNumber;
//	private List<Assignment> assignments;

	public Class(){}

	public Class(String className, long classNumber, long classId){
		this.className = className;
		this.classNumber = classNumber;
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getClassId() {
		return classId;
	}

	public void setClassId(long classId) {
		this.classId = classId;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public long getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(long classNumber) {
		this.classNumber = classNumber;
	}
}
