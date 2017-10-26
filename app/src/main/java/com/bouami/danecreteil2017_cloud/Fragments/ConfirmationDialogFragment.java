package com.bouami.danecreteil2017_cloud.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mbouami on 26/10/2017.
 */

public class ConfirmationDialogFragment extends DialogFragment {
    private String TAG = "ConfirmationDialogFragment";
    private Cursor mcursor;

    public ConfirmationDialogFragment(Cursor cursor) {
        mcursor = cursor;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_confirmation_suppression)
                .setPositiveButton(R.string.Oui, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        final String idreferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry._ID));
                        final String idbasereferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_REFERENT_ID));
                        final String nomreferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_NOM));
                        Log.d(TAG, "Suppression du référent : "+nomreferent+"("+idreferent+"-"+idbasereferent+")");
                        JSONObject jsonreferent = new JSONObject();
                        try {
                            jsonreferent.put("id",idreferent);
                            jsonreferent.put("idbase",idbasereferent);
                            DaneContract.deleteReferent(getContext(),jsonreferent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(R.string.Non, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
