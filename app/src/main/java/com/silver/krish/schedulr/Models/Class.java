package com.silver.krish.schedulr.Models;

import java.util.List;
import java.util.UUID;

/**
 * Created by Krishna Kandula on 8/28/2016.
 */
public class Class {
	private UUID primaryId;
	private String className;
	private String subject;
	private long classId;
	private String teacher;
	private long classNumber;
	private List<Assignment> assignments;

	public Class(String className, long classNumber){
		this.primaryId = UUID.randomUUID();
		this.className = className;
		this.classNumber = classNumber;
	}
	public UUID getPrimaryId() {
		return primaryId;
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
