package org.tyaa.furniturehelper.manager.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class Dialog2Buttons extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		Activity ac = getActivity();
		Bundle args = getArguments();
		DialogInterface.OnClickListener listener = (DialogInterface.OnClickListener)ac;
		String msg = args.getString("msg");

		AlertDialog.Builder blr = new AlertDialog.Builder(ac);
		
		blr.setMessage(msg).
		setPositiveButton(android.R.string.yes, listener).
		setNegativeButton(android.R.string.no, listener);
		
		return blr.create();
	}
}
