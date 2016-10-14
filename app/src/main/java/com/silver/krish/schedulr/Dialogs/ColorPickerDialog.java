package com.silver.krish.schedulr.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.silver.krish.schedulr.Constants;

/**
 * Created by Krishna Kandula on 10/3/2016.
 */

public class ColorPickerDialog extends DialogFragment implements DialogInterface.OnClickListener{
	String colorClicked = Constants.ColorCodes.colors[0];
	private OnColorChosenListener mColorChosenListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try{
			this.mColorChosenListener = (OnColorChosenListener) getActivity();
		} catch (ClassCastException e){
			throw new ClassCastException(getActivity().toString() + " must implement OnColorChosenListener");
		}
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Choose Color").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).setItems(Constants.ColorCodes.colors, this);

		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		colorClicked = Constants.ColorCodes.colors[which];
		mColorChosenListener.onColorChosen(colorClicked);
	}

	public static interface OnColorChosenListener{
		public abstract void onColorChosen(String colorCode);
	}
}
