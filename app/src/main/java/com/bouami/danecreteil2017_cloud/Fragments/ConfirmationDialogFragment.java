package com.bouami.danecreteil2017_cloud.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mbouami on 26/10/2017.
 */

@SuppressLint("ValidFragment")
public class ConfirmationDialogFragment extends DialogFragment {

    public interface ConfirmationDialogListener {
        public void onDialogReferentDeleteClick(ConfirmationDialogFragment dialog, JSONObject jsonreferent);
        public void onDialogReferentQuitterClick(ConfirmationDialogFragment dialog);
    }
    private String TAG = "ConfirmationDialogFragment";
    private Cursor mcursor;
    private String mTitre, mMessage, mIdreferent, mIdbasereferent;
    private Context mContext;

    ConfirmationDialogListener mListener;

    public static ConfirmationDialogFragment newInstance(String titre, String message, String idreferent, String idbasereferent) {

        ConfirmationDialogFragment f = new ConfirmationDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("titre", titre);
        args.putString("message", message);
        args.putString("idreferent", idreferent);
        args.putString("idbasereferent", idbasereferent);
        f.setArguments(args);
        return f;
    }
//    public ConfirmationDialogFragment(Cursor cursor,String message) {
//        mcursor = cursor;
//        mMessage = message;
//    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getBaseContext();
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ConfirmationDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ConfirmationDialogListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitre = getArguments().getString("titre");
        mMessage = getArguments().getString("message");
        mIdreferent = getArguments().getString("idreferent");
        mIdbasereferent = getArguments().getString("idbasereferent");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_settings_applications_black_24dp)
                .setMessage(mMessage)
                .setPositiveButton(R.string.Oui, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
//                        final String idreferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry._ID));
//                        final String idbasereferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_REFERENT_ID));
//                        final String nomreferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_NOM));
//                        Log.d(TAG, "Suppression du référent : "+nomreferent+"("+idreferent+"-"+idbasereferent+")");
                        JSONObject jsonreferent = new JSONObject();
                        try {
                            jsonreferent.put("id",mIdreferent);
                            jsonreferent.put("idbase",mIdbasereferent);
                            DaneContract.deleteReferent(getContext(),jsonreferent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mListener.onDialogReferentDeleteClick(ConfirmationDialogFragment.this,jsonreferent);
                    }
                })
                .setNegativeButton(R.string.Non, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogReferentQuitterClick(ConfirmationDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
