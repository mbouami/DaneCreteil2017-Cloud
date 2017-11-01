package com.bouami.danecreteil2017_cloud.Fragments;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bouami.danecreteil2017_cloud.Adapter.DisciplinesAdapter;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by Mohammed on 27/10/2017.
 */

public class PersonnelDialogFragment extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPersonnelPositiveClick(DialogFragment dialog, ContentValues jsonpersonnel);
        public void onDialogPersonnelCancelClick(DialogFragment dialog);
    }
    private String TAG = "PersonnelDialogFragment";
    private Long mEtablissementId;
    private Long mPersonnelId, mPersonnelIdBase;
    private Context mContext;
    private RadioGroup mGenrePersonnel;
    private EditText mNomPersonnel;
    private EditText mPrenomPersonnel;
    private EditText mTelPersonnel;
    private EditText mMailPersonnel;
    private String mTitre;

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    public static PersonnelDialogFragment newInstance(Long idetab, Long idpersonnel, String titre) {

        PersonnelDialogFragment f = new PersonnelDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong("idetab", idetab);
        args.putLong("idpersonnel", idpersonnel);
        args.putString("titre", titre);
        f.setArguments(args);
        return f;
    }
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getBaseContext();
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEtablissementId = getArguments().getLong("idetab",0);
        mPersonnelId = getArguments().getLong("idpersonnel",0);
        mTitre = getArguments().getString("titre");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewdialog = inflater.inflate(R.layout.personnel_dialog, null);
        mGenrePersonnel = (RadioGroup) viewdialog.findViewById(R.id.civilite);
        mNomPersonnel = (EditText) viewdialog.findViewById(R.id.nom);
        mPrenomPersonnel = (EditText) viewdialog.findViewById(R.id.prenom);
        mTelPersonnel = (EditText) viewdialog.findViewById(R.id.tel);
        mMailPersonnel = (EditText) viewdialog.findViewById(R.id.email);
        mPersonnelIdBase = Long.valueOf(0);
        if (mPersonnelId > 0) {
            Cursor cursorpersonnel = DaneContract.getPersonnelFromId(mContext,mPersonnelId);
//            mEtablissementId = cursorreferent.getLong(cursorreferent.getColumnIndexOrThrow(DaneContract.ReferentEntry.COLUMN_ETABLISSEMENT_ID));
//            mReferentIdBase = cursorreferent.getLong(cursorreferent.getColumnIndexOrThrow(DaneContract.ReferentEntry.COLUMN_REFERENT_ID));
            String civilite = DaneContract.getValueFromCursor(cursorpersonnel,"CIVILITE");
            mNomPersonnel.setText(DaneContract.getValueFromCursor(cursorpersonnel,DaneContract.PersonnelEntry.COLUMN_NOM));
            mPrenomPersonnel.setText(DaneContract.getValueFromCursor(cursorpersonnel,DaneContract.PersonnelEntry.COLUMN_PRENOM));
            mTelPersonnel.setText(DaneContract.getValueFromCursor(cursorpersonnel,DaneContract.PersonnelEntry.COLUMN_TEL));
            mMailPersonnel.setText(DaneContract.getValueFromCursor(cursorpersonnel,DaneContract.PersonnelEntry.COLUMN_EMAIL));
            switch (civilite) {
                case "MM" :
                    RadioButton selectionmme = (RadioButton) viewdialog.findViewById(R.id.mme);
                    selectionmme.setChecked(true);
                    break;
                case "M" :
                    RadioButton selectionmr = (RadioButton) viewdialog.findViewById(R.id.mr);
                    selectionmr.setChecked(true);
                    break;
            }
        }
        // Pass null as the parent view because its going in the dialog layout
        builder.setIcon(R.drawable.ic_settings_applications_black_24dp)
                .setTitle(mTitre)
                .setView(viewdialog)
                // Add action buttons
                .setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ContentValues jsonpersonnel = new ContentValues();
                            final String genre = ((RadioButton) viewdialog.findViewById(mGenrePersonnel.getCheckedRadioButtonId())).getText().toString();
//                            jsonpersonnel.put("nom",mNomPersonnel.getText().toString());
//                            jsonpersonnel.put("prenom",mPrenomPersonnel.getText().toString());
//                            jsonpersonnel.put("civilite_id",DaneContract.getIdCiviliteFromNom(mContext,genre));
                            jsonpersonnel.put("tel",mTelPersonnel.getText().toString());
                            jsonpersonnel.put("email",mMailPersonnel.getText().toString());
                            jsonpersonnel.put("synchroniser",false);
                            jsonpersonnel.put("_id",mPersonnelId);
                            jsonpersonnel.put("personnel_id",mPersonnelIdBase);
                            Log.d(TAG, "enregistrer le personnel : "+jsonpersonnel);
                        mListener.onDialogPersonnelPositiveClick(PersonnelDialogFragment.this, jsonpersonnel);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
//                        Log.d(TAG, "quitter : ");
                        mListener.onDialogPersonnelCancelClick(PersonnelDialogFragment.this);
                    }
                });
        return builder.create();
    }
}