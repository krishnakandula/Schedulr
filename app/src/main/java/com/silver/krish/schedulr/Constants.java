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
	private static final int ASSIGNMENT_GRID_LAYOUT_COLUMNS = 2;
	public static final int COLOR_PICKER_REQUEST_CODE = 4;

	public static class ColorCodes{
		public static String light_purple = "#D1C4E9";
		public static String light_indigo = "#C5CAE9";
		public static String light_blue = "#BBDEFB";
		public static String light_cyan = "#B2EBF2";
		public static String light_teal = "#B2DFDB";
		public static String light_blue_grey = "#CFD8DC";
		public static String light_green = "#C8E6C9";
		public static String light_yellow = "#FFF9C4";
		public static String light_amber = "#FFECB3";
		public static String light_orange = "#FFE0B2";
		public static String light_deep_orange = "#FFCCBC";
		public static String light_brown = "#D7CCC8";
		public static String light_red = "#FFCDD2";
		public static String light_pink = "#F8BBD0";

		public static String[] colors = {light_amber, light_blue, light_blue_grey, light_brown, light_cyan, light_deep_orange, light_green,
											light_indigo, light_orange, light_pink, light_red, light_teal, light_yellow};
	}

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

	public static int getAssignmentGridLayoutColumns() {
		return ASSIGNMENT_GRID_LAYOUT_COLUMNS;
	}
}
