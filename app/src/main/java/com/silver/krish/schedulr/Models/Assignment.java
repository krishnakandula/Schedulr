package com.silver.krish.schedulr.Models;

import java.util.Date;
import java.util.Random;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Krishna Kandula on 8/28/2016.
 */
public class Assignment extends RealmObject{
	private Date dueDate;
//	private Date assignedDate;
	private String subject;
	private long classNumber;
	private String description;
	private boolean isCompleted;
	@PrimaryKey
	private long assignmentId;

	public Assignment(){
		assignmentId = new Random().nextInt();
	}

	public Assignment(Date dueDate, String description){
		assignmentId = new Random().nextInt();
		this.dueDate = dueDate;
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(long classNumber) {
		this.classNumber = classNumber;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}

	@Override
	public String toString() {
		return description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}