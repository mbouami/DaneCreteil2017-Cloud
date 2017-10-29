package com.bouami.danecreteil2017_cloud.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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
        public void onDialogReferentPositiveClick(DialogFragment dialog, JSONObject jsonreferent);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    private String TAG = "NoticeDialogFragment";
    private Long mEtablissementId;
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

    public static NoticeDialogFragment newInstance(Long idetab, String titre) {

        NoticeDialogFragment f = new NoticeDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong("idetab", idetab);
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
        // Pass null as the parent view because its going in the dialog layout
        builder.setIcon(R.drawable.ic_settings_applications_black_24dp)
                .setTitle(mTitre)
                .setView(viewdialog)
                // Add action buttons
                .setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        JSONObject jsonreferent = new JSONObject();
                        try {
                            final String genre = ((RadioButton) viewdialog.findViewById(mGenreReferent.getCheckedRadioButtonId())).getText().toString();
                            jsonreferent.put("nom",mNomReferent.getText().toString());
                            jsonreferent.put("prenom",mPrenomReferent.getText().toString());
                            jsonreferent.put("civilite",genre);
                            jsonreferent.put("tel",mTelReferent.getText().toString());
                            jsonreferent.put("email",mMailReferent.getText().toString());
                            jsonreferent.put("statut","1");
                            jsonreferent.put("discipline",mDisciplineId);
                            jsonreferent.put("etablissement",mEtablissementId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.d(TAG, "enregistrer le référent : "+jsonreferent);
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
