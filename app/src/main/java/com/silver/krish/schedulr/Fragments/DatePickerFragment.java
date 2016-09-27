package com.silver.krish.schedulr.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Krishna Kandula on 9/20/2016.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
	public static final String YEAR_KEY = "YEAR";
	public static final String MONTH_KEY = "MONTH";
	public static final String DAY_KEY = "DAY";
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//Use current date as default date
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getContext(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
		Intent data = new Intent();
		data.putExtra(YEAR_KEY, year);
		data.putExtra(MONTH_KEY, month);
		data.putExtra(DAY_KEY, dayOfMonth);
		getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
	}
}
