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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohammed on 27/10/2017.
 */

public class NoticeDialogFragment extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogReferentPositiveClick(DialogFragment dialog, ContentValues jsonreferent);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    private String TAG = "NoticeDialogFragment";
    private Long mEtablissementId;
    private Long mReferentId, mReferentIdBase;
    private String mDisciplineId;
    private Context mContext;
    private AutoCompleteTextView mDiscipline;
    private RadioGroup mGenreReferent;
    private EditText mNomReferent;
    private EditText mPrenomReferent;
    private EditText mTelReferent;
    private EditText mMailReferent;
    private String mTitre;

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    public static NoticeDialogFragment newInstance(Long idetab,Long idreferent, String titre) {

        NoticeDialogFragment f = new NoticeDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong("idetab", idetab);
        args.putLong("idreferent", idreferent);
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
        mReferentId = getArguments().getLong("idreferent",0);
        mTitre = getArguments().getString("titre");
        mDisciplineId="";
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewdialog = inflater.inflate(R.layout.referent_dialog, null);
        mGenreReferent = (RadioGroup) viewdialog.findViewById(R.id.civilite);
        mNomReferent = (EditText) viewdialog.findViewById(R.id.nom);
        mPrenomReferent = (EditText) viewdialog.findViewById(R.id.prenom);
        mTelReferent = (EditText) viewdialog.findViewById(R.id.tel);
        mMailReferent = (EditText) viewdialog.findViewById(R.id.email);
        mDiscipline = (AutoCompleteTextView) viewdialog.findViewById(R.id.liste_disciplines);
        Cursor listedisciplines = DaneContract.getListeDisciplines(mContext);
        DisciplinesAdapter mDisciplineAdapter = new DisciplinesAdapter(mContext,listedisciplines ,0);
        mDiscipline.setAdapter(mDisciplineAdapter);
        mDiscipline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                mDisciplineId = DaneContract.getValueFromCursor(cursor,DaneContract.DisciplineEntry._ID);
            }
        });
        mReferentIdBase = Long.valueOf(0);
        if (mReferentId > 0) {
            Cursor cursorreferent = DaneContract.getReferentFromId(mContext,mReferentId);
//            mEtablissementId = cursorreferent.getLong(cursorreferent.getColumnIndexOrThrow(DaneContract.ReferentEntry.COLUMN_ETABLISSEMENT_ID));
//            mReferentIdBase = cursorreferent.getLong(cursorreferent.getColumnIndexOrThrow(DaneContract.ReferentEntry.COLUMN_REFERENT_ID));
            String civilite = DaneContract.getValueFromCursor(cursorreferent,"CIVILITE");
            mNomReferent.setText(DaneContract.getValueFromCursor(cursorreferent,DaneContract.ReferentEntry.COLUMN_NOM));
            mPrenomReferent.setText(DaneContract.getValueFromCursor(cursorreferent,DaneContract.ReferentEntry.COLUMN_PRENOM));
            mTelReferent.setText(DaneContract.getValueFromCursor(cursorreferent,DaneContract.ReferentEntry.COLUMN_TEL));
            mMailReferent.setText(DaneContract.getValueFromCursor(cursorreferent,DaneContract.ReferentEntry.COLUMN_EMAIL));
            mDiscipline.setText(DaneContract.getValueFromCursor(cursorreferent,"NOMDISCIPLINE"));
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
                        ContentValues jsonreferent = new ContentValues();
                            final String genre = ((RadioButton) viewdialog.findViewById(mGenreReferent.getCheckedRadioButtonId())).getText().toString();
                            jsonreferent.put("nom",mNomReferent.getText().toString());
                            jsonreferent.put("prenom",mPrenomReferent.getText().toString());
                            jsonreferent.put("civilite_id",DaneContract.getIdCiviliteFromNom(mContext,genre));
                            jsonreferent.put("tel",mTelReferent.getText().toString());
                            jsonreferent.put("email",mMailReferent.getText().toString());
                            jsonreferent.put("statut","référent numérique");
                            jsonreferent.put("discipline_id",mDisciplineId);
                            jsonreferent.put("etablissement_id",mEtablissementId);
                            jsonreferent.put("synchroniser",false);
                            jsonreferent.put("_id",mReferentId);
                            jsonreferent.put("referent_id",mReferentIdBase);
                        Log.d(TAG, "enregistrer le référent : "+jsonreferent);
                        mListener.onDialogReferentPositiveClick(NoticeDialogFragment.this, jsonreferent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
//                        Log.d(TAG, "quitter : ");
                        mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                    }
                });
        return builder.create();
    }
}