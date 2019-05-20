package com.gfd.cropwis.fragments;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.gfd.cropwis.R;

public class AboutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getText(R.string.app_name))
                .setMessage(getText(R.string.about_dialog))
                .setPositiveButton(R.string.dialog_ok, null)
                .create();
        alertDialog.show();
        ((TextView)alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        return alertDialog;
    }
}
