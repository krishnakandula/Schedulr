package com.silver.krish.schedulr;

/**
 * Created by Krishna Kandula on 9/14/2016.
 */
public class Constants {
	private static final int CLASS_LIST_PAGE_POSITION = 0;
	private static final int ASSIGNMENT_LIST_PAGE_POSITION = 1;
	private static final int NUMBER_OF_PAGES = 2;
	private static final String EDIT_CLASS_SUBJECT_KEY = "EDIT_CLASS_SUBJECT";
	private static final String EDIT_CLASS_NUMBER_KEY = "EDIT_CLASS_NUMBER_KEY";
	private static final int EDIT_CLASS_INTENT_REQUEST_CODE = 3;

	public static int getEditClassIntentRequestCode() {
		return EDIT_CLASS_INTENT_REQUEST_CODE;
	}

	public static String getEditClassNumberKey() {
		return EDIT_CLASS_NUMBER_KEY;
	}

	public static int getClassListPagePosition() {
		return CLASS_LIST_PAGE_POSITION;
	}

	public static int getAssignmentListPagePosition() {
		return ASSIGNMENT_LIST_PAGE_POSITION;
	}

	public static int getNumberOfPages() {
		return NUMBER_OF_PAGES;
	}

	public static String getEditClassSubjectKey() {
		return EDIT_CLASS_SUBJECT_KEY;
	}
}
